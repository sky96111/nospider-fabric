package github.sky96111.nospider;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerBlockEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.block.spawner.MobSpawnerLogic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoSpider implements ModInitializer {
    private static int entityCount = 0;
    private static int spawnerCount = 0;
    private static boolean isWorldLoaded = false;

    public static final Logger LOGGER = LoggerFactory.getLogger(NoSpider.class);

    public static final EntityType<?>[] BLOCKED_TYPES = new EntityType[]{
            EntityType.SPIDER,
            EntityType.CAVE_SPIDER,
            EntityType.SILVERFISH
    };

    public static boolean isBlockedEntity(EntityType<?> type) {
        for (EntityType<?> blockedType : BLOCKED_TYPES) {
            if (type == blockedType) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Blocked {} Types: {}", BLOCKED_TYPES.length, BLOCKED_TYPES);
        LOGGER.info("NoSpider initialized!");

        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (isBlockedEntity(entity.getType())) {
                entity.discard();
                entityCount++;
            }
        });

        ServerBlockEntityEvents.BLOCK_ENTITY_LOAD.register((blockEntity, world) -> {
            if (blockEntity instanceof MobSpawnerBlockEntity spawnerBlock) {
                MobSpawnerLogic logic = spawnerBlock.getLogic();
                Entity entity = logic.getRenderedEntity(world, spawnerBlock.getPos());
                if (entity == null) {
                    return;
                }

                if (isBlockedEntity(entity.getType())) {
                    spawnerBlock.markRemoved();
                    spawnerCount++;
                }
            }
        });

        ServerWorldEvents.LOAD.register((server, world) -> {
            if (isWorldLoaded) {
                return;
            }
            entityCount = 0;
            spawnerCount = 0;
            isWorldLoaded = true;
        });

        ServerWorldEvents.UNLOAD.register((server, world) -> {
            if (!isWorldLoaded) {
                return;
            }
            LOGGER.info("Blocked: {} Mobs, {} SpawnerBlocks", entityCount, spawnerCount);
            isWorldLoaded = false;
        });
    }
}
