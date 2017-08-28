package it.antani.cc;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import ic2.api.tile.IEnergyStorage;
import ic2.core.block.wiring.TileEntityElectricBlock;
import it.antani.cc.annotations.AcceptsTileEntity;
import it.antani.cc.annotations.LuaMethod;
import it.antani.cc.impl.IC2EnergyImpl;
import net.minecraft.tileentity.TileEntity;
import org.omg.PortableInterceptor.INACTIVE;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.Provider;
import java.util.*;
import java.util.function.Supplier;

public class IndustrialPeripheral implements InvocationHandler {
    public static class LuaInvocation {
        final Object obj;
        final Method method;
        final Class<? extends TileEntity> tileEntityType;

        LuaInvocation(Object obj, Method method, Class<? extends TileEntity> castType) {
            this.obj = obj;
            this.method = method;
            this.tileEntityType = castType;
        }
    }

    private TileEntity te;
    private Map<String, LuaInvocation> index;
    private String[] method_index;
    private String[] attribute_index;

    private IndustrialPeripheral(TileEntity entity, List<TileEntities> impl) {
        this.te = entity;
        index = new LinkedHashMap<>();
        List<String> attribute_index_builder = new ArrayList<>();
        List<String> method_index_builder = new ArrayList<>();
        method_index_builder.add("get_capabilities");
        for(TileEntities t : impl){
            attribute_index_builder.add(t.label);
            Class<? extends TileEntity> castTo = t.provider.getClass().getAnnotation(AcceptsTileEntity.class).value();
            for(Method m : t.provider.getClass().getMethods()){
                if(m.isAnnotationPresent(LuaMethod.class)){
                    LuaMethod method = m.getAnnotation(LuaMethod.class);
                    if(!index.containsKey(m.getName())){
                        method_index_builder.add(method.value());
                        index.put(method.value(), new LuaInvocation(t.provider, m, castTo));
                    }
                }
            }
        }
        attribute_index = attribute_index_builder.toArray(new String[attribute_index_builder.size()]);
        method_index = method_index_builder.toArray(new String[method_index_builder.size()]);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        switch (method.getName()){
            case "getTileEntity":
                return te;
            case "equals":
                assert args.length == 1;
                return this.peripheralEquals((IndustrialPeripheralApi) proxy, (IPeripheral) args[0]);
            case "getType":
                return "IndustrialPeripheral";
            case "attach":
            case "detach":
                return null;
            case "getMethodNames":
                return getMethodNames();
            case "callMethod":
                return callLua((IComputerAccess) args[0], (ILuaContext) args[1], (int) args[2], (Object[]) args[3]);
        }
        return null;
    }

    private String[] getMethodNames(){
        return method_index;
    }

    private Object[] callLua(IComputerAccess iComputerAccess, ILuaContext iLuaContext, int method, Object[] args) throws Throwable{
        if(method == 0){ // capabilities
            return attribute_index;
        }
        LuaInvocation invocation = index.get(method_index[method]);
        return (Object[]) invocation.method.invoke(
                invocation.obj,
                invocation.tileEntityType.cast(te),
                iComputerAccess,
                iLuaContext,
                args
        );
    }

    private boolean peripheralEquals(@Nonnull IndustrialPeripheralApi self, @Nullable IPeripheral iPeripheral) {
        if(iPeripheral == null || !(iPeripheral instanceof IndustrialPeripheralApi))
            return false; // of course
        IndustrialPeripheralApi other = (IndustrialPeripheralApi) iPeripheral;
        return other.getTileEntity().equals(self.getTileEntity());
    }

    static IPeripheral buildPeripheral(TileEntity te, List<TileEntities> implementations){
        return (IndustrialPeripheralApi) Proxy.newProxyInstance(
                IndustrialPeripheral.class.getClassLoader(),
                new Class[] { IndustrialPeripheralApi.class },
                new IndustrialPeripheral(te, implementations)
        );
    }
}
