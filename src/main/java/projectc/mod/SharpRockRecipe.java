package projectc.mod;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

import java.util.List;

public class SharpRockRecipe extends CustomRecipe {

    public static final SharpRockRecipe INSTANCE = new SharpRockRecipe();

    public static final RecipeSerializer<SharpRockRecipe> SERIALIZER =
            new RecipeSerializer<>(
                    MapCodec.unit(INSTANCE),
                    StreamCodec.unit(INSTANCE)
            );

    public SharpRockRecipe() {
        super();
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        int rocks = 0;

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);

            if (stack.isEmpty()) {
                continue;
            }

            if (!stack.is(ModItems.ROCK)) {
                return false;
            }

            rocks++;
        }

        return rocks == 2;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        return new ItemStack(ModItems.SHARP_ROCK);
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(
                input.size(),
                ItemStack.EMPTY
        );

        remaining.set(0, new ItemStack(ModItems.ROCK));

        return remaining;
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public boolean isSpecial() {
        return false;
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.create(
                List.of(
                        Ingredient.of(ModItems.ROCK),
                        Ingredient.of(ModItems.ROCK)
                )
        );
    }

    @Override
    public List<RecipeDisplay> display() {
        return List.of(
                new ShapelessCraftingRecipeDisplay(
                        List.of(
                                Ingredient.of(ModItems.ROCK).display(),
                                Ingredient.of(ModItems.ROCK).display()
                        ),
                        new SlotDisplay.ItemSlotDisplay(ModItems.SHARP_ROCK),
                        new SlotDisplay.ItemSlotDisplay(Items.CRAFTING_TABLE)
                )
        );
    }
}