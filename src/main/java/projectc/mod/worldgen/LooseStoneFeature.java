package projectc.mod.worldgen;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
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
        BlockPos groundPos = origin.below();
        BlockState ground = level.getBlockState(groundPos);

        boolean suitableGround =
                ground.is(BlockTags.DIRT)
                        || ground.is(BlockTags.BASE_STONE_OVERWORLD)
                        || ground.is(Blocks.GRASS_BLOCK)
                        || ground.is(Blocks.MYCELIUM)
                        || ground.is(Blocks.MUD)
                        || ground.is(Blocks.COBBLESTONE)
                        || ground.is(Blocks.MOSSY_COBBLESTONE)
                        || ground.is(Blocks.COBBLED_DEEPSLATE)
                        || ground.is(Blocks.DRIPSTONE_BLOCK)
                        || ground.is(Blocks.CALCITE)
                        || ground.is(Blocks.BASALT)
                        || ground.is(Blocks.SMOOTH_BASALT)
                        || ground.is(Blocks.BLACKSTONE)
                        || ground.is(Blocks.GRAVEL);

        if (!suitableGround) {
            return false;
        }

        BlockState target = level.getBlockState(origin);

        boolean replaceableVegetation =
                target.is(Blocks.SHORT_GRASS)
                        || target.is(Blocks.TALL_GRASS)
                        || target.is(Blocks.FERN)
                        || target.is(Blocks.LARGE_FERN);

        if (!target.isAir() && !replaceableVegetation) {
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

        level.setBlock(
                origin,
                projectc.mod.ModBlocks.loose_stone.defaultBlockState(),
                2
        );

        return true;
    }
}