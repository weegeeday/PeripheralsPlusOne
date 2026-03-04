package com.austinv11.peripheralsplusplus.init;

import com.austinv11.peripheralsplusplus.reference.Reference;
import com.austinv11.peripheralsplusplus.tiles.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModTileEntities {

public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES =
DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Reference.MOD_ID);

public static final RegistryObject<BlockEntityType<TileEntityChatBox>> CHAT_BOX =
TILE_ENTITIES.register("chat_box", () -> BlockEntityType.Builder
.of(TileEntityChatBox::new, ModBlocks.CHAT_BOX.get()).build(null));

public static final RegistryObject<BlockEntityType<TileEntityPlayerSensor>> PLAYER_SENSOR =
TILE_ENTITIES.register("player_sensor", () -> BlockEntityType.Builder
.of(TileEntityPlayerSensor::new, ModBlocks.PLAYER_SENSOR.get()).build(null));

public static final RegistryObject<BlockEntityType<TileEntityRFCharger>> RF_CHARGER =
TILE_ENTITIES.register("rf_charger", () -> BlockEntityType.Builder
.of(TileEntityRFCharger::new, ModBlocks.RF_CHARGER.get()).build(null));

public static final RegistryObject<BlockEntityType<TileEntityOreDictionary>> ORE_DICTIONARY =
TILE_ENTITIES.register("ore_dictionary", () -> BlockEntityType.Builder
.of(TileEntityOreDictionary::new, ModBlocks.ORE_DICTIONARY.get()).build(null));

public static final RegistryObject<BlockEntityType<TileEntityAnalyzerBee>> ANALYZER_BEE =
TILE_ENTITIES.register("analyzer_bee", () -> BlockEntityType.Builder
.of(TileEntityAnalyzerBee::new, ModBlocks.ANALYZER_BEE.get()).build(null));

public static final RegistryObject<BlockEntityType<TileEntityAnalyzerTree>> ANALYZER_TREE =
TILE_ENTITIES.register("analyzer_tree", () -> BlockEntityType.Builder
.of(TileEntityAnalyzerTree::new, ModBlocks.ANALYZER_TREE.get()).build(null));

public static final RegistryObject<BlockEntityType<TileEntityAnalyzerButterfly>> ANALYZER_BUTTERFLY =
TILE_ENTITIES.register("analyzer_butterfly", () -> BlockEntityType.Builder
.of(TileEntityAnalyzerButterfly::new, ModBlocks.ANALYZER_BUTTERFLY.get()).build(null));

public static final RegistryObject<BlockEntityType<TileEntityTeleporter>> TELEPORTER =
TILE_ENTITIES.register("teleporter", () -> BlockEntityType.Builder
.of(TileEntityTeleporter::new, ModBlocks.TELEPORTER.get()).build(null));

public static final RegistryObject<BlockEntityType<TileEntityEnvironmentScanner>> ENVIRONMENT_SCANNER =
TILE_ENTITIES.register("environment_scanner", () -> BlockEntityType.Builder
.of(TileEntityEnvironmentScanner::new, ModBlocks.ENVIRONMENT_SCANNER.get()).build(null));

public static final RegistryObject<BlockEntityType<TileEntitySpeaker>> SPEAKER =
TILE_ENTITIES.register("speaker", () -> BlockEntityType.Builder
.of(TileEntitySpeaker::new, ModBlocks.SPEAKER.get()).build(null));

public static final RegistryObject<BlockEntityType<TileEntityAntenna>> ANTENNA =
TILE_ENTITIES.register("antenna", () -> BlockEntityType.Builder
.of(TileEntityAntenna::new, ModBlocks.ANTENNA.get()).build(null));

public static final RegistryObject<BlockEntityType<TileEntityPeripheralContainer>> PERIPHERAL_CONTAINER =
TILE_ENTITIES.register("peripheral_container", () -> BlockEntityType.Builder
.of(TileEntityPeripheralContainer::new, ModBlocks.PERIPHERAL_CONTAINER.get()).build(null));

public static final RegistryObject<BlockEntityType<TileEntityMEBridge>> ME_BRIDGE =
TILE_ENTITIES.register("me_bridge", () -> BlockEntityType.Builder
.of(TileEntityMEBridge::new, ModBlocks.ME_BRIDGE.get()).build(null));

public static final RegistryObject<BlockEntityType<TileEntityTimeSensor>> TIME_SENSOR =
TILE_ENTITIES.register("time_sensor", () -> BlockEntityType.Builder
.of(TileEntityTimeSensor::new, ModBlocks.TIME_SENSOR.get()).build(null));

public static final RegistryObject<BlockEntityType<TileEntityInteractiveSorter>> INTERACTIVE_SORTER =
TILE_ENTITIES.register("interactive_sorter", () -> BlockEntityType.Builder
.of(TileEntityInteractiveSorter::new, ModBlocks.INTERACTIVE_SORTER.get()).build(null));

public static final RegistryObject<BlockEntityType<TileEntityPlayerInterface>> PLAYER_INTERFACE =
TILE_ENTITIES.register("player_interface", () -> BlockEntityType.Builder
.of(TileEntityPlayerInterface::new, ModBlocks.PLAYER_INTERFACE.get()).build(null));

public static final RegistryObject<BlockEntityType<TileEntityResupplyStation>> RESUPPLY_STATION =
TILE_ENTITIES.register("resupply_station", () -> BlockEntityType.Builder
.of(TileEntityResupplyStation::new, ModBlocks.RESUPPLY_STATION.get()).build(null));

public static final RegistryObject<BlockEntityType<TileEntityManaManipulator>> MANA_MANIPULATOR =
TILE_ENTITIES.register("mana_manipulator", () -> BlockEntityType.Builder
.of(TileEntityManaManipulator::new, ModBlocks.MANA_MANIPULATOR.get()).build(null));

public static final RegistryObject<BlockEntityType<TileEntityRfidReaderWriter>> RFID_READER_WRITER =
TILE_ENTITIES.register("rfid_reader_writer", () -> BlockEntityType.Builder
.of(TileEntityRfidReaderWriter::new, ModBlocks.RFID_READER_WRITER.get()).build(null));

public static final RegistryObject<BlockEntityType<TileEntityMagReaderWriter>> MAG_READER_WRITER =
TILE_ENTITIES.register("mag_reader_writer", () -> BlockEntityType.Builder
.of(TileEntityMagReaderWriter::new, ModBlocks.MAG_READER_WRITER.get()).build(null));

public static final RegistryObject<BlockEntityType<TileEntityPrivacyGuard>> PRIVACY_GUARD =
TILE_ENTITIES.register("privacy_guard", () -> BlockEntityType.Builder
.of(TileEntityPrivacyGuard::new, ModBlocks.PRIVACY_GUARD.get()).build(null));

public static final RegistryObject<BlockEntityType<TileEntityTurtle>> TURTLE =
TILE_ENTITIES.register("turtle", () -> BlockEntityType.Builder
.of(TileEntityTurtle::new, ModBlocks.TURTLE.get()).build(null));

public static final RegistryObject<BlockEntityType<TileEntityAnalyzer>> ANALYZER_CHAT_BOX =
TILE_ENTITIES.register("analyzer", () -> BlockEntityType.Builder
.of(TileEntityAnalyzer::new, ModBlocks.CHAT_BOX.get()).build(null));
}
