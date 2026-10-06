package projectc.mod.worldgen;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;

public class LooseStoneFeature implements Feature {

    public static final MapCodec<LooseStoneFeature> CODEC =
            MapCodec.unit(new LooseStoneFeature());

    @Override
    public MapCodec<LooseStoneFeature> codec() {
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

        if (!ground.isSolid()) {
            return false;
        }

        if (!level.getBlockState(origin).isAir()) {
            return false;
        }

        level.setBlock(
                origin,
                projectc.mod.ModBlocks.loose_stone.defaultBlockState(),
                2
        );

        return true;
    }
}