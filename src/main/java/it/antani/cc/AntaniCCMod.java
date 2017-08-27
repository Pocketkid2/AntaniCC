package it.antani.cc;

import dan200.computercraft.api.ComputerCraftAPI;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = AntaniCCMod.MODID, version = AntaniCCMod.VERSION, name = "AntaniCC", dependencies = "required-after:ComputerCraft")
public class AntaniCCMod
{
    public static final String MODID = "antanicc";
    public static final String VERSION = "1.0";
    public static final Logger logger = LogManager.getLogger(MODID);
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        logger.info("Registering AntaniCC provider");
        ComputerCraftAPI.registerPeripheralProvider(new IndustrialPeripheralProvider());
    }
}
