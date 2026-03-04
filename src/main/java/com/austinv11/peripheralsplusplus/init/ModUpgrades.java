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
                    () -> TurtleUpgradeSerialiser.simpleSerialiser(TurtleChatBox::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtlePlayerSensor>> PLAYER_SENSOR =
            TURTLE_SERIALISERS.register("player_sensor",
                    () -> TurtleUpgradeSerialiser.simpleSerialiser(TurtlePlayerSensor::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleCompass>> COMPASS =
            TURTLE_SERIALISERS.register("compass",
                    () -> TurtleUpgradeSerialiser.simpleSerialiser(TurtleCompass::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleXP>> XP =
            TURTLE_SERIALISERS.register("xp",
                    () -> TurtleUpgradeSerialiser.simpleSerialiser(TurtleXP::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleBarrel>> BARREL =
            TURTLE_SERIALISERS.register("barrel",
                    () -> TurtleUpgradeSerialiser.simpleSerialiser(TurtleBarrel::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleOreDictionary>> ORE_DICTIONARY =
            TURTLE_SERIALISERS.register("ore_dictionary",
                    () -> TurtleUpgradeSerialiser.simpleSerialiser(TurtleOreDictionary::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleEnvironmentScanner>> ENVIRONMENT_SCANNER =
            TURTLE_SERIALISERS.register("environment_scanner",
                    () -> TurtleUpgradeSerialiser.simpleSerialiser(TurtleEnvironmentScanner::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleFeeder>> FEEDER =
            TURTLE_SERIALISERS.register("feeder",
                    () -> TurtleUpgradeSerialiser.simpleSerialiser(TurtleFeeder::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleShear>> SHEAR =
            TURTLE_SERIALISERS.register("shear",
                    () -> TurtleUpgradeSerialiser.simpleSerialiser(TurtleShear::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleSignReader>> SIGN_READER =
            TURTLE_SERIALISERS.register("sign_reader",
                    () -> TurtleUpgradeSerialiser.simpleSerialiser(TurtleSignReader::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleGarden>> GARDEN =
            TURTLE_SERIALISERS.register("garden",
                    () -> TurtleUpgradeSerialiser.simpleSerialiser(TurtleGarden::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleSpeaker>> SPEAKER =
            TURTLE_SERIALISERS.register("speaker",
                    () -> TurtleUpgradeSerialiser.simpleSerialiser(TurtleSpeaker::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleTank>> TANK =
            TURTLE_SERIALISERS.register("tank",
                    () -> TurtleUpgradeSerialiser.simpleSerialiser(TurtleTank::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleRidable>> RIDABLE =
            TURTLE_SERIALISERS.register("ridable",
                    () -> TurtleUpgradeSerialiser.simpleSerialiser(TurtleRidable::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleDispenser>> DISPENSER =
            TURTLE_SERIALISERS.register("dispenser",
                    () -> TurtleUpgradeSerialiser.simpleSerialiser(TurtleDispenser::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleResupply>> RESUPPLY =
            TURTLE_SERIALISERS.register("resupply",
                    () -> TurtleUpgradeSerialiser.simpleSerialiser(TurtleResupply::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleChunkLoader>> CHUNK_LOADER =
            TURTLE_SERIALISERS.register("chunk_loader",
                    () -> TurtleUpgradeSerialiser.simpleSerialiser(TurtleChunkLoader::new));

    public static final RegistryObject<TurtleUpgradeSerialiser<TurtleRfid>> RFID =
            TURTLE_SERIALISERS.register("rfid",
                    () -> TurtleUpgradeSerialiser.simpleSerialiser(TurtleRfid::new));

    // Pocket upgrade serialisers
    public static final RegistryObject<PocketUpgradeSerialiser<PocketMotionDetector>> POCKET_MOTION_DETECTOR =
            POCKET_SERIALISERS.register("pocket_motion_detector",
                    () -> PocketUpgradeSerialiser.simpleSerialiser(PocketMotionDetector::new));

    public static final RegistryObject<PocketUpgradeSerialiser<PocketPeripheralContainer>> POCKET_PERIPHERAL_CONTAINER =
            POCKET_SERIALISERS.register("pocket_container",
                    () -> PocketUpgradeSerialiser.simpleSerialiser(PocketPeripheralContainer::new));

    public static final RegistryObject<PocketUpgradeSerialiser<PocketRfid>> POCKET_RFID =
            POCKET_SERIALISERS.register("pocket_rfid",
                    () -> PocketUpgradeSerialiser.simpleSerialiser(PocketRfid::new));

    public static void register(IEventBus modBus) {
        TURTLE_SERIALISERS.register(modBus);
        POCKET_SERIALISERS.register(modBus);
    }
}
