package it.antani.cc.impl.ic2;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import ic2.core.block.comp.Energy;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import it.antani.cc.annotations.AcceptsTileEntity;
import it.antani.cc.annotations.LuaMethod;

import java.lang.reflect.Field;

@AcceptsTileEntity(TileEntityElectricMachine.class)
public class GenericElectricMachineImpl {

    @LuaMethod("get_energy_info")
    public Object[] getOutputMb(TileEntityElectricMachine te, IComputerAccess access, ILuaContext context, Object[] args) throws NoSuchFieldException, IllegalAccessException {
        Field f = TileEntityElectricMachine.class.getDeclaredField("energy");
        f.setAccessible(true);
        Energy e = (Energy) f.get(te);
        return new Object[] { e.getEnergy(), e.getCapacity(), e.getSinkTier(), e.getSourceTier() };
    }
}
