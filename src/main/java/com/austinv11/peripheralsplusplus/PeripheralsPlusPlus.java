package com.austinv11.peripheralsplusplus;

import com.austinv11.peripheralsplusplus.creativetab.CreativeTabPPP;
import com.austinv11.peripheralsplusplus.init.ModBlocks;
import com.austinv11.peripheralsplusplus.init.ModItems;
import com.austinv11.peripheralsplusplus.init.ModPeripherals;
import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.reference.Reference;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Reference.MOD_ID)
public class PeripheralsPlusPlus {

    public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);

    public PeripheralsPlusPlus() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        // Wire deferred registers
        ModBlocks.BLOCKS.register(modBus);
        ModItems.ITEMS.register(modBus);
        CreativeTabPPP.TABS.register(modBus);

        modBus.addListener(this::commonSetup);
        modBus.addListener(this::clientSetup);
        modBus.addListener(Config::onLoad);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        ModPeripherals.registerInternally();
        ModPeripherals.registerWithComputerCraft();
        LOGGER.info("PeripheralsPlusOne: peripherals and turtle upgrades registered.");
    }

    private void clientSetup(FMLClientSetupEvent event) {
        // Client-specific setup; renderers etc. will be added in Phase 3
    }
}
