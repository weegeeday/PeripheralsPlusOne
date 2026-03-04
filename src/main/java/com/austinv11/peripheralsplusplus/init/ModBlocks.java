package com.austinv11.peripheralsplusplus.init;

import com.austinv11.peripheralsplusplus.blocks.*;
import com.austinv11.peripheralsplusplus.reference.Reference;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MOD_ID);

    public static final RegistryObject<BlockChatBox> CHAT_BOX =
            BLOCKS.register("chat_box", BlockChatBox::new);
    public static final RegistryObject<BlockPlayerSensor> PLAYER_SENSOR =
            BLOCKS.register("player_sensor", BlockPlayerSensor::new);
    public static final RegistryObject<BlockRFCharger> RF_CHARGER =
            BLOCKS.register("rf_charger", BlockRFCharger::new);
    public static final RegistryObject<BlockOreDictionary> ORE_DICTIONARY =
            BLOCKS.register("ore_dictionary", BlockOreDictionary::new);
    public static final RegistryObject<BlockAnalyzerBee> ANALYZER_BEE =
            BLOCKS.register("analyzer_bee", BlockAnalyzerBee::new);
    public static final RegistryObject<BlockAnalyzerTree> ANALYZER_TREE =
            BLOCKS.register("analyzer_tree", BlockAnalyzerTree::new);
    public static final RegistryObject<BlockAnalyzerButterfly> ANALYZER_BUTTERFLY =
            BLOCKS.register("analyzer_butterfly", BlockAnalyzerButterfly::new);
    public static final RegistryObject<BlockTeleporter> TELEPORTER =
            BLOCKS.register("teleporter", BlockTeleporter::new);
    public static final RegistryObject<BlockEnvironmentScanner> ENVIRONMENT_SCANNER =
            BLOCKS.register("environment_scanner", BlockEnvironmentScanner::new);
    public static final RegistryObject<BlockSpeaker> SPEAKER =
            BLOCKS.register("speaker", BlockSpeaker::new);
    public static final RegistryObject<BlockAntenna> ANTENNA =
            BLOCKS.register("antenna", BlockAntenna::new);
    public static final RegistryObject<BlockPeripheralContainer> PERIPHERAL_CONTAINER =
            BLOCKS.register("peripheral_container", BlockPeripheralContainer::new);
    public static final RegistryObject<BlockMEBridge> ME_BRIDGE =
            BLOCKS.register("me_bridge", BlockMEBridge::new);
    public static final RegistryObject<BlockTurtle> TURTLE =
            BLOCKS.register("turtle", BlockTurtle::new);
    public static final RegistryObject<BlockTimeSensor> TIME_SENSOR =
            BLOCKS.register("time_sensor", BlockTimeSensor::new);
    public static final RegistryObject<BlockInteractiveSorter> INTERACTIVE_SORTER =
            BLOCKS.register("interactive_sorter", BlockInteractiveSorter::new);
    public static final RegistryObject<BlockPlayerInterface> PLAYER_INTERFACE =
            BLOCKS.register("player_interface", BlockPlayerInterface::new);
    public static final RegistryObject<BlockResupplyStation> RESUPPLY_STATION =
            BLOCKS.register("resupply_station", BlockResupplyStation::new);
    public static final RegistryObject<BlockManaManipulator> MANA_MANIPULATOR =
            BLOCKS.register("mana_manipulator", BlockManaManipulator::new);
    public static final RegistryObject<BlockRfidReaderWriter> RFID_READER_WRITER =
            BLOCKS.register("rfid_reader_writer", BlockRfidReaderWriter::new);
    public static final RegistryObject<BlockMagReaderWriter> MAG_READER_WRITER =
            BLOCKS.register("mag_reader_writer", BlockMagReaderWriter::new);
    public static final RegistryObject<BlockPrivacyGuard> PRIVACY_GUARD =
            BLOCKS.register("privacy_guard", BlockPrivacyGuard::new);
}
