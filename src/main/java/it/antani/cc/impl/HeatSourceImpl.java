package it.antani.cc.impl;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import ic2.core.block.TileEntityHeatSourceInventory;
import it.antani.cc.annotations.AcceptsTileEntity;
import it.antani.cc.annotations.LuaMethod;

@AcceptsTileEntity(TileEntityHeatSourceInventory.class)
public class HeatSourceImpl {
    @LuaMethod("get_heat")
    public Object[] getInput(TileEntityHeatSourceInventory te, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return new Object[] {
                te.gettransmitHeat(),
                te.getMaxHeatEmittedPerTick()
        };
    }
}
