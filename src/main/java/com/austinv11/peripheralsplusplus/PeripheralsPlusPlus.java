package com.austinv11.peripheralsplusplus;

import com.austinv11.peripheralsplusplus.creativetab.CreativeTabPPP;
import com.austinv11.peripheralsplusplus.init.ModBlocks;
import com.austinv11.peripheralsplusplus.init.ModItems;
import com.austinv11.peripheralsplusplus.init.ModPeripherals;
import com.austinv11.peripheralsplusplus.init.ModTileEntities;
import com.austinv11.peripheralsplusplus.network.*;
import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.reference.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import com.austinv11.peripheralsplusplus.capabilities.nano.CapabilityNanoBot;
import com.austinv11.peripheralsplusplus.capabilities.nano.NanoBotHolder;
import com.austinv11.peripheralsplusplus.capabilities.rfid.CapabilityRfid;
import com.austinv11.peripheralsplusplus.capabilities.rfid.RfidTagHolder;
import com.austinv11.peripheralsplusplus.event.handler.CapabilitiesHandler;
import net.minecraftforge.event.RegisterCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

@Mod(Reference.MOD_ID)
public class PeripheralsPlusPlus {

    public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel NETWORK = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Reference.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals);

    public PeripheralsPlusPlus() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        // Wire deferred registers
        ModBlocks.BLOCKS.register(modBus);
        ModItems.ITEMS.register(modBus);
        ModTileEntities.TILE_ENTITIES.register(modBus);
        CreativeTabPPP.TABS.register(modBus);

        modBus.addListener(this::commonSetup);
        modBus.addListener(this::clientSetup);
        modBus.addListener(Config::onLoad);
        modBus.addListener(this::registerCapabilities);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(NanoBotHolder.class);
        event.register(RfidTagHolder.class);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new CapabilitiesHandler());
        // Register network packets
        int id = 0;
        NETWORK.registerMessage(id++, ChatPacket.class, ChatPacket::encode, ChatPacket::decode, ChatPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        NETWORK.registerMessage(id++, ParticlePacket.class, ParticlePacket::encode, ParticlePacket::decode, ParticlePacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        NETWORK.registerMessage(id++, CommandPacket.class, CommandPacket::encode, CommandPacket::decode, CommandPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        NETWORK.registerMessage(id++, GuiPacket.class, GuiPacket::encode, GuiPacket::decode, GuiPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        NETWORK.registerMessage(id++, InputEventPacket.class, InputEventPacket::encode, InputEventPacket::decode, InputEventPacket::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        NETWORK.registerMessage(id++, PermCardChangePacket.class, PermCardChangePacket::encode, PermCardChangePacket::decode, PermCardChangePacket::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        NETWORK.registerMessage(id++, RidableTurtlePacket.class, RidableTurtlePacket::encode, RidableTurtlePacket::decode, RidableTurtlePacket::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        NETWORK.registerMessage(id++, RobotEventPacket.class, RobotEventPacket::encode, RobotEventPacket::decode, RobotEventPacket::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        NETWORK.registerMessage(id++, ScaleRequestPacket.class, ScaleRequestPacket::encode, ScaleRequestPacket::decode, ScaleRequestPacket::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        NETWORK.registerMessage(id++, ScaleRequestResponsePacket.class, ScaleRequestResponsePacket::encode, ScaleRequestResponsePacket::decode, ScaleRequestResponsePacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        NETWORK.registerMessage(id++, SynthPacket.class, SynthPacket::encode, SynthPacket::decode, SynthPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        NETWORK.registerMessage(id++, SynthResponsePacket.class, SynthResponsePacket::encode, SynthResponsePacket::decode, SynthResponsePacket::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        NETWORK.registerMessage(id++, TextFieldInputEventPacket.class, TextFieldInputEventPacket::encode, TextFieldInputEventPacket::decode, TextFieldInputEventPacket::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));

        ModPeripherals.registerInternally();
        ModPeripherals.registerWithComputerCraft();
        LOGGER.info("PeripheralsPlusOne: peripherals and turtle upgrades registered.");
    }

    private void clientSetup(FMLClientSetupEvent event) {
        // Client-specific setup; renderers etc. will be added in Phase 3
    }
}
