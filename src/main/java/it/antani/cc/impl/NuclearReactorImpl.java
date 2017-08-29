package it.antani.cc.impl;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import ic2.core.block.reactor.tileentity.TileEntityNuclearReactorElectric;
import it.antani.cc.annotations.AcceptsTileEntity;
import it.antani.cc.annotations.LuaMethod;
import it.antani.cc.util.Utils;

@AcceptsTileEntity(TileEntityNuclearReactorElectric.class)
public class NuclearReactorImpl {

    @LuaMethod("get_eu_output")
    public Object[] getEu(TileEntityNuclearReactorElectric te, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return new Object[] { te.getReactorEUEnergyOutput() };
    }

    @LuaMethod("get_heat")
    public Object[] getHeat(TileEntityNuclearReactorElectric te, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return new Object[] { te.getHeat(), te.getMaxHeat() };
    }

    @LuaMethod("get_emit_heat")
    public Object[] getEmitHeat(TileEntityNuclearReactorElectric te, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return new Object[] { te.EmitHeat };
    }

    @LuaMethod("get_hot_fluid")
    public Object[] getFluidOut(TileEntityNuclearReactorElectric te, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return Utils.getFluidInfo(te.getoutputtank());
    }

    @LuaMethod("get_cold_fluid")
    public Object[] getFluidIn(TileEntityNuclearReactorElectric te, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return Utils.getFluidInfo(te.getinputtank());
    }
}
