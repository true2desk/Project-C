package projectc.mod.npc;

public enum HumanLifeStage {
    toddler,
    child,
    adult,
    senior;

    public static HumanLifeStage fromAge(long age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }

        if (age < 5) {
            return toddler;
        }

        if (age < 12) {
            return child;
        }

        if (age < 60) {
            return adult;
        }

        return senior;
    }
}