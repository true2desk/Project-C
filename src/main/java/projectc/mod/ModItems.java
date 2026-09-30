package projectc.mod;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class ModItems {

    public static final Item FLINT_SHARD = register(
            "flint_shard",
            Item::new,
            new Item.Properties()
    );

    public static <T extends Item> T register(
            String name,
            java.util.function.Function<Item.Properties, T> itemFactory,
            Item.Properties settings
    ) {
        ResourceKey<Item> itemKey = ResourceKey.create(
                Registries.ITEM,
                Identifier.fromNamespaceAndPath(ProjectC.MOD_ID, name)
        );

        T item = itemFactory.apply(settings.setId(itemKey));

        return Registry.register(
                BuiltInRegistries.ITEM,
                itemKey,
                item
        );
    }

    public static void initialize() {
    }
}
