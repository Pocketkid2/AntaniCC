package it.antani.cc.impl.ic2;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import ic2.core.block.TileEntityHeatSourceInventory;
import it.antani.cc.IndustrialPeripheralContext;
import it.antani.cc.annotations.AcceptsTileEntity;
import it.antani.cc.annotations.LuaMethod;

@AcceptsTileEntity(TileEntityHeatSourceInventory.class)
public class HeatSourceImpl {
    @LuaMethod("get_heat")
    public Object[] getInput(IndustrialPeripheralContext<TileEntityHeatSourceInventory> ctx, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        TileEntityHeatSourceInventory te = ctx.getTileEntity();
        return new Object[] {
                te.gettransmitHeat(),
                te.getMaxHeatEmittedPerTick()
        };
    }
}
