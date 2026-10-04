package projectc.mod;

import net.minecraft.util.StringRepresentable;

public enum LooseStonePosition implements StringRepresentable {
    TOP_LEFT("top_left"),
    TOP_RIGHT("top_right"),
    BOTTOM_LEFT("bottom_left"),
    BOTTOM_RIGHT("bottom_right"),
    MIDDLE("middle");

    private final String name;

    LooseStonePosition(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}