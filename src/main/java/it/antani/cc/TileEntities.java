package it.antani.cc;

import ic2.core.block.wiring.TileEntityElectricBlock;
import it.antani.cc.impl.IC2EnergyImpl;
import net.minecraft.tileentity.TileEntity;

public enum TileEntities {

    ENERGY("Energy Generation", TileEntityElectricBlock.class, new IC2EnergyImpl()),


    ;
    public final String label;
    public final Object provider;
    public final Class<? extends TileEntity> type;
    TileEntities(String name, Class<? extends TileEntity> type, Object provider){
        this.label = name;
        this.provider = provider;
        this.type = type;
    }
}
