package it.antani.cc.impl.ic2;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import ic2.core.block.kineticgenerator.tileentity.TileEntitySteamKineticGenerator;
import it.antani.cc.IndustrialPeripheralContext;
import it.antani.cc.annotations.AcceptsTileEntity;
import it.antani.cc.annotations.LuaMethod;
import it.antani.cc.util.Utils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidTank;

import java.lang.reflect.Field;

@AcceptsTileEntity(TileEntitySteamKineticGenerator.class)
public class KineticSteamGeneratorImpl {
    @LuaMethod("get_steam_info")
    public Object[] getEnergy(IndustrialPeripheralContext<TileEntitySteamKineticGenerator> ctx, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        Field f = TileEntitySteamKineticGenerator.class.getDeclaredField("steamTank");
        f.setAccessible(true);
        return Utils.getFluidInfo((FluidTank) f.get(ctx.getTileEntity()));
    }

    @LuaMethod("get_water_info")
    public Object[] getWater(IndustrialPeripheralContext<TileEntitySteamKineticGenerator> ctx, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return Utils.getFluidInfo(ctx.getTileEntity().getDistilledWaterTank());
    }

    @LuaMethod("get_output")
    public Object[] getOutput(IndustrialPeripheralContext<TileEntitySteamKineticGenerator> ctx, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return new Object[] { ctx.getTileEntity().getKUoutput() };
    }

    @LuaMethod("get_turbine_damage")
    public Object[] getDamage(IndustrialPeripheralContext<TileEntitySteamKineticGenerator> ctx, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        ItemStack turbine =  ctx.getTileEntity().turbineSlot.get();
        if(turbine == null){
            return null;
        }
        return new Object[] { turbine.getItemDamage() };
    }
}
