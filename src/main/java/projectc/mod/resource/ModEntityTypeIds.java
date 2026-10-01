package projectc.mod.resource;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;

import projectc.mod.ProjectC;

public class ModEntityTypeIds {

    public static final ResourceKey<EntityType<?>> LOOSE_STICK =
            create("loose_stick");

    private static ResourceKey<EntityType<?>> create(String name) {
        return ResourceKey.create(
                Registries.ENTITY_TYPE,
                ProjectC.id(name)
        );
    }
}
