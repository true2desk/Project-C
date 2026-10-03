package projectc.mod.resource;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class LooseStickEntity extends Entity {

    public LooseStickEntity(
            EntityType<? extends LooseStickEntity> entityType,
            Level level
    ) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {

    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        spawnAtLocation(level, new ItemStack(Items.STICK));
        discard();

        return true;
    }
    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {

    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {

    }

    @Override
    protected AABB makeBoundingBox(Vec3 position) {
        double localWidth = 2.55 / 16.0;
        double localLength = 15.85 / 16.0;

        double angle = Math.toRadians(getYRot());
        double cos = Math.abs(Math.cos(angle));
        double sin = Math.abs(Math.sin(angle));

        double widthX = localWidth * cos + localLength * sin;
        double widthZ = localWidth * sin + localLength * cos;

        double modelCenterX = (6.275 / 16.0) - 0.5;
        double modelCenterZ = (7.925 / 16.0) - 0.5;

        double offsetX = modelCenterX * Math.cos(angle) + modelCenterZ * Math.sin(angle);
        double offsetZ = -modelCenterX * Math.sin(angle) + modelCenterZ * Math.cos(angle);

        Vec3 center = position.add(offsetX, 0.0, offsetZ);

        return new AABB(
                center.x - widthX / 2.0,
                position.y,
                center.z - widthZ / 2.0,
                center.x + widthX / 2.0,
                position.y + 1.0 / 16.0,
                center.z + widthZ / 2.0
        );
    }

}
