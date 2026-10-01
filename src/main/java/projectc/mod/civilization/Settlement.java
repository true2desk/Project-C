package projectc.mod.civilization;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class Settlement {

    public static final Codec<Settlement> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.LONG.fieldOf("id").forGetter(Settlement::getId),
                    Codec.LONG.fieldOf("tribe_id").forGetter(Settlement::getTribeId),
                    Codec.LONG.fieldOf("x").forGetter(Settlement::getX),
                    Codec.LONG.fieldOf("y").forGetter(Settlement::getY),
                    Codec.LONG.fieldOf("z").forGetter(Settlement::getZ),
                    Codec.INT.fieldOf("population").forGetter(Settlement::getPopulation),
                    Codec.DOUBLE.fieldOf("food_storage").forGetter(Settlement::getFoodStorage)
            ).apply(instance, Settlement::new)
    );

    private final long id;
    private final long tribeId;
    private final long x;
    private final long y;
    private final long z;
    private int population;
    private double foodStorage;

    public Settlement(
            long id,
            long tribeId,
            long x,
            long y,
            long z,
            int population,
            double foodStorage
    ) {
        this.id = id;
        this.tribeId = tribeId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.population = population;
        this.foodStorage = foodStorage;
    }

    public long getId() {
        return id;
    }

    public long getTribeId() {
        return tribeId;
    }

    public long getX() {
        return x;
    }
    public long getY() {
        return y;
    }
    public long getZ() {
        return z;
    }

    public int getPopulation() {
        return population;
    }
    public void setPopulation(int population) {
        this.population = population;
    }

    public double getFoodStorage() {
        return foodStorage;
    }

    public void setFoodStorage(double foodStorage) {
        this.foodStorage = foodStorage;
    }
}
