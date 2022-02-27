package it.antani.cc;

import dan200.computercraft.api.ComputerCraftAPI;
import it.antani.cc.turtle.ChunkloadedTurtle;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = AntaniCCMod.MODID, version = AntaniCCMod.VERSION, name = "AntaniCC", dependencies = "required-after:computercraft;required-after:ic2")
public class AntaniCCMod
{
    public static final String MODID = "antanicc";
    public static final String VERSION = "2.0.0";
    public static final Logger logger = LogManager.getLogger(MODID);

    @Config(modid = AntaniCCMod.MODID)
    public static class Configuration {
        @Config.Comment(
                {"Disable peripheral",
                "Useful when in same modpack with plethora"}
        )
        public static boolean disablePeripheral = false;
    }

    public static AntaniCCMod instance;

    public AntaniCCMod() {
        instance = this;
    }

    @EventHandler
    public void init(FMLPreInitializationEvent event)
    {
        Items.createItems();
        if(event.getSide().equals(Side.CLIENT)){
            Items.registerRenderers();
        }
    }
    
    @EventHandler
    public void recipeLoad(FMLInitializationEvent event){
        Items.initializeMod();
        if(!Configuration.disablePeripheral) {
            logger.info("Registering AntaniCC provider");
            ComputerCraftAPI.registerPeripheralProvider(new IndustrialPeripheralProvider());
        }else{
            logger.info("Not registering the peripheral provider. Disabled by config.");
        }
    }

    @EventHandler
    public void load(FMLPostInitializationEvent event){
        logger.info("Registering AntaniCC chunkloaded turtle");
        ComputerCraftAPI.registerTurtleUpgrade(new ChunkloadedTurtle());
        ForgeChunkManager.setForcedChunkLoadingCallback(this, (tickets, world) -> {
            //Turtles are dead anyway, so we release the tickets
            for(ForgeChunkManager.Ticket t : tickets){
                ForgeChunkManager.releaseTicket(t);
            }
        });
    }
}
