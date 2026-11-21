package projectc.mod.worldgen;

import net.minecraft.resources.Identifier;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;

import java.util.Set;

public final class BiomeRemoval {

    private static final Set<Identifier> REMOVED_BIOMES = Set.of(
            Identifier.withDefaultNamespace("dark_forest"),
            Identifier.withDefaultNamespace("mushroom_fields"),
            Identifier.withDefaultNamespace("pale_garden"),

            Identifier.fromNamespaceAndPath("terralith", "alpha_islands"),
            Identifier.fromNamespaceAndPath("terralith", "alpha_islands_winter"),
            Identifier.fromNamespaceAndPath("terralith", "amethyst_canyon"),
            Identifier.fromNamespaceAndPath("terralith", "amethyst_rainforest"),
            Identifier.fromNamespaceAndPath("terralith", "moonlight_grove"),
            Identifier.fromNamespaceAndPath("terralith", "moonlight_valley"),
            Identifier.fromNamespaceAndPath("terralith", "skylands_spring"),
            Identifier.fromNamespaceAndPath("terralith", "skylands_summer"),
            Identifier.fromNamespaceAndPath("terralith", "skylands_autumn"),
            Identifier.fromNamespaceAndPath("terralith", "skylands_winter")
    );

    private BiomeRemoval() {}

    public static boolean isRemoved(Holder<Biome> biome) {
        return biome.unwrapKey()
                .map(key -> REMOVED_BIOMES.contains(key.identifier()))
                .orElse(false);
    }
}