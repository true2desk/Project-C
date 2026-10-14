package projectc.mod;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.resources.Identifier;

public class StoneKnifeItem extends Item {

    public StoneKnifeItem(Properties properties) {
        super(properties.attributes(
                ItemAttributeModifiers.builder()
                        .add(
                                Attributes.ATTACK_DAMAGE,
                                new AttributeModifier(
                                        Identifier.fromNamespaceAndPath(
                                                ProjectC.MOD_ID,
                                                "stone_knife_damage"
                                        ),
                                        3.0,
                                        AttributeModifier.Operation.ADD_VALUE
                                ),
                                EquipmentSlotGroup.MAINHAND
                        )
                        .add(
                                Attributes.ATTACK_SPEED,
                                new AttributeModifier(
                                        Identifier.fromNamespaceAndPath(
                                                ProjectC.MOD_ID,
                                                "stone_knife_speed"
                                        ),
                                        -1.5,
                                        AttributeModifier.Operation.ADD_VALUE
                                ),
                                EquipmentSlotGroup.MAINHAND
                        )
                        .build()
        ));
    }
}