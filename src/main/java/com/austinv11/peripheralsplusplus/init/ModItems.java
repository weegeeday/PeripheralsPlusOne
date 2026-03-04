package com.austinv11.peripheralsplusplus.init;

import com.austinv11.peripheralsplusplus.items.*;
import com.austinv11.peripheralsplusplus.reference.Reference;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);

    // Standalone items
    public static final RegistryObject<ItemFeederUpgrade> FEEDER_UPGRADE =
            ITEMS.register("feeder_upgrade", () -> new ItemFeederUpgrade(new Item.Properties()));
    public static final RegistryObject<ItemTank> TANK =
            ITEMS.register("tank", () -> new ItemTank(new Item.Properties()));
    public static final RegistryObject<ItemSmartHelmet> SMART_HELMET =
            ITEMS.register("smart_helmet", () -> new ItemSmartHelmet(new Item.Properties()));
    public static final RegistryObject<ItemNanoSwarm> NANO_SWARM =
            ITEMS.register("nano_swarm", () -> new ItemNanoSwarm(new Item.Properties()));
    public static final RegistryObject<ItemChunkLoaderUpgrade> CHUNK_LOADER_UPGRADE =
            ITEMS.register("chunk_loader_upgrade", () -> new ItemChunkLoaderUpgrade(new Item.Properties()));
    public static final RegistryObject<ItemPermissionsCard> PERM_CARD =
            ITEMS.register("permissions_card", () -> new ItemPermissionsCard(new Item.Properties()));
    public static final RegistryObject<ItemResupplyUpgrade> RESUPPLY_UPGRADE =
            ITEMS.register("resupply_upgrade", () -> new ItemResupplyUpgrade(new Item.Properties()));
    public static final RegistryObject<ItemMotionDetector> MOTION_DETECTOR =
            ITEMS.register("motion_detector", () -> new ItemMotionDetector(new Item.Properties()));
    public static final RegistryObject<ItemRfidChip> RFID_CHIP =
            ITEMS.register("rfid_chip", () -> new ItemRfidChip(new Item.Properties()));
    public static final RegistryObject<ItemPlasticCard> PLASTIC_CARD =
            ITEMS.register("plastic_card", () -> new ItemPlasticCard(new Item.Properties()));
    public static final RegistryObject<ItemFork> FORK =
            ITEMS.register("fork", () -> new ItemFork(new Item.Properties()));

    // BlockItem registrations
    public static final RegistryObject<ItemBlockTurtle> TURTLE =
            ITEMS.register("turtle", () -> new ItemBlockTurtle(ModBlocks.TURTLE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> CHAT_BOX =
            ITEMS.register("chat_box", () -> new BlockItem(ModBlocks.CHAT_BOX.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> PLAYER_SENSOR =
            ITEMS.register("player_sensor", () -> new BlockItem(ModBlocks.PLAYER_SENSOR.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> ORE_DICTIONARY =
            ITEMS.register("ore_dictionary", () -> new BlockItem(ModBlocks.ORE_DICTIONARY.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> ENVIRONMENT_SCANNER =
            ITEMS.register("environment_scanner", () -> new BlockItem(ModBlocks.ENVIRONMENT_SCANNER.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> SPEAKER =
            ITEMS.register("speaker", () -> new BlockItem(ModBlocks.SPEAKER.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> RESUPPLY_STATION =
            ITEMS.register("resupply_station", () -> new BlockItem(ModBlocks.RESUPPLY_STATION.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> ANTENNA =
            ITEMS.register("antenna", () -> new BlockItem(ModBlocks.ANTENNA.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> RF_CHARGER =
            ITEMS.register("rf_charger", () -> new BlockItem(ModBlocks.RF_CHARGER.get(), new Item.Properties()));
    public static final RegistryObject<ItemTeleporter> TELEPORTER =
            ITEMS.register("teleporter", () -> new ItemTeleporter(ModBlocks.TELEPORTER.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> PERIPHERAL_CONTAINER =
            ITEMS.register("peripheral_container", () -> new BlockItem(ModBlocks.PERIPHERAL_CONTAINER.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> TIME_SENSOR =
            ITEMS.register("time_sensor", () -> new BlockItem(ModBlocks.TIME_SENSOR.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> INTERACTIVE_SORTER =
            ITEMS.register("interactive_sorter", () -> new BlockItem(ModBlocks.INTERACTIVE_SORTER.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> PLAYER_INTERFACE =
            ITEMS.register("player_interface", () -> new BlockItem(ModBlocks.PLAYER_INTERFACE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> RFID_READER_WRITER =
            ITEMS.register("rfid_reader_writer", () -> new BlockItem(ModBlocks.RFID_READER_WRITER.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> MAG_READER_WRITER =
            ITEMS.register("mag_reader_writer", () -> new BlockItem(ModBlocks.MAG_READER_WRITER.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> PRIVACY_GUARD =
            ITEMS.register("privacy_guard", () -> new BlockItem(ModBlocks.PRIVACY_GUARD.get(), new Item.Properties()));
}
