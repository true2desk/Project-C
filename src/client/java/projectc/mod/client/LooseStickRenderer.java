package projectc.mod.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.RandomSource;

import java.util.ArrayList;
import java.util.List;

import net.fabricmc.fabric.api.client.model.loading.v1.FabricModelManager;

import projectc.mod.resource.LooseStickEntity;

public class LooseStickRenderer extends EntityRenderer<LooseStickEntity, LooseStickRenderState> {

    private final ItemModelResolver itemModelResolver;
    private final FabricModelManager modelManager;
    private final BlockStateModel looseStickModel;

    public LooseStickRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemModelResolver = context.getItemModelResolver();
        this.modelManager = (FabricModelManager) Minecraft.getInstance().getModelManager();
        this.looseStickModel = modelManager.getModel(
                ProjectCModelLoading.LOOSE_STICK_MODEL_KEY
        );
    }

    @Override
    public LooseStickRenderState createRenderState() {
        return new LooseStickRenderState();
    }

    @Override
    public void extractRenderState(
            LooseStickEntity entity,
            LooseStickRenderState state,
            float partialTicks
    ) {
        super.extractRenderState(entity, state, partialTicks);

        state.itemRenderState.clear();

        itemModelResolver.updateForNonLiving(
                state.itemRenderState,
                new ItemStack(Items.STICK),
                ItemDisplayContext.GROUND,
                entity
        );
        state.yRot = entity.getYRot(partialTicks);
    }

    @Override
    public void submit(
            LooseStickRenderState state,
            PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector,
            CameraRenderState camera
    ) {
        RandomSource random = RandomSource.create();

        List<BlockStateModelPart> parts = new ArrayList<>();
        looseStickModel.collectParts(random, parts);

        poseStack.translate(-0.5F, 0.0F, -0.5F);
        poseStack.translate(0.5F, 0.0F, 0.5F);
        poseStack.rotateDegrees(Axis.YP, state.yRot);
        poseStack.translate(-0.5F, 0.0F, -0.5F);

        submitNodeCollector.submitBlockModel(
                poseStack,
                RenderTypes.solidMovingBlock(),                parts,
                new int[0],
                state.lightCoords,
                0,
                0
        );
    }
}