
package projectc.mod.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import projectc.mod.resource.HumanEntity;

public class HumanRenderer extends HumanoidMobRenderer<
        HumanEntity,
        HumanoidRenderState,
        HumanoidModel<HumanoidRenderState>
        > {

    public HumanRenderer(EntityRendererProvider.Context context) {
        super(
                context,
                new HumanoidModel<>(
                        context.bakeLayer(ModelLayers.PLAYER)
                ),
                0.5F
        );
    }

    @Override
    public HumanoidRenderState createRenderState() {
        return new HumanoidRenderState();
    }

    @Override
    public Identifier getTextureLocation(HumanoidRenderState state) {
        return Identifier.fromNamespaceAndPath(
                "minecraft",
                "textures/entity/player/wide/steve.png"
        );
    }
}
