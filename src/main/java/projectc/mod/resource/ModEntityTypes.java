package projectc.mod.resource;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntityTypes {

    public static final EntityType<LooseStickEntity> LOOSE_STICK =
            register(
                    ModEntityTypeIds.LOOSE_STICK,
                    EntityType.Builder
                            .<LooseStickEntity>of(
                                    LooseStickEntity::new,
                                    MobCategory.MISC
                            )
                            .sized(0.25f, 0.25f)
                            .clientTrackingRange(4)
                            .updateInterval(10)
            );

    private static <T extends net.minecraft.world.entity.Entity> EntityType<T> register(
            net.minecraft.resources.ResourceKey<EntityType<?>> key,
            EntityType.Builder<T> builder
    ) {
        return Registry.register(
                BuiltInRegistries.ENTITY_TYPE,
                key,
                builder.build(key)
        );
    }

    public static void initialize() {
        // Forces class initialization and therefore entity registration.
    }
}