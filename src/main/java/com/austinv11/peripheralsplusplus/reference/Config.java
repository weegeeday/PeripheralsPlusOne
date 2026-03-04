package com.austinv11.peripheralsplusplus.reference;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public class Config {

    private static final String ENABLE_CONFIG_MESSAGE =
            "If disabled, the recipe will be disabled and the current peripherals would cease to work";

    // --- ForgeConfigSpec values ---
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    // Chatbox
    private static final ForgeConfigSpec.BooleanValue ENABLE_CHAT_BOX_V;
    private static final ForgeConfigSpec.BooleanValue LOG_COORDS_V;
    private static final ForgeConfigSpec.DoubleValue READ_RANGE_V;
    private static final ForgeConfigSpec.DoubleValue SAY_RANGE_V;
    private static final ForgeConfigSpec.IntValue SAY_RATE_V;
    private static final ForgeConfigSpec.BooleanValue ALLOW_UNLIMITED_VERTICAL_V;
    private static final ForgeConfigSpec.ConfigValue<String> CHATBOX_COMMAND_PREFIX_V;
    // Player Sensor
    private static final ForgeConfigSpec.BooleanValue ENABLE_PLAYER_SENSOR_V;
    private static final ForgeConfigSpec.BooleanValue ADDITIONAL_METHODS_V;
    private static final ForgeConfigSpec.DoubleValue SENSOR_RANGE_V;
    // RF Charger
    private static final ForgeConfigSpec.BooleanValue ENABLE_RF_CHARGER_V;
    private static final ForgeConfigSpec.IntValue FUEL_RF_V;
    // Turtles
    private static final ForgeConfigSpec.BooleanValue ENABLE_NAVIGATION_TURTLE_V;
    private static final ForgeConfigSpec.BooleanValue ENABLE_XP_TURTLE_V;
    private static final ForgeConfigSpec.BooleanValue ENABLE_BARREL_TURTLE_V;
    private static final ForgeConfigSpec.BooleanValue ENABLE_ORE_DICTIONARY_V;
    private static final ForgeConfigSpec.BooleanValue ORE_DICTIONARY_MESSAGE_V;
    private static final ForgeConfigSpec.BooleanValue ENABLE_SHEAR_TURTLE_V;
    private static final ForgeConfigSpec.BooleanValue ENABLE_ANALYZERS_V;
    private static final ForgeConfigSpec.BooleanValue ENABLE_TURTLE_TELEPORTER_V;
    private static final ForgeConfigSpec.DoubleValue TELEPORTER_PENALTY_V;
    private static final ForgeConfigSpec.BooleanValue ENABLE_ENVIRONMENT_SCANNER_V;
    private static final ForgeConfigSpec.BooleanValue ENABLE_FEEDER_TURTLE_V;
    private static final ForgeConfigSpec.BooleanValue ENABLE_VILLAGERS_V;
    private static final ForgeConfigSpec.BooleanValue ENABLE_RED_POWER_LIKE_TURTLES_V;
    // Speaker
    private static final ForgeConfigSpec.BooleanValue ENABLE_SPEAKER_V;
    private static final ForgeConfigSpec.DoubleValue SPEECH_RANGE_V;
    // Containers
    private static final ForgeConfigSpec.BooleanValue ENABLE_PERIPHERAL_CONTAINER_V;
    private static final ForgeConfigSpec.IntValue MAX_NUMBER_OF_PERIPHERALS_V;
    // ME Bridge
    private static final ForgeConfigSpec.BooleanValue ENABLE_ME_BRIDGE_V;
    // Tank Turtle
    private static final ForgeConfigSpec.BooleanValue ENABLE_TANK_TURTLE_V;
    private static final ForgeConfigSpec.IntValue MAX_NUMBER_OF_MILLIBUCKETS_V;
    // Smart Helmet
    private static final ForgeConfigSpec.BooleanValue ENABLE_SMART_HELMET_V;
    // More turtles
    private static final ForgeConfigSpec.BooleanValue ENABLE_READER_TURTLE_V;
    private static final ForgeConfigSpec.BooleanValue ENABLE_GARDENING_TURTLE_V;
    private static final ForgeConfigSpec.BooleanValue ENABLE_RIDABLE_TURTLE_V;
    private static final ForgeConfigSpec.IntValue FUEL_PER_TURTLE_MOVEMENT_V;
    // Nano Bots
    private static final ForgeConfigSpec.BooleanValue ENABLE_NANO_BOTS_V;
    private static final ForgeConfigSpec.IntValue NUMBER_OF_INSTRUCTIONS_V;
    private static final ForgeConfigSpec.DoubleValue SECONDS_BEFORE_REVERSAL_V;
    // Misc turtles
    private static final ForgeConfigSpec.BooleanValue ENABLE_FLINGING_TURTLE_V;
    private static final ForgeConfigSpec.BooleanValue ENABLE_TIME_SENSOR_V;
    private static final ForgeConfigSpec.BooleanValue ENABLE_CHUNKY_TURTLE_V;
    private static final ForgeConfigSpec.IntValue CHUNK_LOADING_RADIUS_V;
    // Blocks
    private static final ForgeConfigSpec.BooleanValue ENABLE_INTERACTIVE_SORTER_V;
    private static final ForgeConfigSpec.BooleanValue ENABLE_RESUPPLY_STATION_V;
    private static final ForgeConfigSpec.BooleanValue ENABLE_PLAYER_INTERFACE_V;
    private static final ForgeConfigSpec.BooleanValue ENABLE_MOTION_DETECTOR_V;
    private static final ForgeConfigSpec.BooleanValue ENABLE_INTERFACE_PERMISSIONS_V;
    private static final ForgeConfigSpec.BooleanValue ENABLE_MANA_MANIPULATOR_V;
    private static final ForgeConfigSpec.BooleanValue ENABLE_RFID_ITEMS_V;
    private static final ForgeConfigSpec.BooleanValue ENABLE_MAG_STRIP_ITEMS_V;
    private static final ForgeConfigSpec.BooleanValue ENABLE_PRIVACY_GUARD_V;

    static {
        BUILDER.comment("Chatbox Settings").push("Chatbox");
        ENABLE_CHAT_BOX_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enableChatBox", true);
        LOG_COORDS_V = BUILDER.comment("Log the Chat Box peripheral's coordinates when it says a message").define("logCoords", true);
        READ_RANGE_V = BUILDER.comment("Range for reading. Negative values indicate infinite").defineInRange("readRange", -1.0, Double.NEGATIVE_INFINITY, Double.MAX_VALUE);
        SAY_RANGE_V = BUILDER.comment("Range for say/tell. Negative values indicate infinite").defineInRange("sayRange", 64.0, Double.NEGATIVE_INFINITY, Double.MAX_VALUE);
        SAY_RATE_V = BUILDER.comment("Maximum messages per second").defineInRange("sayRate", 1, 1, Integer.MAX_VALUE);
        ALLOW_UNLIMITED_VERTICAL_V = BUILDER.comment("Allow unlimited vertical distance").define("allowUnlimitedVertical", true);
        CHATBOX_COMMAND_PREFIX_V = BUILDER.comment("Prefix for chatbox-only commands").define("chatboxCommandPrefix", "\\");
        BUILDER.pop();

        BUILDER.comment("Player Sensor Settings").push("PlayerSensor");
        ENABLE_PLAYER_SENSOR_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enablePlayerSensor", true);
        ADDITIONAL_METHODS_V = BUILDER.comment("Enables getNearbyPlayers and getAllPlayers").define("additionalMethods", true);
        SENSOR_RANGE_V = BUILDER.comment("Max search range").defineInRange("sensorRange", 64.0, 0.0, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.comment("RF Turtle Charger Settings").push("RFCharger");
        ENABLE_RF_CHARGER_V = BUILDER.comment("If disabled, the recipe will be disabled").define("enableRFCharger", true);
        FUEL_RF_V = BUILDER.comment("Amount of RF per turtle fuel value").defineInRange("fuelRF", 200, 1, Integer.MAX_VALUE);
        BUILDER.pop();

        BUILDER.comment("Turtle Upgrade Settings").push("Turtles");
        ENABLE_NAVIGATION_TURTLE_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enableNavigationTurtle", true);
        ENABLE_XP_TURTLE_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enableXPTurtle", true);
        ENABLE_BARREL_TURTLE_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enableBarrelTurtle", true);
        ENABLE_ORE_DICTIONARY_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enableOreDictionary", true);
        ORE_DICTIONARY_MESSAGE_V = BUILDER.comment("Display chat message with Ore Dictionary entries").define("oreDictionaryMessage", false);
        ENABLE_SHEAR_TURTLE_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enableShearTurtle", true);
        ENABLE_ANALYZERS_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enableAnalyzers", true);
        ENABLE_TURTLE_TELEPORTER_V = BUILDER.comment("If disabled, the recipe will be disabled").define("enableTurtleTeleporter", true);
        TELEPORTER_PENALTY_V = BUILDER.comment("Fuel penalty multiplier for Turtle Teleporter").defineInRange("teleporterPenalty", 2.0, 1.0, Double.MAX_VALUE);
        ENABLE_ENVIRONMENT_SCANNER_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enableEnvironmentScanner", true);
        ENABLE_FEEDER_TURTLE_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enableFeederTurtle", true);
        ENABLE_RED_POWER_LIKE_TURTLES_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enableRedPowerLikeTurtles", true);
        ENABLE_READER_TURTLE_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enableReaderTurtle", true);
        ENABLE_GARDENING_TURTLE_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enableGardeningTurtle", true);
        ENABLE_RIDABLE_TURTLE_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enableRidableTurtle", true);
        FUEL_PER_TURTLE_MOVEMENT_V = BUILDER.comment("Fuel used per turtle movement").defineInRange("fuelPerTurtleMovement", 1, 0, Integer.MAX_VALUE);
        ENABLE_FLINGING_TURTLE_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enableFlingingTurtle", true);
        ENABLE_CHUNKY_TURTLE_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enableChunkyTurtle", true);
        CHUNK_LOADING_RADIUS_V = BUILDER.comment("Radius of loaded chunks (0=1x1, 1=3x3, 2=5x5)").defineInRange("chunkLoadingRadius", 1, 0, 5);
        BUILDER.pop();

        BUILDER.comment("Speaker Settings").push("Speaker");
        ENABLE_SPEAKER_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enableSpeaker", true);
        SPEECH_RANGE_V = BUILDER.comment("Speech range. Negative = infinite").defineInRange("speechRange", 64.0, Double.NEGATIVE_INFINITY, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.comment("Peripheral Container Settings").push("PeripheralContainer");
        ENABLE_PERIPHERAL_CONTAINER_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enablePeripheralContainer", true);
        MAX_NUMBER_OF_PERIPHERALS_V = BUILDER.comment("Max peripherals in container").defineInRange("maxNumberOfPeripherals", 6, 1, Integer.MAX_VALUE);
        BUILDER.pop();

        BUILDER.comment("ME Bridge Settings").push("MEBridge");
        ENABLE_ME_BRIDGE_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enableMEBridge", true);
        BUILDER.pop();

        BUILDER.comment("Tank Turtle Settings").push("TankTurtle");
        ENABLE_TANK_TURTLE_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enableTankTurtle", true);
        MAX_NUMBER_OF_MILLIBUCKETS_V = BUILDER.comment("Max mB stored internally").defineInRange("maxNumberOfMillibuckets", 10000, 1, Integer.MAX_VALUE);
        BUILDER.pop();

        BUILDER.comment("Smart Helmet Settings").push("SmartHelmet");
        ENABLE_SMART_HELMET_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enableSmartHelmet", true);
        BUILDER.pop();

        BUILDER.comment("Nano Bot Settings").push("NanoBots");
        ENABLE_NANO_BOTS_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enableNanoBots", true);
        NUMBER_OF_INSTRUCTIONS_V = BUILDER.comment("Instructions per nano swarm. Negative = infinite").defineInRange("numberOfInstructions", 8, Integer.MIN_VALUE, Integer.MAX_VALUE);
        SECONDS_BEFORE_REVERSAL_V = BUILDER.comment("Seconds before key release. Negative = infinite").defineInRange("secondsBeforeReversal", 5.0, Double.NEGATIVE_INFINITY, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.comment("Time Sensor Settings").push("TimeSensor");
        ENABLE_TIME_SENSOR_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enableTimeSensor", true);
        BUILDER.pop();

        BUILDER.comment("Interactive Sorter Settings").push("InteractiveSorter");
        ENABLE_INTERACTIVE_SORTER_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enableInteractiveSorter", true);
        BUILDER.pop();

        BUILDER.comment("Resupply Station Settings").push("ResupplyStation");
        ENABLE_RESUPPLY_STATION_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enableResupplyStation", true);
        BUILDER.pop();

        BUILDER.comment("Player Interface Settings").push("PlayerInterface");
        ENABLE_PLAYER_INTERFACE_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enablePlayerInterface", true);
        ENABLE_INTERFACE_PERMISSIONS_V = BUILDER.comment("Require permission cards to access inventories").define("enableInterfacePermissions", true);
        BUILDER.pop();

        BUILDER.comment("Motion Detector Settings").push("MotionDetector");
        ENABLE_MOTION_DETECTOR_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enableMotionDetector", true);
        BUILDER.pop();

        BUILDER.comment("Mana Manipulator Settings").push("ManaManipulator");
        ENABLE_MANA_MANIPULATOR_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enableManaManipulator", true);
        BUILDER.pop();

        BUILDER.comment("RFID Item Settings").push("RFIDItems");
        ENABLE_RFID_ITEMS_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enableRfidItems", true);
        BUILDER.pop();

        BUILDER.comment("Mag Strip Item Settings").push("MagStripItems");
        ENABLE_MAG_STRIP_ITEMS_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enableMagStripItems", true);
        BUILDER.pop();

        BUILDER.comment("Privacy Guard Settings").push("PrivacyGuard");
        ENABLE_PRIVACY_GUARD_V = BUILDER.comment(ENABLE_CONFIG_MESSAGE).define("enablePrivacyGuard", true);
        BUILDER.pop();

        BUILDER.comment("Villager Settings").push("Villagers");
        ENABLE_VILLAGERS_V = BUILDER.comment("Whether to enable villagers from this mod").define("enableVillagers", true);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }

    // --- Baked (static) values used throughout the codebase ---
    public static boolean enableChatBox = true;
    public static boolean logCoords = true;
    public static double readRange = -1.0;
    public static double sayRange = 64;
    public static int sayRate = 1;
    public static boolean allowUnlimitedVertical = true;
    public static String chatboxCommandPrefix = "\\";
    public static boolean enablePlayerSensor = true;
    public static boolean additionalMethods = true;
    public static double sensorRange = 64.0;
    public static boolean enableRFCharger = true;
    public static int fuelRF = 200;
    public static boolean enableNavigationTurtle = true;
    public static boolean enableXPTurtle = true;
    public static boolean enableBarrelTurtle = true;
    public static boolean enableOreDictionary = true;
    public static boolean oreDictionaryMessage = false;
    public static boolean enableShearTurtle = true;
    public static boolean enableAnalyzers = true;
    public static boolean enableTurtleTeleporter = true;
    public static double teleporterPenalty = 2.0;
    public static boolean enableEnvironmentScanner = true;
    public static boolean enableFeederTurtle = true;
    public static boolean enableVillagers = true;
    public static boolean enableRedPowerLikeTurtles = true;
    public static boolean enableSpeaker = true;
    public static double speechRange = 64;
    public static boolean enableAPIs = true;
    public static boolean enablePeripheralContainer = true;
    public static int maxNumberOfPeripherals = 6;
    public static boolean enableMEBridge = true;
    public static boolean enableTankTurtle = true;
    public static int maxNumberOfMillibuckets = 10000;
    public static boolean enableSmartHelmet = true;
    public static boolean enableReaderTurtle = true;
    public static boolean enableGardeningTurtle = true;
    public static boolean enableRidableTurtle = true;
    public static int fuelPerTurtleMovement = 1;
    public static boolean enableNanoBots = true;
    public static int numberOfInstructions = 8;
    public static double secondsBeforeReversal = 5.0;
    public static boolean enableFlingingTurtle = true;
    public static boolean enableTimeSensor = true;
    public static boolean enableChunkyTurtle = true;
    public static int chunkLoadingRadius = 1;
    public static boolean enableInteractiveSorter = true;
    public static boolean enableResupplyStation = true;
    public static boolean enablePlayerInterface = true;
    public static boolean enableMotionDetector = true;
    public static boolean enableInterfacePermissions = true;
    public static boolean enableManaManipulator = true;
    public static boolean enableRfidItems = true;
    public static boolean enableMagStripItems = true;
    public static boolean enablePrivacyGuard = true;

    /** Called when the config is loaded/reloaded to update the baked static values. */
    public static void bake() {
        enableChatBox = ENABLE_CHAT_BOX_V.get();
        logCoords = LOG_COORDS_V.get();
        readRange = READ_RANGE_V.get();
        sayRange = SAY_RANGE_V.get();
        sayRate = SAY_RATE_V.get();
        allowUnlimitedVertical = ALLOW_UNLIMITED_VERTICAL_V.get();
        chatboxCommandPrefix = CHATBOX_COMMAND_PREFIX_V.get();
        enablePlayerSensor = ENABLE_PLAYER_SENSOR_V.get();
        additionalMethods = ADDITIONAL_METHODS_V.get();
        sensorRange = SENSOR_RANGE_V.get();
        enableRFCharger = ENABLE_RF_CHARGER_V.get();
        fuelRF = FUEL_RF_V.get();
        enableNavigationTurtle = ENABLE_NAVIGATION_TURTLE_V.get();
        enableXPTurtle = ENABLE_XP_TURTLE_V.get();
        enableBarrelTurtle = ENABLE_BARREL_TURTLE_V.get();
        enableOreDictionary = ENABLE_ORE_DICTIONARY_V.get();
        oreDictionaryMessage = ORE_DICTIONARY_MESSAGE_V.get();
        enableShearTurtle = ENABLE_SHEAR_TURTLE_V.get();
        enableAnalyzers = ENABLE_ANALYZERS_V.get();
        enableTurtleTeleporter = ENABLE_TURTLE_TELEPORTER_V.get();
        teleporterPenalty = TELEPORTER_PENALTY_V.get();
        enableEnvironmentScanner = ENABLE_ENVIRONMENT_SCANNER_V.get();
        enableFeederTurtle = ENABLE_FEEDER_TURTLE_V.get();
        enableVillagers = ENABLE_VILLAGERS_V.get();
        enableRedPowerLikeTurtles = ENABLE_RED_POWER_LIKE_TURTLES_V.get();
        enableSpeaker = ENABLE_SPEAKER_V.get();
        speechRange = SPEECH_RANGE_V.get();
        enablePeripheralContainer = ENABLE_PERIPHERAL_CONTAINER_V.get();
        maxNumberOfPeripherals = MAX_NUMBER_OF_PERIPHERALS_V.get();
        enableMEBridge = ENABLE_ME_BRIDGE_V.get();
        enableTankTurtle = ENABLE_TANK_TURTLE_V.get();
        maxNumberOfMillibuckets = MAX_NUMBER_OF_MILLIBUCKETS_V.get();
        enableSmartHelmet = ENABLE_SMART_HELMET_V.get();
        enableReaderTurtle = ENABLE_READER_TURTLE_V.get();
        enableGardeningTurtle = ENABLE_GARDENING_TURTLE_V.get();
        enableRidableTurtle = ENABLE_RIDABLE_TURTLE_V.get();
        fuelPerTurtleMovement = FUEL_PER_TURTLE_MOVEMENT_V.get();
        enableNanoBots = ENABLE_NANO_BOTS_V.get();
        numberOfInstructions = NUMBER_OF_INSTRUCTIONS_V.get();
        secondsBeforeReversal = SECONDS_BEFORE_REVERSAL_V.get();
        enableFlingingTurtle = ENABLE_FLINGING_TURTLE_V.get();
        enableTimeSensor = ENABLE_TIME_SENSOR_V.get();
        enableChunkyTurtle = ENABLE_CHUNKY_TURTLE_V.get();
        chunkLoadingRadius = CHUNK_LOADING_RADIUS_V.get();
        enableInteractiveSorter = ENABLE_INTERACTIVE_SORTER_V.get();
        enableResupplyStation = ENABLE_RESUPPLY_STATION_V.get();
        enablePlayerInterface = ENABLE_PLAYER_INTERFACE_V.get();
        enableMotionDetector = ENABLE_MOTION_DETECTOR_V.get();
        enableInterfacePermissions = ENABLE_INTERFACE_PERMISSIONS_V.get();
        enableManaManipulator = ENABLE_MANA_MANIPULATOR_V.get();
        enableRfidItems = ENABLE_RFID_ITEMS_V.get();
        enableMagStripItems = ENABLE_MAG_STRIP_ITEMS_V.get();
        enablePrivacyGuard = ENABLE_PRIVACY_GUARD_V.get();
    }

    /** Listens for config load/reload events and rebakes static values. */
    public static void onLoad(ModConfigEvent event) {
        if (event.getConfig().getSpec() == SPEC) {
            bake();
        }
    }
}
