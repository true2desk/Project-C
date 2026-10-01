package projectc.mod.civilization;

import java.util.ArrayList;
import java.util.List;

import projectc.mod.civilization.SettlementFoodSimulation;

public class SettlementManager {

    private final List<Settlement> settlements = new ArrayList<>();
    private long nextSettlementId = 1;

    public Settlement createSettlement(
            long tribeId,
            long x,
            long y,
            long z,
            int population
    ) {
        Settlement settlement = new Settlement(
                nextSettlementId++,
                tribeId,
                x,
                y,
                z,
                population,
                100.0
        );


        settlements.add(settlement);

        return settlement;
    }

    public List<Settlement> getSettlements() {
        return settlements;
    }

    public void addAll(List<Settlement> settlements) {
        this.settlements.addAll(settlements);

        for (Settlement settlement : settlements) {
            if (settlement.getId() >= nextSettlementId) {
                nextSettlementId = settlement.getId() + 1;
            }
        }
    }

    public int getPopulationForTribe(long tribeId) {
        int population = 0;

        for (Settlement settlement : settlements) {
            if (settlement.getTribeId() == tribeId) {
                population += settlement.getPopulation();
            }
        }

        return population;
    }
    public int getSettlementCountForTribe(long tribeId) {
        int count = 0;

        for (Settlement settlement : settlements) {
            if (settlement.getTribeId() == tribeId) {
                count++;
            }
        }

        return count;
    }
    public double getFoodForTribe(long tribeId) {
        double food = 0.0;

        for (Settlement settlement : settlements) {
            if (settlement.getTribeId() == tribeId) {
                food += settlement.getFoodStorage();
            }
        }

        return food;
    }

    public void tick() {
        for (Settlement settlement : settlements) {
            SettlementFoodSimulation.applyFoodCycle(settlement);
        }
    }
}