package it.antani.cc.util;

import dan200.computercraft.api.lua.ILuaTask;
import dan200.computercraft.api.lua.LuaException;
import ic2.core.block.TileEntityBlock;

import javax.annotation.Nullable;

public class LuaTileActivator implements ILuaTask{
    private final TileEntityBlock block;
    private final boolean status;

    public LuaTileActivator(TileEntityBlock block, boolean status) {
        this.block = block;
        this.status = status;
    }

    @Nullable
    @Override
    public Object[] execute() throws LuaException {
        block.setActive(status);
        return null;
    }
}
