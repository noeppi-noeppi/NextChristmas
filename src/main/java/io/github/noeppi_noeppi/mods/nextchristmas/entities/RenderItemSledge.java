package io.github.noeppi_noeppi.mods.nextchristmas.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.noeppi_noeppi.libx.render.ClientTickHandler;
import io.github.noeppi_noeppi.mods.nextchristmas.ModEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.LazyValue;
import net.minecraft.util.math.vector.Vector3f;

import javax.annotation.Nonnull;

public class RenderItemSledge extends ItemStackTileEntityRenderer {

    private static final RenderItemSledge INSTANCE = new RenderItemSledge();

    private final LazyValue<Sledge> sledge;

    private RenderItemSledge() {
        //noinspection ConstantConditions
        this.sledge = new LazyValue<>(() -> new Sledge(ModEntities.sledge, Minecraft.getInstance().world));
    }

    @Override
    public void func_239207_a_(@Nonnull ItemStack stack, @Nonnull ItemCameraTransforms.TransformType type, @Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer buffer, int light, int overlay) {
        if (stack.getItem() instanceof ItemSledge) {
            matrixStack.push();

            matrixStack.translate(0.5, 0, 0.5);
            if (type == ItemCameraTransforms.TransformType.GUI) {
                matrixStack.rotate(Vector3f.YP.rotationDegrees(180));
            }
            matrixStack.scale(0.6f, 0.6f, 0.6f);

            Sledge sledge = this.sledge.getValue();
            EntityRenderer<? super Sledge> render = Minecraft.getInstance().getRenderManager().getRenderer(sledge);
            if (Minecraft.getInstance().world != null) {
                sledge.setWorld(Minecraft.getInstance().world);
            }
            sledge.setPosition(0, 0, 0);
            sledge.setSledgeType(((ItemSledge) stack.getItem()).type);
            sledge.setDamageTaken(0);
            sledge.setForwardDirection(0);
            sledge.setTimeSinceHit(0);
            sledge.rotationYaw = 0;
            sledge.rotationPitch = 0;
            sledge.ticksExisted = ClientTickHandler.ticksInGame;
            render.render(sledge, 0, Minecraft.getInstance().getRenderPartialTicks(), matrixStack, buffer, light);

            matrixStack.pop();
        }
    }

    public static RenderItemSledge get() {
        return INSTANCE;
    }
}
