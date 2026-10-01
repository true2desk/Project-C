package projectc.mod.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import projectc.mod.resource.LooseStickEntity;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class LooseStickRenderer extends EntityRenderer<LooseStickEntity, LooseStickRenderState> {

    private final ItemModelResolver itemModelResolver;

    public LooseStickRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemModelResolver = context.getItemModelResolver();
    }

    @Override
    public LooseStickRenderState createRenderState() {
        return new LooseStickRenderState();
    }


    @Override
    public void submit(
            LooseStickRenderState state,
            PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector,
            CameraRenderState camera
    ) {
        state.itemRenderState.submit(
                poseStack,
                submitNodeCollector,
                15728880,
                0,
                0
        );
    }

    @Override
    public void extractRenderState(LooseStickEntity entity, LooseStickRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);

        state.itemRenderState.clear();

        itemModelResolver.updateForNonLiving(
                state.itemRenderState,
                new ItemStack(Items.STICK),
                ItemDisplayContext.GROUND,
                entity
        );

    }

}