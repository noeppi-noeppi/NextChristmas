package io.github.noeppi_noeppi.mods.nextchristmas.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.noeppi_noeppi.mods.nextchristmas.NextChristmas;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class ReindeerRender extends MobRenderer<Reindeer, ReindeerModel> {

    private static final ResourceLocation REINDEER_TEXTURE = new ResourceLocation(NextChristmas.getInstance().modid, "textures/entity/reindeer/reindeer.png");

    public ReindeerRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ReindeerModel(), 0.7F);
    }

    @Override
    protected void preRenderCallback(@Nonnull Reindeer entity, @Nonnull MatrixStack matrixStack, float partialTickTime) {
        super.preRenderCallback(entity, matrixStack, partialTickTime);
        matrixStack.scale(1.8f, 1.8f, 1.8f);
    }

    @Nonnull
    public ResourceLocation getEntityTexture(@Nonnull Reindeer entity) {
        return REINDEER_TEXTURE;
    }
}
