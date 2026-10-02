package projectc.mod.client;
import projectc.mod.ProjectC;

import net.fabricmc.fabric.api.client.model.loading.v1.ExtraModelKey;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.SimpleUnbakedExtraModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.resources.Identifier;

import net.minecraft.client.Minecraft;



public class ProjectCModelLoading implements ModelLoadingPlugin {

    public static final Identifier LOOSE_STICK_MODEL =
            ProjectC.id("loose_stick");

    public static final ExtraModelKey<BlockStateModel> LOOSE_STICK_MODEL_KEY =
            ExtraModelKey.create(LOOSE_STICK_MODEL::toString);

    @Override
    public void initialize(Context pluginContext) {
        ProjectC.LOGGER.info("Project C model loading plugin initialized");

        var resourceManager = Minecraft.getInstance().getResourceManager();

        ProjectC.LOGGER.info(
                "Project C loose stick model resource exists: {}",
                resourceManager.getResource(
                        Identifier.fromNamespaceAndPath(
                                "project-c",
                                "models/loose_stick.json"
                        )
                ).isPresent()
        );

        pluginContext.addModel(
                LOOSE_STICK_MODEL_KEY,
                SimpleUnbakedExtraModel.blockStateModel(LOOSE_STICK_MODEL)
        );

        ProjectC.LOGGER.info("Project C loose stick model registered");
    }
}