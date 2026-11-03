package projectc.mod.civilization;

import com.mojang.brigadier.CommandDispatcher;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import projectc.mod.civilization.calendar.CivilizationCalendar;

public class CalendarCommands {

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
                        .then(
                                Commands.literal("calendar")
                                        .executes(context -> {
                                            CommandSourceStack source =
                                                    context.getSource();

                                            CivilizationCalendar.CalendarDate date =
                                                    CivilizationCalendar.getCurrentDate(
                                                            source.getLevel()
                                                    );

                                            source.sendSuccess(
                                                    () -> Component.literal(
                                                            "Current date: " + date
                                                    ),
                                                    false
                                            );

                                            return 1;
                                        })
                        )
        );
    }
}

