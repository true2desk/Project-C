package projectc.mod;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ModRecipes {

    public static final RecipeSerializer<SharpRockRecipe> SHARP_ROCK =
            Registry.register(
                    BuiltInRegistries.RECIPE_SERIALIZER,
                    Identifier.fromNamespaceAndPath(
                            ProjectC.MOD_ID,
                            "sharp_rock"
                    ),
                    SharpRockRecipe.SERIALIZER
            );

    public static void initialize() {
        // Registration happens when this class is initialized.
    }
}