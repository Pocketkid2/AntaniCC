package it.antani.cc;

import it.antani.cc.impl.ic2.*;

public enum TileEntities {

    ENERGY("Energy Generation", new IC2EnergyImpl()),
    CONDENSER("Condenser", new CondenserImpl()),
    FLUID_REGULATOR("Fluid Regulator", new FluidRegulatorImpl()),
    KINETIC_STEAM_GENERATOR("Kinetic steam generator", new KineticSteamGeneratorImpl()),
    HEAT_EXCHANGER("Heat Exchanger", new HeatExchangerImpl()),
    HEAT_SOURCE("Heat Source", new HeatSourceImpl()),
    ELECTRIC_MACHINE("Electric Machine", new GenericElectricMachineImpl()),
    NUCLEAR_REACTOR("Nuclear reactor", new NuclearReactorImpl()),
    REACTOR_CHAMBER("Reactor chamber", new NuclearReactorChamberImpl()),
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
