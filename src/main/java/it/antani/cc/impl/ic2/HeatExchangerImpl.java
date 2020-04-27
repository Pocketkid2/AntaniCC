package it.antani.cc.impl.ic2;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import ic2.core.block.machine.tileentity.TileEntityLiquidHeatExchanger;
import it.antani.cc.IndustrialPeripheralContext;
import it.antani.cc.annotations.AcceptsTileEntity;
import it.antani.cc.annotations.LuaMethod;
import it.antani.cc.util.Utils;

@AcceptsTileEntity(TileEntityLiquidHeatExchanger.class)
public class HeatExchangerImpl {
    @LuaMethod("get_input_tank")
    public Object[] getInput(IndustrialPeripheralContext<TileEntityLiquidHeatExchanger> ctx, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return Utils.getFluidInfo(ctx.getTileEntity().getInputTank());
    }

    @LuaMethod("get_output_tank")
    public Object[] getOutput(IndustrialPeripheralContext<TileEntityLiquidHeatExchanger> ctx, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return Utils.getFluidInfo(ctx.getTileEntity().getOutputTank());
    }
}
