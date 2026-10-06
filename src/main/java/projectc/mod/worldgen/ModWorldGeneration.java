package projectc.mod.worldgen;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

public class ModWorldGeneration {

    public static void initialize() {
        Registry.register(
                BuiltInRegistries.FEATURE_TYPE,
                Identifier.fromNamespaceAndPath(
                        projectc.mod.ProjectC.MOD_ID,
                        "loose_stone"
                ),
                LooseStoneFeature.CODEC
        );
    }
}