package it.antani.cc.impl.ic2;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import ic2.core.block.kineticgenerator.tileentity.TileEntitySteamKineticGenerator;
import it.antani.cc.annotations.AcceptsTileEntity;
import it.antani.cc.annotations.LuaMethod;
import it.antani.cc.util.Utils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidTank;

import java.lang.reflect.Field;

@AcceptsTileEntity(TileEntitySteamKineticGenerator.class)
public class KineticSteamGeneratorImpl {
    @LuaMethod("get_steam_info")
    public Object[] getEnergy(TileEntitySteamKineticGenerator block, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        Field f = TileEntitySteamKineticGenerator.class.getDeclaredField("steamTank");
        f.setAccessible(true);
        return Utils.getFluidInfo((FluidTank) f.get(block));
    }

    @LuaMethod("get_water_info")
    public Object[] getWater(TileEntitySteamKineticGenerator block, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return Utils.getFluidInfo(block.getDistilledWaterTank());
    }

    @LuaMethod("get_output")
    public Object[] getOutput(TileEntitySteamKineticGenerator block, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        return new Object[] { block.getKUoutput() };
    }

    @LuaMethod("get_turbine_damage")
    public Object[] getDamage(TileEntitySteamKineticGenerator block, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        ItemStack turbine =  block.turbineSlot.get();
        if(turbine == null){
            return null;
        }
        return new Object[] { turbine.getItemDamage() };
    }
}
