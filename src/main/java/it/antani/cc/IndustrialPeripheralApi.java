package it.antani.cc;

import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

public interface IndustrialPeripheralApi extends IPeripheral {
    TileEntity getTileEntity();
}
