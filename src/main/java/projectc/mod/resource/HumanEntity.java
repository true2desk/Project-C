package projectc.mod.resource;

import com.mojang.serialization.Codec;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import projectc.mod.npc.PersonalityTrait;
import projectc.mod.npc.TribeMemberProfile;
import projectc.mod.npc.TribeRole;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class HumanEntity extends PathfinderMob {

    private TribeMemberProfile profile = new TribeMemberProfile(
            TribeRole.Gatherer,
            EnumSet.noneOf(PersonalityTrait.class),
            -1L
    );

    public HumanEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    public TribeMemberProfile getProfile() {
        return profile;
    }

    public void setProfile(TribeMemberProfile profile) {
        if (profile == null) {
            throw new IllegalArgumentException("Profile cannot be null");
        }
        this.profile = profile;
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);

        output.putString("tribe_role", profile.getRole().name());
        output.store(
                "personality_traits",
                Codec.STRING.listOf(),
                profile.getPersonalityTraits().stream()
                        .map(Enum::name)
                        .sorted()
                        .toList()
        );
        output.putLong("settlement_id", profile.getSettlementId());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);

        TribeRole role;
        try {
            role = TribeRole.valueOf(
                    input.getStringOr("tribe_role", TribeRole.Gatherer.name())
            );
        } catch (IllegalArgumentException exception) {
            role = TribeRole.Gatherer;
        }

        List<String> storedTraits = input.read(
                "personality_traits",
                Codec.STRING.listOf()
        ).orElse(List.of());

        Set<PersonalityTrait> traits =
                EnumSet.noneOf(PersonalityTrait.class);

        for (String traitName : storedTraits) {
            try {
                traits.add(PersonalityTrait.valueOf(traitName));
            } catch (IllegalArgumentException ignored) {
                // Ignore unknown traits so saved entities remain loadable.
            }
        }

        long settlementId = input.getLongOr("settlement_id", -1L);
        if (settlementId < -1L) {
            settlementId = -1L;
        }

        this.profile = new TribeMemberProfile(role, traits, settlementId);
    }

    public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH, 20.0)
                .add(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED, 0.25)
                .add(net.minecraft.world.entity.ai.attributes.Attributes.FOLLOW_RANGE, 16.0);
    }
}