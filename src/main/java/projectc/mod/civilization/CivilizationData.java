package projectc.mod.civilization;

import com.mojang.serialization.Codec;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import projectc.mod.ProjectC;

import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.ArrayList;
import java.util.List;

public class CivilizationData extends SavedData {

    private static final Codec<CivilizationData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Tribe.CODEC.listOf()
                            .fieldOf("tribes")
                            .forGetter(data -> data.tribeManager.getTribes()),

                    Settlement.CODEC.listOf()
                            .fieldOf("settlements")
                            .forGetter(data -> data.settlementManager.getSettlements())
            ).apply(instance, CivilizationData::new)
    );

    private static final SavedDataType<CivilizationData> TYPE = new SavedDataType<>(
            ProjectC.id("civilization_data"),
            CivilizationData::new,
            CODEC,
            null
    );

    private final TribeManager tribeManager = new TribeManager();
    private final SettlementManager settlementManager = new SettlementManager();

    public CivilizationData() {
    }
    private CivilizationData(
            List<Tribe> tribes,
            List<Settlement> settlements
    ) {
        tribeManager.addAll(tribes);
        settlementManager.addAll(settlements);
    }

    public TribeManager getTribeManager() {
        return tribeManager;
    }
    public SettlementManager getSettlementManager() {
        return settlementManager;
    }

    public static CivilizationData get(MinecraftServer server) {
        ServerLevel level = server.getLevel(ServerLevel.OVERWORLD);

        if (level == null) {
            return new CivilizationData();
        }

        return level.getDataStorage().computeIfAbsent(TYPE);
    }

    public void tick() {
        tribeManager.tick();
        settlementManager.tick();
        setDirty();
    }
}
