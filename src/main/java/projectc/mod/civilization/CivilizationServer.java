package projectc.mod.civilization;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

public class CivilizationServer {

    private static int ticksUntilSimulation =
            SettlementFoodSimulation.FOOD_CYCLE_TICKS;

    public static void initialize() {
        ServerTickEvents.END_SERVER_TICK.register(
                CivilizationServer::tick
        );
    }

    private static void tick(MinecraftServer server) {
        ticksUntilSimulation--;

        if (ticksUntilSimulation > 0) {
            return;
        }

        ticksUntilSimulation =
                SettlementFoodSimulation.FOOD_CYCLE_TICKS;

        CivilizationData data = CivilizationData.get(server);
        data.tick();
    }
}