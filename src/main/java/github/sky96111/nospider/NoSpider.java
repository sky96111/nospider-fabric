package github.sky96111.nospider;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerBlockEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.block.spawner.MobSpawnerLogic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;

public class NoSpider implements ModInitializer {
    private static int entityCount = 0;
    private static int spawnerCount = 0;
    public static final Logger LOGGER = LoggerFactory.getLogger(NoSpider.class);

    public static HashSet<EntityType<?>> BLOCKED_TYPES = new HashSet<>() {{
        add(EntityType.SPIDER);
        add(EntityType.CAVE_SPIDER);
        add(EntityType.SILVERFISH);
    }};

    public static HashSet<Block> BLOCKED_BLOCKS = new HashSet<>() {{
        add(Blocks.COBWEB);
    }};

    public static boolean isBlockedType(EntityType<?> type) {
        return BLOCKED_TYPES.contains(type);
    }

    public static boolean isBlockedType(Block block) {
        return BLOCKED_BLOCKS.contains(block);
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Blocked {} Mobs: {}", BLOCKED_TYPES.size(), BLOCKED_TYPES);
        LOGGER.info("Blocked {} Blocks: {}", BLOCKED_BLOCKS.size(), BLOCKED_BLOCKS);
        LOGGER.info("NoSpider initialized!");

        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (isBlockedType(entity.getType())) {
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

                if (isBlockedType(entity.getType())) {
                    spawnerBlock.markRemoved();
                    spawnerCount++;
                }
            }
        });

        ServerLifecycleEvents.SERVER_STARTING.register((server) -> {
            entityCount = 0;
            spawnerCount = 0;
        });

        ServerLifecycleEvents.SERVER_STOPPING.register((server) -> {
            LOGGER.info("Blocked: {} Mobs, {} SpawnerBlocks", entityCount, spawnerCount);
        });

    }
}
