package it.antani.cc;

import ic2.core.block.machine.tileentity.TileEntityCondenser;
import ic2.core.block.machine.tileentity.TileEntityFluidRegulator;
import ic2.core.block.wiring.TileEntityElectricBlock;
import it.antani.cc.impl.*;
import net.minecraft.tileentity.TileEntity;

public enum TileEntities {

    ENERGY("Energy Generation", new IC2EnergyImpl()),
    CONDENSER("Condenser", new CondenserImpl()),
    FLUID_REGULATOR("Fluid Regulator", new FluidRegulatorImpl()),
    KINETIC_STEAM_GENERATOR("Kinetic steam generator", new KineticSteamGeneratorImpl()),
    HEAT_EXCHANGER("Heat Exchanger", new HeatExchangerImpl()),
    HEAT_SOURCE("Heat Source", new HeatSourceImpl()),
    ELECTRIC_MACHINE("Electric Machine", new GenericElectricMachineImpl()),
    NUCLEAR_REACTOR("Nuclear reactor", new NuclearReactorImpl()),
    CORE("IC2 Machine", new CoreIc2Impl()),
    STEAM_GENERATOR("Steam generator", new SteamGeneratorImpl()),

    ;
    public final String label;
    public final Object provider;
    TileEntities(String name, Object provider){
        this.label = name;
        this.provider = provider;
    }
}
