package projectc.mod.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.tags.BiomeTags;


public class ModWorldGeneration {

    private static final ResourceKey<PlacedFeature> LOOSE_STONE_PLACED =
            ResourceKey.create(
                    Registries.PLACED_FEATURE,
                    Identifier.fromNamespaceAndPath(
                            projectc.mod.ProjectC.MOD_ID,
                            "loose_stone"
                    )
            );

    private static final ResourceKey<PlacedFeature> LOOSE_STONE_ROCKY_PLACED =
            ResourceKey.create(
                    Registries.PLACED_FEATURE,
                    Identifier.fromNamespaceAndPath(
                            projectc.mod.ProjectC.MOD_ID,
                            "loose_stone_rocky"
                    )
            );

    private static final ResourceKey<PlacedFeature> LOOSE_STICK_PLACED =
            ResourceKey.create(
                    Registries.PLACED_FEATURE,
                    Identifier.fromNamespaceAndPath(
                            projectc.mod.ProjectC.MOD_ID,
                            "loose_stick"
                    )
            );

    public static void initialize() {
        Registry.register(
                BuiltInRegistries.FEATURE_TYPE,
                Identifier.fromNamespaceAndPath(
                        projectc.mod.ProjectC.MOD_ID,
                        "loose_stone"
                ),
                LooseStoneFeature.CODEC
        );

        Registry.register(
                BuiltInRegistries.FEATURE_TYPE,
                Identifier.fromNamespaceAndPath(
                        projectc.mod.ProjectC.MOD_ID,
                        "loose_stick"
                ),
                LooseStickFeature.CODEC
        );

        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                LOOSE_STICK_PLACED
        );

        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld()
                        .and(BiomeSelectors.tag(BiomeTags.IS_MOUNTAIN).negate()),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                LOOSE_STONE_PLACED
        );

        BiomeModifications.addFeature(
                BiomeSelectors.tag(BiomeTags.IS_MOUNTAIN),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                LOOSE_STONE_ROCKY_PLACED
        );
    }
}