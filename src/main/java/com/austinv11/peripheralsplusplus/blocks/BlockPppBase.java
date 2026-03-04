package com.austinv11.peripheralsplusplus.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BlockPppBase extends Block {

    public BlockPppBase(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public BlockPppBase() {
        this(BlockBehaviour.Properties.of().strength(4.0F).requiresCorrectToolForDrops());
    }
}
