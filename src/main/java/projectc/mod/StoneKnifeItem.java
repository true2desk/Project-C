
package projectc.mod;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

public class StoneKnifeItem extends Item {

    public StoneKnifeItem(Properties properties) {
        super(properties
                .sword(ToolMaterial.WOOD, 3.0f, -1.5f)
                .durability(80)
        );
    }
}
