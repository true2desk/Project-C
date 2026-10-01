package projectc.mod.client;
import projectc.mod.resource.ModEntityTypes;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ProjectCClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(
				ModEntityTypes.LOOSE_STICK,
				LooseStickRenderer::new
		);
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
	}
}