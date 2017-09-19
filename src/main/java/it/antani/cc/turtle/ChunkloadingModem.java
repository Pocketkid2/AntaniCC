package it.antani.cc.turtle;

import dan200.computercraft.ComputerCraft;
import net.minecraft.item.Item;

public class ChunkloadingModem extends Item {
    public ChunkloadingModem() {
        super();

        this.setUnlocalizedName("Chunkloading Modem");
        this.setRegistryName("chunkloading_modem");
        this.setCreativeTab(ComputerCraft.mainCreativeTab);
    }
}
