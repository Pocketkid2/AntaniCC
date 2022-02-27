package it.antani.cc.impl.ic2;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import ic2.core.block.reactor.tileentity.TileEntityReactorChamberElectric;
import ic2.core.block.reactor.tileentity.TileEntityNuclearReactorElectric;
import it.antani.cc.IndustrialPeripheralContext;
import it.antani.cc.annotations.AcceptsTileEntity;
import it.antani.cc.annotations.LuaMethod;
import it.antani.cc.util.Utils;

@AcceptsTileEntity(TileEntityReactorChamberElectric.class)
public class NuclearReactorChamberImpl {

    @LuaMethod("get_eu_output")
    public Object[] getEu(IndustrialPeripheralContext<TileEntityReactorChamberElectric> ctx, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return new Object[] { ctx.getTileEntity().getReactorInstance().getReactorEUEnergyOutput() };
    }

    @LuaMethod("get_heat")
    public Object[] getHeat(IndustrialPeripheralContext<TileEntityReactorChamberElectric> ctx, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        TileEntityNuclearReactorElectric te = ctx.getTileEntity().getReactorInstance();
        return new Object[] { te.getHeat(), te.getMaxHeat() };
    }

    @LuaMethod("get_emit_heat")
    public Object[] getEmitHeat(IndustrialPeripheralContext<TileEntityReactorChamberElectric> ctx, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return new Object[] { ctx.getTileEntity().getReactorInstance().EmitHeat };
    }

    @LuaMethod("get_hot_fluid")
    public Object[] getFluidOut(IndustrialPeripheralContext<TileEntityReactorChamberElectric> ctx, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return Utils.getFluidInfo(ctx.getTileEntity().getReactorInstance().getoutputtank());
    }

    @LuaMethod("get_cold_fluid")
    public Object[] getFluidIn(IndustrialPeripheralContext<TileEntityReactorChamberElectric> ctx, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return Utils.getFluidInfo(ctx.getTileEntity().getReactorInstance().getinputtank());
    }
}
