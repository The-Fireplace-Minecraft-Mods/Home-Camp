package the_fireplace.homecamp.mixin;

import dev.the_fireplace.annotateddi.api.DIContainer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import the_fireplace.homecamp.domain.config.ConfigValues;

@Mixin(SpawnHelper.class)
public abstract class SpawnHelperMixin
{
    @Inject(method = "canSpawn", at = @At("HEAD"), cancellable = true)
    private static void handleCustomSpawnRestriction(
        ServerWorld world,
        SpawnGroup group,
        StructureAccessor pAccessor,
        ChunkGenerator chunkGenerator,
        SpawnSettings.SpawnEntry spawnEntry,
        BlockPos.Mutable blockPos,
        double squaredDistance,
        CallbackInfoReturnable<Boolean> cir
    ) {
        if (SpawnGroup.MONSTER.equals(group)) {
            int radius = DIContainer.get().getInstance(ConfigValues.class).noMobSpawnRegion();
            if (radius == 0) {
                return;
            }

            int minX = ChunkSectionPos.getSectionCoord(blockPos.getX() - radius);
            int minZ = ChunkSectionPos.getSectionCoord(blockPos.getZ() - radius);
            int maxX = ChunkSectionPos.getSectionCoord(blockPos.getX() + radius);
            int maxZ = ChunkSectionPos.getSectionCoord(blockPos.getZ() + radius);
            for (int chunkX = minX; chunkX <= maxX; chunkX++) {
                for (int chunkZ = minZ; chunkZ <= maxZ; chunkZ++) {
                    WorldChunk chunk = world.getChunkManager().getWorldChunk(chunkX, chunkZ, false);
                    if (chunk != null) {
                        for (BlockPos blockEntity : chunk.getBlockEntityPositions()) {
                            BlockState blockState = chunk.getBlockState(blockEntity);
                            if (blockState.getBlock() == Blocks.CAMPFIRE || blockState.getBlock() == Blocks.SOUL_CAMPFIRE) {
                                if (blockState.get(CampfireBlock.LIT)) {
                                    if (blockPos.isWithinDistance(blockEntity, radius)) {
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