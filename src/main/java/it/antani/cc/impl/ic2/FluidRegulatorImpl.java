package it.antani.cc.impl.ic2;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import ic2.core.block.machine.tileentity.TileEntityFluidRegulator;
import it.antani.cc.annotations.AcceptsTileEntity;
import it.antani.cc.annotations.LuaMethod;
import it.antani.cc.util.LuaValueSetter;

import java.lang.reflect.Field;

@AcceptsTileEntity(TileEntityFluidRegulator.class)
public class FluidRegulatorImpl {

    @LuaMethod("get_output_rate")
    public Object[] getOutputMb(TileEntityFluidRegulator te, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return new Object[] { te.getoutputmb() };
    }

    @LuaMethod("set_output_rate")
    public Object[] setOutputMb(TileEntityFluidRegulator te, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException, LuaException, InterruptedException {
        Field f = TileEntityFluidRegulator.class.getDeclaredField("outputmb");
        context.issueMainThreadTask(new LuaValueSetter(f, te, args[0]));
        return new Object[0];
    }


    @LuaMethod("get_mode")
    public Object[] getMode(TileEntityFluidRegulator te, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException{
        Field f = TileEntityFluidRegulator.class.getDeclaredField("mode");
        f.setAccessible(true);
        return new Object[] { f.get(te) };
    }

    @LuaMethod("set_mode")
    public Object[] setMode(TileEntityFluidRegulator te, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException,  LuaException, InterruptedException{
        Field f = TileEntityFluidRegulator.class.getDeclaredField("mode");

        context.issueMainThreadTask(new LuaValueSetter(f, te, args[0]));

        return new Object[0];
    }

}
