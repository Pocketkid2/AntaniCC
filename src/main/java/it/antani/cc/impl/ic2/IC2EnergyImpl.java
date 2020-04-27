package it.antani.cc.impl.ic2;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import ic2.core.block.wiring.TileEntityElectricBlock;
import it.antani.cc.IndustrialPeripheralContext;
import it.antani.cc.annotations.AcceptsTileEntity;
import it.antani.cc.annotations.LuaMethod;

@AcceptsTileEntity(TileEntityElectricBlock.class)
public class IC2EnergyImpl {

    @LuaMethod("get_energy")
    public Object[] getEnergy(IndustrialPeripheralContext<TileEntityElectricBlock> ctx, IComputerAccess access, ILuaContext context, Object[] args) {
        return new Object[]{ctx.getTileEntity().energy.getEnergy()};
    }

    @LuaMethod("get_capacity")
    public Object[] getCap(IndustrialPeripheralContext<TileEntityElectricBlock> ctx, IComputerAccess access, ILuaContext context, Object[] args) {
        return new Object[]{ctx.getTileEntity().getCapacity()};
    }

    @LuaMethod("get_output")
    public Object[] getOutput(IndustrialPeripheralContext<TileEntityElectricBlock> ctx, IComputerAccess access, ILuaContext context, Object[] args) {
        return new Object[]{ctx.getTileEntity().getOutput()};
    }
}
