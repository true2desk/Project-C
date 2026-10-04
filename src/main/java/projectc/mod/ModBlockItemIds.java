package projectc.mod;

import net.minecraft.resources.Identifier;
import net.minecraft.references.BlockItemId;

public class ModBlockItemIds {

    public static final BlockItemId LOOSE_STONE = create("loose_stone");

    private static BlockItemId create(String name) {
        Identifier id = ProjectC.id(name);
        return BlockItemId.create(id, id);
    }
}