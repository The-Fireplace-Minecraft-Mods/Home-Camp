package dev.the_fireplace.homecamp.mixin;

import dev.the_fireplace.homecamp.HomeCampConstants;
import dev.the_fireplace.homecamp.domain.config.ConfigValues;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NaturalSpawner.class)
public abstract class SpawnHelperMixin
{
    @Inject(method = "isValidSpawnPostitionForType", at = @At("HEAD"), cancellable = true)
    private static void handleCustomSpawnRestriction(
        ServerLevel world,
        MobCategory group,
        StructureFeatureManager pAccessor,
        ChunkGenerator chunkGenerator,
        MobSpawnSettings.SpawnerData spawnEntry,
        BlockPos.MutableBlockPos blockPos,
        double squaredDistance,
        CallbackInfoReturnable<Boolean> cir
    ) {
        if (MobCategory.MONSTER.equals(group)) {
            int radius = HomeCampConstants.getInjector().getInstance(ConfigValues.class).noMobSpawnRegion();
            if (radius == 0) {
                return;
            }

            int minX = SectionPos.blockToSectionCoord(blockPos.getX() - radius);
            int minZ = SectionPos.blockToSectionCoord(blockPos.getZ() - radius);
            int maxX = SectionPos.blockToSectionCoord(blockPos.getX() + radius);
            int maxZ = SectionPos.blockToSectionCoord(blockPos.getZ() + radius);
            for (int chunkX = minX; chunkX <= maxX; chunkX++) {
                for (int chunkZ = minZ; chunkZ <= maxZ; chunkZ++) {
                    LevelChunk chunk = world.getChunkSource().getChunk(chunkX, chunkZ, false);
                    if (chunk != null) {
                        for (BlockPos blockEntity : chunk.getBlockEntitiesPos()) {
                            BlockState blockState = chunk.getBlockState(blockEntity);
                            if (blockState.getBlock() == Blocks.CAMPFIRE || blockState.getBlock() == Blocks.SOUL_CAMPFIRE) {
                                if (blockState.getValue(CampfireBlock.LIT)) {
                                    if (blockPos.closerThan(blockEntity, radius)) {
                                        cir.setReturnValue(false);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}