package it.antani.cc;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import ic2.api.tile.IEnergyStorage;
import ic2.core.block.wiring.TileEntityElectricBlock;
import it.antani.cc.impl.IC2EnergyImpl;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.security.Provider;
import java.util.*;
import java.util.function.Supplier;

public class IndustrialPeripheral implements IPeripheral {

    public enum Types {

        ENERGY(new IC2EnergyImpl())

        ;
        public final IC2MethodProvider provider;
        Types(IC2MethodProvider provider){
            this.provider = provider;
        }
    }


    private TileEntity tileEntity;
    private ArrayList<String> methods;
    private ArrayList<Types> implementations;

    private IndustrialPeripheral(List<Types> providers, TileEntity tileEntity){
        methods = new ArrayList<>();
        implementations = new ArrayList<>();
        this.tileEntity = tileEntity;
        Set<String> visited = new HashSet<>();
        for(Types prov : providers){
            for(String method : prov.provider.getMethods()){
                if(!visited.contains(method)){
                    visited.add(method);
                    methods.add(method);
                    implementations.add(prov);
                }
            }
        }
    }

    @Nonnull
    @Override
    public String getType() {
        return "IndustrialPeripheral";
    }

    @Nonnull
    @Override
    public String[] getMethodNames() {
        // BWEHEHEHE
        return methods.toArray(new String[0]);
    }

    @Nullable
    @Override
    public Object[] callMethod(@Nonnull IComputerAccess iComputerAccess, @Nonnull ILuaContext iLuaContext, int i, @Nonnull Object[] objects) throws LuaException, InterruptedException {
        Types t = implementations.get(i);
        return t.provider.call(methods.get(i), tileEntity, objects);
    }

    @Override
    public void attach(@Nonnull IComputerAccess iComputerAccess) {
        // Nop
    }

    @Override
    public void detach(@Nonnull IComputerAccess iComputerAccess) {
        // Nop
    }

    @Override
    public boolean equals(@Nullable IPeripheral iPeripheral) {
        if(iPeripheral == null || !(iPeripheral instanceof IndustrialPeripheral))
            return false; // of course
        IndustrialPeripheral other = (IndustrialPeripheral) iPeripheral;
        return other.tileEntity.equals(tileEntity);
    }

    static IndustrialPeripheral buildPeripheral(TileEntity te){
        ArrayList<Types> t = new ArrayList<>();
        for(Types prov: Types.values()){
            if(prov.provider.isAcceptable(te)){
                t.add(prov);
            }
        }
        if(t.size() == 0)
            return null;
        return new IndustrialPeripheral(t, te);
    }
}
