package it.antani.cc.impl;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import ic2.core.block.TileEntityBlock;
import it.antani.cc.annotations.AcceptsTileEntity;
import it.antani.cc.annotations.LuaMethod;
import it.antani.cc.util.LuaTileActivator;

@AcceptsTileEntity(TileEntityBlock.class)
public class CoreIc2Impl {

    @LuaMethod("is_active")
    public Object[] isActive(TileEntityBlock te, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return new Object[] { te.getActive() };
    }

    /*@LuaMethod("set_active")
    public Object[] setActive(TileEntityBlock te, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException, LuaException {
        context.issueMainThreadTask(new LuaTileActivator(te, (boolean) args[0]));
        return null;
    }*/

}
