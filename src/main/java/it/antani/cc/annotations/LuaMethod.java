package it.antani.cc.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotating a method with this makes it to be called with the following params in this order
 * @param <? extends TileEntity> tileEntity
 * @param IComputerAccess iComputerAccess
 * @param ILuaContext iLuaContext
 * @param Object[] objects
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface LuaMethod {
    String value();
}
