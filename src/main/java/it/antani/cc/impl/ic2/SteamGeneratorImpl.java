package it.antani.cc.impl.ic2;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import ic2.core.block.machine.tileentity.TileEntitySteamGenerator;
import it.antani.cc.IndustrialPeripheralContext;
import it.antani.cc.annotations.AcceptsTileEntity;
import it.antani.cc.annotations.LuaMethod;
import it.antani.cc.util.LuaValueSetter;
import it.antani.cc.util.Utils;

import java.lang.reflect.Field;

@AcceptsTileEntity(TileEntitySteamGenerator.class)
public class SteamGeneratorImpl {
    @LuaMethod("get_pressure")
    public Object[] getPressure(IndustrialPeripheralContext<TileEntitySteamGenerator> ctx, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return new Object[] {ctx.getTileEntity().getPressure()};
    }

    @LuaMethod("set_pressure")
    public Object[] setPressure(IndustrialPeripheralContext<TileEntitySteamGenerator> ctx, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException, LuaException {
        Field f = TileEntitySteamGenerator.class.getField("pressure");
        context.issueMainThreadTask(new LuaValueSetter(f, ctx.getTileEntity(), args[0]));
        return null;
    }

    @LuaMethod("get_water_input")
    public Object[] getInput(IndustrialPeripheralContext<TileEntitySteamGenerator> ctx, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return new Object[] {ctx.getTileEntity().getInputMB()};
    }

    @LuaMethod("set_water_input")
    public Object[] setInput(IndustrialPeripheralContext<TileEntitySteamGenerator> ctx, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException, LuaException {
        Field f = TileEntitySteamGenerator.class.getField("inputMB");
        context.issueMainThreadTask(new LuaValueSetter(f, ctx.getTileEntity(), args[0]));
        return null;
    }

    @LuaMethod("get_calcification")
    public Object[] getCalcification(IndustrialPeripheralContext<TileEntitySteamGenerator> ctx, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException, LuaException {
        Field f1 = TileEntitySteamGenerator.class.getDeclaredField("calcification");
        f1.setAccessible(true);
        Field f2 = TileEntitySteamGenerator.class.getDeclaredField("maxCalcification");
        f2.setAccessible(true);
        TileEntitySteamGenerator te = ctx.getTileEntity();
        return new Object[] {
                f1.get(te),
                f2.get(te)
        };
    }

    @LuaMethod("get_heat_input")
    public Object[] getHeatInput(TileEntitySteamGenerator te, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return new Object[] {te.getHeatInput()};
    }

    @LuaMethod("get_system_heat")
    public Object[] getSysHeat(TileEntitySteamGenerator te, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return new Object[] {te.getSystemHeat()};
    }

    @LuaMethod("get_output_info")
    public Object[] getOutput(TileEntitySteamGenerator te, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return new Object[] { te.getOutputMB(), te.getOutputFluidName() };
    }

    @LuaMethod("get_water_info")
    public Object[] getWaterTank(TileEntitySteamGenerator te, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return Utils.getFluidInfo(te.waterTank);
    }


}
