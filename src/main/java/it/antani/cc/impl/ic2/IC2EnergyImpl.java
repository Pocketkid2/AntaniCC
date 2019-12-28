package it.antani.cc.impl.ic2;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import ic2.core.block.wiring.TileEntityElectricBlock;
import it.antani.cc.annotations.AcceptsTileEntity;
import it.antani.cc.annotations.LuaMethod;

@AcceptsTileEntity(TileEntityElectricBlock.class)
public class IC2EnergyImpl {

    @LuaMethod("get_energy")
    public Object[] getEnergy(TileEntityElectricBlock block, IComputerAccess access, ILuaContext context, Object[] args) {
        return new Object[]{block.energy.getEnergy()};
    }

    @LuaMethod("get_capacity")
    public Object[] getCap(TileEntityElectricBlock block, IComputerAccess access, ILuaContext context, Object[] args) {
        return new Object[]{block.getCapacity()};
    }

    @LuaMethod("get_output")
    public Object[] getOutput(TileEntityElectricBlock block, IComputerAccess access, ILuaContext context, Object[] args) {
        return new Object[]{block.getOutput()};
    }
}
