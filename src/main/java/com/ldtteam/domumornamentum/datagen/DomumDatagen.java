package com.ldtteam.domumornamentum.datagen;

import com.ldtteam.domumornamentum.event.handlers.ModBusEventHandler;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DomumDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
        ModBusEventHandler.dataGeneratorSetup(dataGenerator);
    }
}
