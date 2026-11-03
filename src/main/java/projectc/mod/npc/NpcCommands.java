
package projectc.mod.npc;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import projectc.mod.resource.HumanEntity;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public final class NpcCommands {

    private NpcCommands() {
    }

    public static void initialize() {
        CommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess, environment) ->
                        dispatcher.register(
                                Commands.literal("civ")
                                        .then(Commands.literal("npc")
                                                .then(Commands.literal("info")
                                                        .executes(context -> {
                                                            var source = context.getSource();
                                                            HumanEntity human = findNearest(source);

                                                            if (human == null) {
                                                                source.sendFailure(Component.literal(
                                                                        "No human NPC found within 16 blocks."
                                                                ));
                                                                return 0;
                                                            }

                                                            TribeMemberProfile profile = human.getProfile();

                                                            source.sendSuccess(() -> Component.literal(
                                                                    "Role: " + profile.getRole()
                                                                            + " | Traits: " + profile.getPersonalityTraits()
                                                                            + " | Settlement ID: " + profile.getSettlementId()
                                                            ), false);
                                                            return 1;
                                                        }))
                                                .then(Commands.literal("role")
                                                        .then(Commands.argument(
                                                                "role",
                                                                StringArgumentType.word()
                                                        ).suggests((context, builder) -> {
                                                            for (TribeRole role : TribeRole.values()) {
                                                                builder.suggest(role.name());
                                                            }
                                                            return builder.buildFuture();
                                                        }).executes(context -> {
                                                            var source = context.getSource();
                                                            HumanEntity human = findNearest(source);

                                                            if (human == null) {
                                                                source.sendFailure(Component.literal(
                                                                        "No human NPC found within 16 blocks."
                                                                ));
                                                                return 0;
                                                            }

                                                            TribeRole role;
                                                            try {
                                                                String roleName = StringArgumentType.getString(context, "role");

                                                                role = java.util.Arrays.stream(TribeRole.values())
                                                                        .filter(value -> value.name().equalsIgnoreCase(roleName))
                                                                        .findFirst()
                                                                        .orElseThrow(() -> new IllegalArgumentException("Invalid role"));
                                                            } catch (IllegalArgumentException exception) {
                                                                source.sendFailure(Component.literal(
                                                                        "Invalid role. Options: "
                                                                                + java.util.Arrays.toString(TribeRole.values())
                                                                ));
                                                                return 0;
                                                            }

                                                            TribeMemberProfile old = human.getProfile();
                                                            human.setProfile(new TribeMemberProfile(
                                                                    role,
                                                                    old.getPersonalityTraits(),
                                                                    old.getSettlementId()
                                                            ));

                                                            source.sendSuccess(() -> Component.literal(
                                                                    "NPC role set to " + role
                                                            ), false);
                                                            return 1;
                                                        })))
                                                .then(Commands.literal("trait")
                                                        .then(Commands.literal("add")
                                                                .then(Commands.argument(
                                                                        "trait",
                                                                        StringArgumentType.word()
                                                                ).suggests((context, builder) -> {
                                                                    for (PersonalityTrait trait : PersonalityTrait.values()) {
                                                                        builder.suggest(trait.name());
                                                                    }
                                                                    return builder.buildFuture();
                                                                }).executes(context -> {
                                                                    var source = context.getSource();
                                                                    HumanEntity human = findNearest(source);

                                                                    if (human == null) {
                                                                        source.sendFailure(Component.literal(
                                                                                "No human NPC found within 16 blocks."
                                                                        ));
                                                                        return 0;
                                                                    }

                                                                    PersonalityTrait trait;
                                                                    try {
                                                                        String traitName = StringArgumentType.getString(context, "trait");

                                                                        trait = java.util.Arrays.stream(PersonalityTrait.values())
                                                                                .filter(value -> value.name().equalsIgnoreCase(traitName))
                                                                                .findFirst()
                                                                                .orElseThrow(() -> new IllegalArgumentException("Invalid trait"));
                                                                    } catch (IllegalArgumentException exception) {
                                                                        source.sendFailure(Component.literal(
                                                                                "Invalid trait. Options: "
                                                                                        + java.util.Arrays.toString(PersonalityTrait.values())
                                                                        ));
                                                                        return 0;
                                                                    }

                                                                    TribeMemberProfile old = human.getProfile();
                                                                    EnumSet<PersonalityTrait> traits =
                                                                            old.getPersonalityTraits().isEmpty()
                                                                                    ? EnumSet.noneOf(PersonalityTrait.class)
                                                                                    : EnumSet.copyOf(old.getPersonalityTraits());
                                                                    traits.add(trait);

                                                                    human.setProfile(new TribeMemberProfile(
                                                                            old.getRole(),
                                                                            traits,
                                                                            old.getSettlementId()
                                                                    ));

                                                                    source.sendSuccess(() -> Component.literal(
                                                                            "Added trait " + trait
                                                                    ), false);
                                                                    return 1;
                                                                })))
                                                        .then(Commands.literal("clear")
                                                                .executes(context -> {
                                                                    var source = context.getSource();
                                                                    HumanEntity human = findNearest(source);

                                                                    if (human == null) {
                                                                        source.sendFailure(Component.literal(
                                                                                "No human NPC found within 16 blocks."
                                                                        ));
                                                                        return 0;
                                                                    }

                                                                    TribeMemberProfile old = human.getProfile();
                                                                    human.setProfile(new TribeMemberProfile(
                                                                            old.getRole(),
                                                                            EnumSet.noneOf(PersonalityTrait.class),
                                                                            old.getSettlementId()
                                                                    ));

                                                                    source.sendSuccess(() -> Component.literal(
                                                                            "Cleared NPC personality traits."
                                                                    ), false);
                                                                    return 1;
                                                                })))
                                        )
                        )
        );
    }

    private static HumanEntity findNearest(
            net.minecraft.commands.CommandSourceStack source
    ) {
        var position = source.getPosition();

        AABB area = new AABB(
                position.x - 16, position.y - 16, position.z - 16,
                position.x + 16, position.y + 16, position.z + 16
        );

        List<HumanEntity> humans = source.getLevel().getEntities(
                EntityTypeTest.forClass(HumanEntity.class),
                area,
                human -> true
        );

        return humans.stream()
                .min(Comparator.comparingDouble(
                        human -> human.position().distanceToSqr(position)
                ))
                .orElse(null);
    }
}
