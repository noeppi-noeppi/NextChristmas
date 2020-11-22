package io.github.noeppi_noeppi.mods.nextchristmas.mixin;

import io.github.noeppi_noeppi.mods.nextchristmas.ModWorldGen;
import net.minecraft.block.BlockState;
import net.minecraft.block.IceBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.Random;

@Mixin(IceBlock.class)
public class MixinIceBlock {

    @Inject(
            method = "randomTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/server/ServerWorld;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo info) {
        if (Objects.equals(ModWorldGen.christmasForest.getRegistryName(), world.getBiome(pos).getRegistryName())) {
            info.cancel();
        }
    }
}
