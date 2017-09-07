package it.antani.cc;

import dan200.computercraft.api.ComputerCraftAPI;
import it.antani.cc.turtle.ChunkloadedTurtle;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = AntaniCCMod.MODID, version = AntaniCCMod.VERSION, name = "AntaniCC", dependencies = "required-after:ComputerCraft")
public class AntaniCCMod
{
    public static final String MODID = "antanicc";
    public static final String VERSION = "1.2";
    public static final Logger logger = LogManager.getLogger(MODID);

    public static AntaniCCMod instance;

    public AntaniCCMod() {
        instance = this;
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        logger.info("Registering AntaniCC provider");
        ComputerCraftAPI.registerPeripheralProvider(new IndustrialPeripheralProvider());
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
