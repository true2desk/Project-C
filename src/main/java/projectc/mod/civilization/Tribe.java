package projectc.mod.civilization;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class Tribe {

    public static final Codec<Tribe> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.LONG.fieldOf("id").forGetter(Tribe::getId)
            ).apply(instance, Tribe::new)
    );

    private final long id;

    public Tribe(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}