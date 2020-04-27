package it.antani.cc;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import it.antani.cc.annotations.AcceptsTileEntity;
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
        IndustrialPeripheralContext context = new IndustrialPeripheralContext(world, blockPos, enumFacing, te);


        List<TileEntities> implementations = new ArrayList<>();
        if(te != null){
            for(TileEntities entity : TileEntities.values()){
                Class<? extends TileEntity> cl = entity.provider.getClass().getAnnotation(AcceptsTileEntity.class).value();
                if(cl.isInstance(te)){
                    implementations.add(entity);
                }
            }
            if(implementations.size() > 0){
                return IndustrialPeripheral.buildPeripheral(context, implementations);
            }
        }
        return null;
    }
}
