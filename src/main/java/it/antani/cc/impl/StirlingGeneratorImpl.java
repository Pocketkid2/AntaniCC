package it.antani.cc.impl;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import ic2.core.block.generator.tileentity.TileEntityStirlingGenerator;
import it.antani.cc.annotations.AcceptsTileEntity;
import it.antani.cc.annotations.LuaMethod;

@AcceptsTileEntity(TileEntityStirlingGenerator.class)
public class StirlingGeneratorImpl {
    @LuaMethod("get_production")
    public Object[] getPressure(TileEntityStirlingGenerator te, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return new Object[] {
                te.receivedheat, te.production, te.productionpeerheat
        };
    }
}
