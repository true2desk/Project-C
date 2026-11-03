package projectc.mod.npc;

import java.util.EnumSet;
import java.util.Set;

public class TribeMemberProfile {

    private final TribeRole role;
    private final EnumSet<PersonalityTrait> personalityTraits;
    private final long settlementId;

    public TribeMemberProfile(
            TribeRole role,
            Set<PersonalityTrait> personalityTraits,
            long settlementId
    ) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        if (personalityTraits == null) {
            throw new IllegalArgumentException("Personality traits cannot be null");
        }
        if (settlementId < -1) {
            throw new IllegalArgumentException("Settlement ID must be -1 (unassigned) or non-negative");
        }

        this.role = role;
        this.personalityTraits = personalityTraits.isEmpty()
                ? EnumSet.noneOf(PersonalityTrait.class)
                : EnumSet.copyOf(personalityTraits);
        this.settlementId = settlementId;
    }

    public TribeRole getRole() {
        return role;
    }

    public Set<PersonalityTrait> getPersonalityTraits() {
        return Set.copyOf(personalityTraits);
    }

    public boolean hasTrait(PersonalityTrait trait) {
        return personalityTraits.contains(trait);
    }

    public long getSettlementId() {
        return settlementId;
    }
}