package com.austinv11.peripheralsplusplus.init;

import com.austinv11.peripheralsplusplus.reference.Reference;
import com.austinv11.peripheralsplusplus.tiles.containers.*;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, Reference.MOD_ID);

    public static final RegistryObject<MenuType<ContainerInteractiveSorter>> INTERACTIVE_SORTER =
            MENUS.register("interactive_sorter", () ->
                    IForgeMenuType.create((syncId, inv, buf) -> {
                        net.minecraft.core.BlockPos pos = buf.readBlockPos();
                        net.minecraft.world.level.block.entity.BlockEntity te =
                                inv.player.level().getBlockEntity(pos);
                        if (te instanceof net.minecraft.world.Container container)
                            return new ContainerInteractiveSorter(syncId, inv, container);
                        return new ContainerInteractiveSorter(syncId, inv,
                                new net.minecraft.world.SimpleContainer(1));
                    }));

    public static final RegistryObject<MenuType<ContainerRfidReaderWriter>> RFID_READER_WRITER =
            MENUS.register("rfid_reader_writer", () ->
                    IForgeMenuType.create((syncId, inv, buf) -> {
                        net.minecraft.core.BlockPos pos = buf.readBlockPos();
                        net.minecraft.world.level.block.entity.BlockEntity te =
                                inv.player.level().getBlockEntity(pos);
                        if (te instanceof net.minecraft.world.Container container)
                            return new ContainerRfidReaderWriter(syncId, inv, container);
                        return new ContainerRfidReaderWriter(syncId, inv,
                                new net.minecraft.world.SimpleContainer(1));
                    }));

    public static final RegistryObject<MenuType<ContainerResupplyStation>> RESUPPLY_STATION =
            MENUS.register("resupply_station", () ->
                    IForgeMenuType.create((syncId, inv, buf) -> {
                        net.minecraft.core.BlockPos pos = buf.readBlockPos();
                        net.minecraft.world.level.block.entity.BlockEntity te =
                                inv.player.level().getBlockEntity(pos);
                        if (te instanceof net.minecraft.world.Container container)
                            return new ContainerResupplyStation(syncId, inv, container);
                        return new ContainerResupplyStation(syncId, inv,
                                new net.minecraft.world.SimpleContainer(56));
                    }));

    public static final RegistryObject<MenuType<ContainerPlayerInterface>> PLAYER_INTERFACE =
            MENUS.register("player_interface", () ->
                    IForgeMenuType.create((syncId, inv, buf) -> {
                        net.minecraft.core.BlockPos pos = buf.readBlockPos();
                        net.minecraft.world.level.block.entity.BlockEntity te =
                                inv.player.level().getBlockEntity(pos);
                        if (te instanceof net.minecraft.world.Container container)
                            return new ContainerPlayerInterface(syncId, inv, container);
                        return new ContainerPlayerInterface(syncId, inv,
                                new net.minecraft.world.SimpleContainer(8));
                    }));
}
