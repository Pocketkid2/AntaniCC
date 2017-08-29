package it.antani.cc.impl;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import ic2.core.block.machine.tileentity.TileEntityCondenser;
import it.antani.cc.util.Utils;
import it.antani.cc.annotations.AcceptsTileEntity;
import it.antani.cc.annotations.LuaMethod;

import java.lang.reflect.Field;

/**
 * This tile entity is used by the condenser
 */
@AcceptsTileEntity(TileEntityCondenser.class)
public class CondenserImpl {

    /**
     * Gets the progress of the operation.
     *
     * Returns: *(int, int)* - current progress over maximum progress
     */
    @LuaMethod("get_progress")
    public Object[] getProgress(TileEntityCondenser te, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        Field f1 = TileEntityCondenser.class.getDeclaredField("progress");
        f1.setAccessible(true);

        Field f2 = TileEntityCondenser.class.getDeclaredField("maxProgress");
        f2.setAccessible(true);

        return new Object[] { f1.get(te), f2.get(te) };
    }

    /**
     * Gets information about the input tank
     */
    @LuaMethod("get_input_tank")
    public Object[] getInputFluid(TileEntityCondenser te, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return Utils.getFluidInfo(te.getInputTank());
    }

    /**
     * Gets information about the output tank
     */
    @LuaMethod("get_output_tank")
    public Object[] getOutput(TileEntityCondenser te, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return Utils.getFluidInfo(te.getOutputTank());
    }
}
