package projectc.mod;

import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.Identifier;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import projectc.mod.civilization.CivCommands;
import projectc.mod.civilization.CivilizationServer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import projectc.mod.world.WorldConfiguration;
import projectc.mod.resource.ModEntityTypes;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import projectc.mod.resource.LooseStickEntity;

public class ProjectC implements ModInitializer {
	public static final String MOD_ID = "project-c";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");

		ModItems.initialize();
		ModBlocks.initialize();
		CivCommands.initialize();
		CivilizationServer.initialize();
		ModEntityTypes.initialize();

		ServerLifecycleEvents.SERVER_STARTED.register(
				WorldConfiguration::initialize
		);

		UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
			if (!player.getItemInHand(hand).is(Items.STICK)) {
				return InteractionResult.PASS;
			}
			if (hitResult.getDirection() != Direction.UP) {
				return InteractionResult.PASS;
			}
			if (level.getBlockState(hitResult.getBlockPos()).getCollisionShape(level, hitResult.getBlockPos()).isEmpty()) {
				return InteractionResult.PASS;
			}

			if (!level.isClientSide()) {
				if (!level.getEntitiesOfClass(
						LooseStickEntity.class,
						new AABB(
								hitResult.getBlockPos().getX(),
								hitResult.getBlockPos().getY() + 0.5,
								hitResult.getBlockPos().getZ(),
								hitResult.getBlockPos().getX() + 1,
								hitResult.getBlockPos().getY() + 1.5,
								hitResult.getBlockPos().getZ() + 1
						)
				).isEmpty()) {
					return InteractionResult.FAIL;
				}

				LooseStickEntity looseStick = new LooseStickEntity(
						ModEntityTypes.LOOSE_STICK,
						level
				);

				BlockPos blockPos = hitResult.getBlockPos();

				looseStick.setPos(
						blockPos.getX() + 0.5,
						blockPos.getY() + 1.0,
						blockPos.getZ() + 0.5
				);

				looseStick.setYRot(level.getRandom().nextFloat() * 360.0F);

				level.addFreshEntity(looseStick);

				if (!player.getAbilities().instabuild) {
					player.getItemInHand(hand).shrink(1);
				}
			}

			return InteractionResult.SUCCESS;
		});

	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
