package it.antani.cc.util;

import dan200.computercraft.api.lua.ILuaTask;
import dan200.computercraft.api.lua.LuaException;

import javax.annotation.Nullable;
import java.lang.reflect.Field;

public class LuaValueSetter implements ILuaTask {

    private final Field field;
    private final Object instance;
    private final Object value;

    public LuaValueSetter(Field field, Object instance, Object value) {
        this.field = field;
        this.instance = instance;
        this.value = value;
    }

    @Nullable
    @Override
    public Object[] execute() throws LuaException {
        if(instance == null){
            return null;
        }
        field.setAccessible(true);
        try {
            field.set(instance, value);
        }catch (IllegalAccessException e){
            throw new LuaException("Failed to set value of " + field.getName() + " for entity of type " + instance.getClass().getName());
        }
        return null;
    }
}
