package projectc.mod.civilization;


public class SettlementFoodSimulation {

    public static final int TICKS_PER_MINECRAFT_DAY = 24_000;

    public static final int FOOD_CYCLE_DAYS = 2;

    public static final int FOOD_CYCLE_TICKS = 400;
            //TICKS_PER_MINECRAFT_DAY * FOOD_CYCLE_DAYS;

    public static final double BASE_FOOD_PRODUCTION_RATE = 15.0;

    public static final double BASE_FOOD_CONSUMPTION_RATE = 5.0;

    public static final double BASE_FOOD_LIFETIME_MULTIPLIER = 1.0;

    public static final double FOOD_REQUIRED_PER_NEW_POPULATION = 10.0;

    public static double calculateProduction(
            Settlement settlement
    ) {
        double foodProducingFraction = 1.0;
        double productionMultiplier = 1.0;

        return settlement.getPopulation()
                * foodProducingFraction
                * productionMultiplier
                * BASE_FOOD_PRODUCTION_RATE;
    }

    public static double calculateConsumption(
            Settlement settlement
    ) {
        return settlement.getPopulation()
                * BASE_FOOD_CONSUMPTION_RATE
                * FOOD_CYCLE_DAYS;
    }

    public static double calculateFoodBalance(
            Settlement settlement
    ) {
        double production = calculateProduction(settlement);
        double consumption = calculateConsumption(settlement);

        return settlement.getFoodStorage()
                + production
                - consumption;
    }

    public static void applyFoodCycle(
            Settlement settlement
    ) {
        double oldFood = settlement.getFoodStorage();
        double production = calculateProduction(settlement);
        double consumption = calculateConsumption(settlement);

        double foodBalance = oldFood + production - consumption;

        if (foodBalance <= 0.0) {
            settlement.setFoodStorage(0.0);
            return;
        }

        int populationGrowth = (int) Math.floor(
                foodBalance / FOOD_REQUIRED_PER_NEW_POPULATION
        );

        double foodUsedForGrowth =
                populationGrowth * FOOD_REQUIRED_PER_NEW_POPULATION;

        settlement.setPopulation(
                settlement.getPopulation() + populationGrowth
        );

        settlement.setFoodStorage(
                foodBalance - foodUsedForGrowth
        );
    }

    public static double calculateFoodShortage(
            Settlement settlement
    ) {
        double oldFood = settlement.getFoodStorage();
        double production = calculateProduction(settlement);
        double consumption = calculateConsumption(settlement);

        double shortage = consumption - oldFood - production;

        return Math.max(0.0, shortage);
    }

    public static double calculateGrowthPotential(
            Settlement settlement
    ) {
        double foodBalance = calculateFoodBalance(settlement);

        if (foodBalance <= 0.0) {
            return 0.0;
        }

        return foodBalance;
    }

    public static int calculatePopulationGrowth(
            Settlement settlement
    ) {
        double growthPotential = calculateGrowthPotential(settlement);

        return (int) Math.floor(
                growthPotential / FOOD_REQUIRED_PER_NEW_POPULATION
        );
    }

    public static void applyPopulationGrowth(
            Settlement settlement
    ) {
        int populationGrowth = calculatePopulationGrowth(settlement);

        if (populationGrowth <= 0) {
            return;
        }

        settlement.setPopulation(
                settlement.getPopulation() + populationGrowth
        );
    }
}

