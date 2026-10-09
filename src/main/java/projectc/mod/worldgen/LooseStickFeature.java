package projectc.mod.worldgen;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.entity.EntitySpawnReason;

import projectc.mod.resource.LooseStickEntity;
import projectc.mod.resource.ModEntityTypes;

public class LooseStickFeature implements Feature {

    public static final MapCodec<LooseStickFeature> CODEC =
            MapCodec.unit(new LooseStickFeature());

    @Override
    public MapCodec<LooseStickFeature> codec() {
        return CODEC;
    }

    @Override
    public boolean place(
            WorldGenLevel level,
            ChunkGenerator chunkGenerator,
            RandomSource random,
            BlockPos origin
    ) {
        BlockState ground = level.getBlockState(origin.below());

        boolean suitableGround =
                ground.isSolid()
                        && !ground.is(BlockTags.LEAVES)
                        && !ground.is(BlockTags.LOGS)
                        && !ground.is(Blocks.CACTUS);

        if (!suitableGround || !level.getBlockState(origin).isAir()) {
            return false;
        }

        boolean hasVegetation =
                level.getBiome(origin).is(BiomeTags.IS_FOREST)
                        || level.getBiome(origin).is(BiomeTags.IS_JUNGLE)
                        || level.getBiome(origin).is(BiomeTags.IS_SAVANNA)
                        || level.getBiome(origin).is(BiomeTags.IS_TAIGA);

        if (!hasVegetation) {
            return false;
        }

        boolean nearRiver =
                level.getBiome(origin).is(BiomeTags.IS_RIVER)
                        || level.getBiome(origin.offset(8, 0, 0)).is(BiomeTags.IS_RIVER)
                        || level.getBiome(origin.offset(-8, 0, 0)).is(BiomeTags.IS_RIVER)
                        || level.getBiome(origin.offset(0, 0, 8)).is(BiomeTags.IS_RIVER)
                        || level.getBiome(origin.offset(0, 0, -8)).is(BiomeTags.IS_RIVER);

        int denominator = nearRiver ? 1 : 3;

        if (random.nextInt(denominator) != 0) {
            return false;
        }

        if (random.nextInt(denominator) != 0) {
            return false;
        }

        LooseStickEntity stick = ModEntityTypes.LOOSE_STICK.create(
                level.getLevel(),
                EntitySpawnReason.TRIGGERED
        );

        if (stick == null) {
            return false;
        }

        stick.setPos(
                origin.getX() + 0.5,
                origin.getY(),
                origin.getZ() + 0.5
        );
        stick.setYRot(random.nextFloat() * 360.0F);

        return level.addFreshEntity(stick);
    }
}