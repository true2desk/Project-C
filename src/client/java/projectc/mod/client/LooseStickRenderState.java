package projectc.mod.client;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public class LooseStickRenderState extends EntityRenderState {

    public final ItemStackRenderState itemRenderState = new ItemStackRenderState();
    public float yRot;
}