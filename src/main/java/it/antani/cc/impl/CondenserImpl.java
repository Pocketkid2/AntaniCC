package it.antani.cc.impl;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import ic2.core.block.machine.tileentity.TileEntityCondenser;
import it.antani.cc.util.Utils;
import it.antani.cc.annotations.AcceptsTileEntity;
import it.antani.cc.annotations.LuaMethod;

import java.lang.reflect.Field;

@AcceptsTileEntity(TileEntityCondenser.class)
public class CondenserImpl {

    @LuaMethod("get_progress")
    public Object[] getProgress(TileEntityCondenser te, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        Field f1 = TileEntityCondenser.class.getDeclaredField("progress");
        f1.setAccessible(true);

        Field f2 = TileEntityCondenser.class.getDeclaredField("maxProgress");
        f2.setAccessible(true);

        return new Object[] { f1.get(te), f2.get(te) };
    }

    @LuaMethod("get_input_tank")
    public Object[] getInputFluid(TileEntityCondenser te, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return Utils.getFluidInfo(te.getInputTank());
    }

    @LuaMethod("get_output_tank")
    public Object[] getOutput(TileEntityCondenser te, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return Utils.getFluidInfo(te.getOutputTank());
    }
}
