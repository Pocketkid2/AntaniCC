package it.antani.cc;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class IndustrialPeripheralContext<T> {
    private final World world;
    private final BlockPos peripheralPosition;
    private final BlockPos selfPosition;
    private final EnumFacing peripheralFacing;
    private final T tileEntity;

    public IndustrialPeripheralContext(World world, BlockPos peripheralPosition, EnumFacing peripheralFacing, T tileEntity) {
        this.world = world;
        this.peripheralPosition = peripheralPosition;
        this.selfPosition = peripheralPosition.offset(peripheralFacing);
        this.peripheralFacing = peripheralFacing;
        this.tileEntity = tileEntity;
    }

    public World getWorld() {
        return world;
    }

    public BlockPos getPeripheralPosition() {
        return peripheralPosition;
    }

    public BlockPos getSelfPosition() {
        return selfPosition;
    }

    public EnumFacing getPeripheralFacing() {
        return peripheralFacing;
    }

    public T getTileEntity() {
        return tileEntity;
    }

    public static <O> IndustrialPeripheralContext<O> forTileEntity(IndustrialPeripheralContext<?> o, Class<O> clazz) {
            return (IndustrialPeripheralContext<O>) o;
    }
}
