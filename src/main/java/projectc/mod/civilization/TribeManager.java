package projectc.mod.civilization;

import java.util.ArrayList;
import java.util.List;

public class TribeManager {

    private final List<Tribe> tribes = new ArrayList<>();
    private long nextTribeId = 1;

    public Tribe createTribe() {
        Tribe tribe = new Tribe(nextTribeId++);
        tribes.add(tribe);

        return tribe;
    }

    public List<Tribe> getTribes() {
        return tribes;
    }

    public void addAll(List<Tribe> tribes) {
        this.tribes.addAll(tribes);

        for (Tribe tribe : tribes) {
            if (tribe.getId() >= nextTribeId) {
                nextTribeId = tribe.getId() + 1;
            }
        }
    }

    public void tick() {
        // Tribe simulation will happen here.
    }
}


