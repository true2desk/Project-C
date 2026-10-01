package projectc.mod.world;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.gamerules.GameRules;

public class WorldConfiguration {

    public static void initialize(MinecraftServer server) {
        server.getAllLevels().forEach(level ->
                level.getGameRules().set(
                        GameRules.ALLOW_ENTERING_NETHER_USING_PORTALS,
                        false,
                        server
                )
        );
    }
}