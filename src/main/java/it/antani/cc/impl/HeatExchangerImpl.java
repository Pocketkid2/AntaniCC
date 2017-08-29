package it.antani.cc.impl;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import ic2.core.block.machine.tileentity.TileEntityLiquidHeatExchanger;
import it.antani.cc.annotations.AcceptsTileEntity;
import it.antani.cc.annotations.LuaMethod;
import it.antani.cc.util.Utils;

@AcceptsTileEntity(TileEntityLiquidHeatExchanger.class)
public class HeatExchangerImpl {
    @LuaMethod("get_input_tank")
    public Object[] getInput(TileEntityLiquidHeatExchanger te, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return Utils.getFluidInfo(te.getInputTank());
    }

    @LuaMethod("get_output_tank")
    public Object[] getOutput(TileEntityLiquidHeatExchanger te, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return Utils.getFluidInfo(te.getOutputTank());
    }
}
