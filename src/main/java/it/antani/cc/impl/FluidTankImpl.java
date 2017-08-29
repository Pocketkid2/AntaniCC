package it.antani.cc.impl;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import ic2.core.block.TileEntityLiquidTankElectricMachine;
import ic2.core.block.machine.tileentity.TileEntityFluidRegulator;
import it.antani.cc.annotations.AcceptsTileEntity;
import it.antani.cc.annotations.LuaMethod;
import it.antani.cc.util.Utils;

@AcceptsTileEntity(TileEntityLiquidTankElectricMachine.class)
public class FluidTankImpl {
    @LuaMethod("get_fluid_info")
    public Object[] getOutputMb(TileEntityLiquidTankElectricMachine te, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return Utils.getFluidInfo(te.getFluidTank());
    }
}
