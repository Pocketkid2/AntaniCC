package it.antani.cc;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class IndustrialPeripheralProvider implements IPeripheralProvider {


    @Nullable
    @Override
    public IPeripheral getPeripheral(@Nonnull World world, @Nonnull BlockPos blockPos, @Nonnull EnumFacing enumFacing) {
        TileEntity te = world.getTileEntity(blockPos);
        List<TileEntities> implementations = new ArrayList<>();
        if(te != null){
            for(TileEntities entity : TileEntities.values()){
                if(entity.type.isInstance(te)){
                    implementations.add(entity);
                }
            }
            if(implementations.size() > 0){
                return IndustrialPeripheral.buildPeripheral(te, implementations);
            }
        }
        return null;
    }
}
