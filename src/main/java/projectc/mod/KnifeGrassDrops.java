package projectc.mod;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;

public class KnifeGrassDrops {

    public static void initialize() {
        PlayerBlockBreakEvents.AFTER.register(
                (level, player, pos, state, blockEntity) -> {
                    if (level.isClientSide() || player.isCreative()) {
                        return;
                    }

                    if (state.is(Blocks.SHORT_GRASS)
                            || state.is(Blocks.TALL_GRASS)
                            || state.is(Blocks.FERN)
                            || state.is(Blocks.LARGE_FERN)) {

                        if (player.getMainHandItem().is(ModItems.STONE_KNIFE)) {
                            Block.popResource(
                                    level,
                                    pos,
                                    new ItemStack(ModItems.PLANT_FIBER)
                            );
                        }
                    }
                }
        );
    }
}