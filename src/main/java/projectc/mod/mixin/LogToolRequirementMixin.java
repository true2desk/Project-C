package projectc.mod.mixin;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class LogToolRequirementMixin {

    @Inject(
            method = "requiresCorrectToolForDrops",
            at = @At("HEAD"),
            cancellable = true
    )
    private void projectc$requireCorrectToolForLogs(
            CallbackInfoReturnable<Boolean> cir
    ) {
        BlockState state = (BlockState) (Object) this;

        if (state.is(BlockTags.LOGS)) {
            cir.setReturnValue(true);
        }
    }
}