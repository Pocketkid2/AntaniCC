package it.antani.cc.impl;

import dan200.computercraft.api.lua.LuaException;
import ic2.core.block.wiring.TileEntityElectricBlock;
import it.antani.cc.AntaniCCMod;
import it.antani.cc.IC2MethodProvider;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IC2EnergyImpl implements IC2MethodProvider {
    @Override
    public List<String> getMethods() {
        return Arrays.asList(
                "get_energy",
                "get_capacity",
                "get_output"
        );
    }

    @Override
    public Object[] call(String method, TileEntity tileEntity, Object[] params) throws LuaException {
        ArrayList<Object> result = new ArrayList<>();
        TileEntityElectricBlock te;
        try {
            te = (TileEntityElectricBlock) tileEntity;
            switch (method){
                case "get_energy":
                    result.add(te.energy.getEnergy());
                    break;
                case "get_capacity":
                    result.add(te.getCapacity());
                    break;
                case "get_output":
                    result.add(te.getOutput());
                    break;
            };
        } catch (Exception e) {
            AntaniCCMod.logger.error("Error while executing IC2 te code.", e);
            throw new LuaException("Error interacting with IC2");
        }
        return result.toArray();
    }

    @Override
    public boolean isAcceptable(TileEntity tileEntity) {
        return tileEntity instanceof TileEntityElectricBlock;
    }
}
