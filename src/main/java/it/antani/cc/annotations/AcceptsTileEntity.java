package it.antani.cc.annotations;

import net.minecraft.tileentity.TileEntity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotating a class for automatically casting tileentity
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AcceptsTileEntity {
    Class<? extends TileEntity> value();
}
