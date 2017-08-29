package it.antani.cc.util;

import net.minecraftforge.fluids.FluidTank;

public class Utils {

    public static Object[] getFluidInfo(FluidTank tank) throws NoSuchFieldException, IllegalAccessException{
        if(tank == null || tank.getFluidAmount() == 0){
            return new Object[]{"empty", 0, 0};
        }
        return new Object[] {
                tank.getFluid().getUnlocalizedName(),
                tank.getFluidAmount(),
                tank.getCapacity()
        };
    }
}
