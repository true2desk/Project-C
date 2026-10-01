package projectc.mod.civilization;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.LongArgumentType;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import java.util.List;

public class CivCommands {

    public static void initialize() {
        CommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess, environment) ->
                        register(dispatcher)
        );
    }

    private static void register(
            CommandDispatcher<CommandSourceStack> dispatcher
    ) {
        dispatcher.register(
                Commands.literal("civ")

                        // /civ tribe ...
                        .then(
                                Commands.literal("tribe")
                                        .then(
                                                Commands.literal("create")
                                                        .executes(context -> {
                                                            CommandSourceStack source =
                                                                    context.getSource();

                                                            CivilizationData data =
                                                                    CivilizationData.get(
                                                                            source.getServer()
                                                                    );

                                                            Tribe tribe =
                                                                    data.getTribeManager()
                                                                            .createTribe();

                                                            data.setDirty();

                                                            long tribeId =
                                                                    tribe.getId();

                                                            source.sendSuccess(
                                                                    () -> Component.literal(
                                                                            "Created tribe #" +
                                                                                    tribeId
                                                                    ),
                                                                    false
                                                            );

                                                            return 1;
                                                        })
                                        )
                                        .then(
                                                Commands.literal("list")
                                                        .executes(context -> {
                                                            CommandSourceStack source =
                                                                    context.getSource();

                                                            CivilizationData data =
                                                                    CivilizationData.get(
                                                                            source.getServer()
                                                                    );

                                                            List<Tribe> tribes =
                                                                    data.getTribeManager()
                                                                            .getTribes();

                                                            source.sendSuccess(
                                                                    () -> Component.literal(
                                                                            "Loaded tribes: " +
                                                                                    tribes.size()
                                                                    ),
                                                                    false
                                                            );

                                                            for (Tribe tribe : tribes) {
                                                                long tribeId =
                                                                        tribe.getId();

                                                                int population =
                                                                        data.getSettlementManager()
                                                                                .getPopulationForTribe(
                                                                                        tribeId
                                                                                );

                                                                double food =
                                                                        data.getSettlementManager()
                                                                                .getFoodForTribe(
                                                                                        tribeId
                                                                                );

                                                                int settlementCount =
                                                                        data.getSettlementManager()
                                                                                .getSettlementCountForTribe(
                                                                                        tribeId
                                                                                );

                                                                source.sendSuccess(
                                                                        () -> Component.literal(
                                                                                "Tribe #" +
                                                                                        tribeId +
                                                                                        " | Population: " +
                                                                                        population +
                                                                                        " | Food: " +
                                                                                        food +
                                                                                        " | Settlements: " +
                                                                                        settlementCount
                                                                        ),
                                                                        false
                                                                );
                                                            }

                                                            return 1;
                                                        })
                                        )
                        )

                        // /civ settlement ...
                        .then(
                                Commands.literal("settlement")

                                        // /civ settlement create
                                        .then(
                                                Commands.literal("create")
                                                        .executes(context -> {
                                                            CommandSourceStack source =
                                                                    context.getSource();

                                                            CivilizationData data =
                                                                    CivilizationData.get(
                                                                            source.getServer()
                                                                    );

                                                            long defaultTribeId = 1;

                                                            Tribe existingTribe =
                                                                    data.getTribeManager()
                                                                            .getTribes()
                                                                            .stream()
                                                                            .filter(
                                                                                    tribe ->
                                                                                            tribe.getId()
                                                                                                    == defaultTribeId
                                                                            )
                                                                            .findFirst()
                                                                            .orElse(null);

                                                            final long tribeId;

                                                            if (existingTribe == null) {
                                                                Tribe newTribe =
                                                                        data.getTribeManager()
                                                                                .createTribe();

                                                                tribeId =
                                                                        newTribe.getId();
                                                            } else {
                                                                tribeId =
                                                                        existingTribe.getId();
                                                            }

                                                            Settlement settlement =
                                                                    data.getSettlementManager()
                                                                            .createSettlement(
                                                                                    tribeId,
                                                                                    Math.round(
                                                                                            source.getPosition().x()
                                                                                    ),
                                                                                    Math.round(
                                                                                            source.getPosition().y()
                                                                                    ),
                                                                                    Math.round(
                                                                                            source.getPosition().z()
                                                                                    ),
                                                                                    50
                                                                            );

                                                            data.setDirty();

                                                            source.sendSuccess(
                                                                    () -> Component.literal(
                                                                            "Created settlement #" +
                                                                                    settlement.getId() +
                                                                                    " for tribe #" +
                                                                                    tribeId +
                                                                                    " with population " +
                                                                                    settlement.getPopulation()
                                                                    ),
                                                                    false
                                                            );

                                                            return 1;
                                                        })

                                                        // /civ settlement create tribeID <id>
                                                        .then(
                                                                Commands.literal("tribeID")
                                                                        .then(
                                                                                Commands.argument(
                                                                                                "id",
                                                                                                LongArgumentType.longArg(1)
                                                                                        )
                                                                                        .executes(context -> {
                                                                                            CommandSourceStack source =
                                                                                                    context.getSource();

                                                                                            CivilizationData data =
                                                                                                    CivilizationData.get(
                                                                                                            source.getServer()
                                                                                                    );

                                                                                            long tribeId =
                                                                                                    LongArgumentType.getLong(
                                                                                                            context,
                                                                                                            "id"
                                                                                                    );

                                                                                            Tribe tribe =
                                                                                                    data.getTribeManager()
                                                                                                            .getTribes()
                                                                                                            .stream()
                                                                                                            .filter(
                                                                                                                    t ->
                                                                                                                            t.getId()
                                                                                                                                    == tribeId
                                                                                                            )
                                                                                                            .findFirst()
                                                                                                            .orElse(null);

                                                                                            if (tribe == null) {
                                                                                                source.sendFailure(
                                                                                                        Component.literal(
                                                                                                                "Tribe #" +
                                                                                                                        tribeId +
                                                                                                                        " does not exist."
                                                                                                        )
                                                                                                );

                                                                                                return 0;
                                                                                            }

                                                                                            Settlement settlement =
                                                                                                    data.getSettlementManager()
                                                                                                            .createSettlement(
                                                                                                                    tribeId,
                                                                                                                    Math.round(
                                                                                                                            source.getPosition().x()
                                                                                                                    ),
                                                                                                                    Math.round(
                                                                                                                            source.getPosition().y()
                                                                                                                    ),
                                                                                                                    Math.round(
                                                                                                                            source.getPosition().z()
                                                                                                                    ),
                                                                                                                    50
                                                                                                            );

                                                                                            data.setDirty();

                                                                                            source.sendSuccess(
                                                                                                    () -> Component.literal(
                                                                                                            "Created settlement #" +
                                                                                                                    settlement.getId() +
                                                                                                                    " for tribe #" +
                                                                                                                    tribeId +
                                                                                                                    " with population " +
                                                                                                                    settlement.getPopulation()
                                                                                                    ),
                                                                                                    false
                                                                                            );

                                                                                            return 1;
                                                                                        })
                                                                        )
                                                        )
                                        )

                                        // /civ settlement list
                                        .then(
                                                Commands.literal("list")
                                                        .executes(context -> {
                                                            CommandSourceStack source =
                                                                    context.getSource();

                                                            CivilizationData data =
                                                                    CivilizationData.get(
                                                                            source.getServer()
                                                                    );

                                                            for (
                                                                    Settlement settlement :
                                                                    data.getSettlementManager()
                                                                            .getSettlements()
                                                            ) {
                                                                sendSettlement(
                                                                        source,
                                                                        settlement
                                                                );
                                                            }

                                                            return 1;
                                                        })

                                                        // /civ settlement list tribeid <id>
                                                        .then(
                                                                Commands.literal("tribeid")
                                                                        .then(
                                                                                Commands.argument(
                                                                                                "tribeId",
                                                                                                LongArgumentType.longArg(1)
                                                                                        )
                                                                                        .executes(context -> {
                                                                                            CommandSourceStack source =
                                                                                                    context.getSource();

                                                                                            CivilizationData data =
                                                                                                    CivilizationData.get(
                                                                                                            source.getServer()
                                                                                                    );

                                                                                            long tribeId =
                                                                                                    LongArgumentType.getLong(
                                                                                                            context,
                                                                                                            "tribeId"
                                                                                                    );

                                                                                            for (
                                                                                                    Settlement settlement :
                                                                                                    data.getSettlementManager()
                                                                                                            .getSettlements()
                                                                                            ) {
                                                                                                if (
                                                                                                        settlement.getTribeId()
                                                                                                                != tribeId
                                                                                                ) {
                                                                                                    continue;
                                                                                                }

                                                                                                sendSettlement(
                                                                                                        source,
                                                                                                        settlement
                                                                                                );
                                                                                            }

                                                                                            return 1;
                                                                                        })
                                                                        )
                                                        )
                                        )
                        )
        );
    }

    private static void sendSettlement(
            CommandSourceStack source,
            Settlement settlement
    ) {
        source.sendSuccess(
                () -> Component.literal(
                        "Settlement " +
                                settlement.getId() +
                                " | Tribe " +
                                settlement.getTribeId() +
                                " | Population " +
                                settlement.getPopulation() +
                                " | Food " +
                                settlement.getFoodStorage() +
                                " | Position " +
                                settlement.getX() +
                                ", " +
                                settlement.getY() +
                                ", " +
                                settlement.getZ()
                ),
                false
        );
    }
}