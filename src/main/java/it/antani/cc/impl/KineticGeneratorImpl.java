package it.antani.cc.impl;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import ic2.core.block.generator.tileentity.TileEntityKineticGenerator;
import it.antani.cc.annotations.AcceptsTileEntity;
import it.antani.cc.annotations.LuaMethod;

import java.lang.reflect.Field;

@AcceptsTileEntity(TileEntityKineticGenerator.class)
public class KineticGeneratorImpl {

    @LuaMethod("get_kinetic_production")
    public Object[] getEnergy(TileEntityKineticGenerator block, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        Field f = TileEntityKineticGenerator.class.getDeclaredField("receivedkinetic");
        f.setAccessible(true);
        return new Object[]{ block.getproduction(), f.get(block) };
    }
}
