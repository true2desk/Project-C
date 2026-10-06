package projectc.mod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
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
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import projectc.mod.resource.LooseStickEntity;
import projectc.mod.worldgen.ModWorldGeneration;

public class ProjectC implements ModInitializer {
	public static final String MOD_ID = "project-c";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");

		ModItems.initialize();
		ModBlocks.initialize();
		CivCommands.initialize();
		CivilizationServer.initialize();
		ModEntityTypes.initialize();
		ModWorldGeneration.initialize();

		ServerLifecycleEvents.SERVER_STARTED.register(
				WorldConfiguration::initialize
		);

		AttackBlockCallback.EVENT.register((player, level, hand, pos, direction) -> {
			if (!level.getBlockState(pos).is(ModBlocks.loose_stone)) {
				return InteractionResult.PASS;
			}

			if (!level.isClientSide()) {
				ItemStack rock = new ItemStack(ModItems.ROCK);

				if (!player.getAbilities().instabuild) {
					if (!player.getInventory().add(rock)) {
						level.addFreshEntity(
								new net.minecraft.world.entity.item.ItemEntity(
										level,
										player.getX(),
										player.getY() + 0.5,
										player.getZ(),
										rock
								)
						);
					}
				}

				level.removeBlock(pos, false);
			}

			return InteractionResult.SUCCESS;
		});

		UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
			ItemStack heldItem = player.getItemInHand(hand);

			if (heldItem.is(ModItems.ROCK)) {
				BlockPos placePos = hitResult.getBlockPos().relative(hitResult.getDirection());
				BlockPos supportPos = placePos.below();

				if (!level.getBlockState(supportPos).isFaceSturdy(
						level,
						supportPos,
						Direction.UP
				)) {
					return InteractionResult.FAIL;
				}

				if (!level.getBlockState(placePos).isAir()) {
					return InteractionResult.FAIL;
				}

				if (!level.isClientSide()) {
					level.setBlock(
							placePos,
							ModBlocks.loose_stone.defaultBlockState().setValue(
									LooseStoneBlock.POSITION,
									LooseStonePosition.values()[level.getRandom().nextInt(5)]
							),
							3
					);

					if (!player.getAbilities().instabuild) {
						heldItem.shrink(1);
					}
				}

				return InteractionResult.SUCCESS;
			}

			if (!heldItem.is(Items.STICK)) {
				return InteractionResult.PASS;
			}

			if (hitResult.getDirection() != Direction.UP) {
				return InteractionResult.PASS;
			}

			if (level.getBlockState(hitResult.getBlockPos()).getCollisionShape(
					level,
					hitResult.getBlockPos()
			).isEmpty()) {
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