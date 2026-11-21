package projectc.mod.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import projectc.mod.worldgen.BiomeRemoval;

import java.util.List;

@Mixin(MultiNoiseBiomeSource.class)
public abstract class MultiNoiseBiomeSourceMixin {

    @Unique
    private Climate.ParameterList<Holder<Biome>> projectc$filteredParameters;

    @Inject(
            method = "parameters",
            at = @At("RETURN"),
            cancellable = true
    )
    private void projectc$filterRemovedBiomes(
            CallbackInfoReturnable<Climate.ParameterList<Holder<Biome>>> cir
    ) {
        if (projectc$filteredParameters != null) {
            cir.setReturnValue(projectc$filteredParameters);
            return;
        }

        Climate.ParameterList<Holder<Biome>> original = cir.getReturnValue();

        List<Pair<Climate.ParameterPoint, Holder<Biome>>> filtered =
                original.values().stream()
                        .filter(pair -> !BiomeRemoval.isRemoved(pair.getSecond()))
                        .toList();

        if (filtered.size() != original.values().size() && !filtered.isEmpty()) {
            projectc$filteredParameters = new Climate.ParameterList<>(filtered);
        } else {
            projectc$filteredParameters = original;
        }

        cir.setReturnValue(projectc$filteredParameters);
    }
}