package com.austinv11.peripheralsplusplus.init;

import com.austinv11.peripheralsplusplus.pocket.*;
import com.austinv11.peripheralsplusplus.reference.Reference;
import com.austinv11.peripheralsplusplus.turtles.*;
import dan200.computercraft.api.pocket.PocketUpgradeSerialiser;
import dan200.computercraft.api.turtle.TurtleUpgradeSerialiser;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.eventbus.api.IEventBus;

public class ModUpgrades {

    public static final DeferredRegister<TurtleUpgradeSerialiser<?>> TURTLE_SERIALISERS =
            DeferredRegister.create(TurtleUpgradeSerialiser.registryId(), Reference.MOD_ID);

    public static final DeferredRegister<PocketUpgradeSerialiser<?>> POCKET_SERIALISERS =
            DeferredRegister.create(PocketUpgradeSerialiser.registryId(), Reference.MOD_ID);

    // Turtle upgrade serialisers
    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleChatBox>> CHAT_BOX =
            TURTLE_SERIALISERS.register("chat_box",
                    () -> TurtleUpgradeSerialiser.simple(TurtleChatBox::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtlePlayerSensor>> PLAYER_SENSOR =
            TURTLE_SERIALISERS.register("player_sensor",
                    () -> TurtleUpgradeSerialiser.simple(TurtlePlayerSensor::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleCompass>> COMPASS =
            TURTLE_SERIALISERS.register("compass",
                    () -> TurtleUpgradeSerialiser.simple(TurtleCompass::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleXP>> XP =
            TURTLE_SERIALISERS.register("xp",
                    () -> TurtleUpgradeSerialiser.simple(TurtleXP::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleBarrel>> BARREL =
            TURTLE_SERIALISERS.register("barrel",
                    () -> TurtleUpgradeSerialiser.simple(TurtleBarrel::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleOreDictionary>> ORE_DICTIONARY =
            TURTLE_SERIALISERS.register("ore_dictionary",
                    () -> TurtleUpgradeSerialiser.simple(TurtleOreDictionary::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleEnvironmentScanner>> ENVIRONMENT_SCANNER =
            TURTLE_SERIALISERS.register("environment_scanner",
                    () -> TurtleUpgradeSerialiser.simple(TurtleEnvironmentScanner::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleFeeder>> FEEDER =
            TURTLE_SERIALISERS.register("feeder",
                    () -> TurtleUpgradeSerialiser.simple(TurtleFeeder::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleShear>> SHEAR =
            TURTLE_SERIALISERS.register("shear",
                    () -> TurtleUpgradeSerialiser.simple(TurtleShear::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleSignReader>> SIGN_READER =
            TURTLE_SERIALISERS.register("sign_reader",
                    () -> TurtleUpgradeSerialiser.simple(TurtleSignReader::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleGarden>> GARDEN =
            TURTLE_SERIALISERS.register("garden",
                    () -> TurtleUpgradeSerialiser.simple(TurtleGarden::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleSpeaker>> SPEAKER =
            TURTLE_SERIALISERS.register("speaker",
                    () -> TurtleUpgradeSerialiser.simple(TurtleSpeaker::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleTank>> TANK =
            TURTLE_SERIALISERS.register("tank",
                    () -> TurtleUpgradeSerialiser.simple(TurtleTank::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleRidable>> RIDABLE =
            TURTLE_SERIALISERS.register("ridable",
                    () -> TurtleUpgradeSerialiser.simple(TurtleRidable::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleDispenser>> DISPENSER =
            TURTLE_SERIALISERS.register("dispenser",
                    () -> TurtleUpgradeSerialiser.simple(TurtleDispenser::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleResupply>> RESUPPLY =
            TURTLE_SERIALISERS.register("resupply",
                    () -> TurtleUpgradeSerialiser.simple(TurtleResupply::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleChunkLoader>> CHUNK_LOADER =
            TURTLE_SERIALISERS.register("chunk_loader",
                    () -> TurtleUpgradeSerialiser.simple(TurtleChunkLoader::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleRfid>> RFID =
            TURTLE_SERIALISERS.register("rfid",
                    () -> TurtleUpgradeSerialiser.simple(TurtleRfid::new));

    // Pocket upgrade serialisers
    public static final RegistryObject<PocketUpgradeSerialiser<PocketMotionDetector>> POCKET_MOTION_DETECTOR =
            POCKET_SERIALISERS.register("pocket_motion_detector",
                    () -> PocketUpgradeSerialiser.simple(PocketMotionDetector::new));

    public static final RegistryObject<PocketUpgradeSerialiser<PocketPeripheralContainer>> POCKET_PERIPHERAL_CONTAINER =
            POCKET_SERIALISERS.register("pocket_container",
                    () -> PocketUpgradeSerialiser.simple(PocketPeripheralContainer::new));

    public static final RegistryObject<PocketUpgradeSerialiser<PocketRfid>> POCKET_RFID =
            POCKET_SERIALISERS.register("pocket_rfid",
                    () -> PocketUpgradeSerialiser.simple(PocketRfid::new));

    public static void register(IEventBus modBus) {
        TURTLE_SERIALISERS.register(modBus);
        POCKET_SERIALISERS.register(modBus);
    }
}
