package com.austinv11.peripheralsplusplus.init;

import com.austinv11.peripheralsplusplus.entities.EntityNanoBotSwarm;
import com.austinv11.peripheralsplusplus.entities.EntityRidableTurtle;
import com.austinv11.peripheralsplusplus.reference.Reference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Reference.MOD_ID);

    public static final RegistryObject<EntityType<EntityNanoBotSwarm>> NANO_BOT_SWARM =
            ENTITIES.register("nano_bot_swarm", () -> EntityType.Builder
                    .<EntityNanoBotSwarm>of(EntityNanoBotSwarm::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .build(Reference.MOD_ID + ":nano_bot_swarm"));

    public static final RegistryObject<EntityType<EntityRidableTurtle>> RIDABLE_TURTLE =
            ENTITIES.register("ridable_turtle", () -> EntityType.Builder
                    .<EntityRidableTurtle>of(EntityRidableTurtle::new, MobCategory.MISC)
                    .sized(0.9F, 0.5F)
                    .build(Reference.MOD_ID + ":ridable_turtle"));

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(RIDABLE_TURTLE.get(), EntityRidableTurtle.createAttributes().build());
    }
}
