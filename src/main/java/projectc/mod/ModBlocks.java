package projectc.mod;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.references.BlockItemId;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.world.item.CreativeModeTabs;

import java.util.function.Function;

public class ModBlocks {

    public static final Block loose_stone = register(
            ModBlockItemIds.LOOSE_STONE,
            LooseStoneBlock::new,
            Block.Properties.of()
    );

    private static Block register(
            BlockItemId id,
            Function<Block.Properties, Block> blockFactory,
            Block.Properties properties
    ) {
        Block block = register(
                id.block(),
                blockFactory,
                properties
        );

        BlockItem blockItem = new BlockItem(
                block,
                new Item.Properties()
                        .useBlockDescriptionPrefix()
                        .setId(id.item())
        );

        Registry.register(
                BuiltInRegistries.ITEM,
                id.item(),
                blockItem
        );

        return block;
    }

    private static Block register(
            net.minecraft.resources.ResourceKey<Block> blockKey,
            Function<Block.Properties, Block> blockFactory,
            Block.Properties properties
    ) {
        Block block = blockFactory.apply(
                properties.setId(blockKey)
        );

        return Registry.register(
                BuiltInRegistries.BLOCK,
                blockKey,
                block
        );
    }

    public static void initialize() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS)
                .register(creativeTab -> creativeTab.accept(loose_stone));
    }
}