package projectc.mod.resource;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class ModEntityAttributes {

    public static void initialize() {
        FabricDefaultAttributeRegistry.register(
                ModEntityTypes.HUMAN,
                HumanEntity.createAttributes()
        );
    }
}