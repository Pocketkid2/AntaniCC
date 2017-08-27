package it.antani.cc;

import dan200.computercraft.api.lua.LuaException;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

public interface IC2MethodProvider {
    /**
     * Gets a list of methods supported by this provider
     * @return a list of methods callable on the peripheral
     */
    List<String> getMethods();

    Object[] call(String method, TileEntity tileEntity, Object[] param) throws LuaException;

    boolean isAcceptable(TileEntity tileEntity);

}
