package projectc.mod;

import net.minecraft.resources.Identifier;
import net.minecraft.references.BlockItemId;

public class ModBlockItemIds {

    public static final BlockItemId FLINT_BLOCK = create("flint_block");

    private static BlockItemId create(String name) {
        Identifier id = ProjectC.id(name);
        return BlockItemId.create(id, id);
    }
}