// Made with Blockbench 3.7.2
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

package io.github.noeppi_noeppi.mods.nextchristmas.entities;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

import javax.annotation.Nonnull;

@SuppressWarnings("FieldCanBeLocal")
public class SledgeModel extends EntityModel<Sledge> {

	private final ModelRenderer bb_main;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;
	private final ModelRenderer cube_r5;
	private final ModelRenderer cube_r6;
	private final ModelRenderer cube_r7;
	private final ModelRenderer cube_r8;

	public SledgeModel() {
		this.textureWidth = 128;
		this.textureHeight = 128;

		this.bb_main = new ModelRenderer(this);
		this.bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		this.bb_main.setTextureOffset(0, 18).addBox(2.5F, -1.0F, -7.0F, 2.0F, 1.0F, 17.0F, 0.03F, false);
		this.bb_main.setTextureOffset(0, 0).addBox(-4.5F, -1.0F, -7.0F, 2.0F, 1.0F, 17.0F, 0.03F, true);
		this.bb_main.setTextureOffset(22, 12).addBox(-2.6F, -6.0F, -4.0F, 5.0F, 1.0F, 1.0F, 0.05F, false);
		this.bb_main.setTextureOffset(22, 14).addBox(-2.6F, -6.0F, 1.0F, 5.0F, 1.0F, 1.0F, 0.05F, false);
		this.bb_main.setTextureOffset(0, 12).addBox(-2.6F, -6.0F, 6.0F, 5.0F, 1.0F, 1.0F, 0.05F, false);
		this.bb_main.setTextureOffset(0, 80).addBox(-0.5F, -6.0F, -7.0F, 1.0F, 1.0F, 17.0F, 0.0F, false);

		this.cube_r1 = new ModelRenderer(this);
		this.cube_r1.setRotationPoint(-2.8F, -4.6F, 12.0F);
		this.bb_main.addChild(this.cube_r1);
		this.setRotationAngle(this.cube_r1, 0.0F, 0.0F, 0.1745F);
		this.cube_r1.setTextureOffset(4, 6).addBox(-0.5F, -0.5F, -6.0F, 1.0F, 5.0F, 1.0F, 0.0F, true);
		this.cube_r1.setTextureOffset(12, 0).addBox(-0.5F, -0.5F, -11.0F, 1.0F, 5.0F, 1.0F, 0.0F, true);
		this.cube_r1.setTextureOffset(4, 0).addBox(-0.5F, -0.5F, -16.0F, 1.0F, 5.0F, 1.0F, 0.0F, true);

		this.cube_r2 = new ModelRenderer(this);
		this.cube_r2.setRotationPoint(-2.6F, -5.5F, 0.0F);
		this.bb_main.addChild(this.cube_r2);
		this.setRotationAngle(this.cube_r2, 0.0F, 0.0F, 0.1745F);
		this.cube_r2.setTextureOffset(0, 58).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 20.0F, 0.0F, true);

		this.cube_r3 = new ModelRenderer(this);
		this.cube_r3.setRotationPoint(-2.8922F, -3.7575F, -9.1857F);
		this.bb_main.addChild(this.cube_r3);
		this.setRotationAngle(this.cube_r3, 0.0F, 0.0F, 0.1745F);
		this.cube_r3.setTextureOffset(12, 6).addBox(-0.5F, -3.6893F, -1.3143F, 1.0F, 4.0F, 1.0F, 0.05F, true);

		this.cube_r4 = new ModelRenderer(this);
		this.cube_r4.setRotationPoint(-2.8922F, -3.7575F, -9.1857F);
		this.bb_main.addChild(this.cube_r4);
		this.setRotationAngle(this.cube_r4, -0.7854F, 0.0F, 0.1745F);
		this.cube_r4.setTextureOffset(22, 6).addBox(-0.5F, 0.1187F, -0.7296F, 1.0F, 1.0F, 5.0F, 0.1F, true);

		this.cube_r5 = new ModelRenderer(this);
		this.cube_r5.setRotationPoint(2.8F, -4.6F, 12.0F);
		this.bb_main.addChild(this.cube_r5);
		this.setRotationAngle(this.cube_r5, 0.0F, 0.0F, -0.1745F);
		this.cube_r5.setTextureOffset(0, 6).addBox(-0.5F, -0.5F, -6.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
		this.cube_r5.setTextureOffset(8, 0).addBox(-0.5F, -0.5F, -11.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
		this.cube_r5.setTextureOffset(0, 0).addBox(-0.5F, -0.5F, -16.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);

		this.cube_r6 = new ModelRenderer(this);
		this.cube_r6.setRotationPoint(2.6F, -5.5F, 0.0F);
		this.bb_main.addChild(this.cube_r6);
		this.setRotationAngle(this.cube_r6, 0.0F, 0.0F, -0.1745F);
		this.cube_r6.setTextureOffset(0, 36).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 20.0F, 0.0F, false);

		this.cube_r7 = new ModelRenderer(this);
		this.cube_r7.setRotationPoint(2.8922F, -3.7575F, -9.1857F);
		this.bb_main.addChild(this.cube_r7);
		this.setRotationAngle(this.cube_r7, 0.0F, 0.0F, -0.1745F);
		this.cube_r7.setTextureOffset(8, 6).addBox(-0.5F, -3.6893F, -1.3143F, 1.0F, 4.0F, 1.0F, 0.05F, false);

		this.cube_r8 = new ModelRenderer(this);
		this.cube_r8.setRotationPoint(2.8922F, -3.7575F, -9.1857F);
		this.bb_main.addChild(this.cube_r8);
		this.setRotationAngle(this.cube_r8, -0.7854F, 0.0F, -0.1745F);
		this.cube_r8.setTextureOffset(22, 0).addBox(-0.5F, 0.1187F, -0.7296F, 1.0F, 1.0F, 5.0F, 0.1F, false);
	}

	@Override
	public void setRotationAngles(@Nonnull Sledge sledge, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void render(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		this.bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}