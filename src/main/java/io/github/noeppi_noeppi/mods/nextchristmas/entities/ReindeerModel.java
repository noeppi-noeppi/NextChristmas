// Made with Blockbench 3.7.2
// Exported for Minecraft version 1.15

package io.github.noeppi_noeppi.mods.nextchristmas.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

@SuppressWarnings("FieldCanBeLocal")
public class ReindeerModel extends EntityModel<Reindeer> {

	private final ModelRenderer root;
	private final ModelRenderer head;
	private final ModelRenderer real_head;
	private final ModelRenderer antler;
	private final ModelRenderer left;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;
	private final ModelRenderer cube_r5;
	private final ModelRenderer cube_r6;
	private final ModelRenderer cube_r7;
	private final ModelRenderer right;
	private final ModelRenderer cube_r8;
	private final ModelRenderer cube_r9;
	private final ModelRenderer cube_r10;
	private final ModelRenderer cube_r11;
	private final ModelRenderer cube_r12;
	private final ModelRenderer cube_r13;
	private final ModelRenderer cube_r14;
	private final ModelRenderer body;
	private final ModelRenderer legs;
	private final ModelRenderer cube_r15;
	private final ModelRenderer cube_r16;
	private final ModelRenderer cube_r17;
	private final ModelRenderer cube_r18;

	public ReindeerModel() {

		this.textureWidth = 128;
		this.textureHeight = 128;

		this.root = new ModelRenderer(this);
		this.root.setRotationPoint(4.0F, 12.0F, 0.0F);


		this.head = new ModelRenderer(this);
		this.head.setRotationPoint(9.8F, -6.0F, 0.0F);
		this.root.addChild(this.head);
		this.setRotationAngle(this.head, 0.0F, 0.0F, 1.0036F);
		this.head.setTextureOffset(100, 0).addBox(-3.8F, -11.0F, -3.0F, 5.0F, 12.0F, 6.0F, 0.0F, false);
		this.head.setTextureOffset(0, 20).addBox(-4.8F, -11.0F, -2.5F, 1.0F, 13.0F, 5.0F, 0.0F, false);
		this.head.setTextureOffset(122, 0).addBox(1.2F, -9.0F, -1.0F, 1.0F, 10.0F, 2.0F, 0.0F, false);
		this.head.setTextureOffset(122, 12).addBox(2.2F, -6.0F, -1.0F, 1.0F, 4.0F, 2.0F, 0.0F, false);

		this.real_head = new ModelRenderer(this);
		this.real_head.setRotationPoint(-4.8F, -10.8333F, 0.0F);
		this.head.addChild(this.real_head);
		this.setRotationAngle(this.real_head, 0.0F, 0.0F, 0.5236F);
		this.real_head.setTextureOffset(12, 20).addBox(1.0F, -2.1667F, -4.0F, 7.0F, 2.0F, 8.0F, 0.0F, false);
		this.real_head.setTextureOffset(12, 30).addBox(0.0F, -2.1667F, -2.0F, 1.0F, 2.0F, 4.0F, 0.0F, false);
		this.real_head.setTextureOffset(100, 18).addBox(2.0F, -5.1667F, -3.5F, 5.0F, 3.0F, 7.0F, 0.0F, false);
		this.real_head.setTextureOffset(22, 30).addBox(6.0F, -2.1667F, -2.5F, 1.0F, 2.0F, 5.0F, 0.0F, false);
		this.real_head.setTextureOffset(34, 30).addBox(1.0F, -3.1667F, -2.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);
		this.real_head.setTextureOffset(42, 20).addBox(2.5F, -7.1667F, -2.5F, 4.0F, 2.0F, 5.0F, 0.0F, false);
		this.real_head.setTextureOffset(124, 18).addBox(-1.0F, -3.1667F, -1.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		this.real_head.setTextureOffset(124, 20).addBox(-1.0F, -3.1667F, 0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		this.antler = new ModelRenderer(this);
		this.antler.setRotationPoint(-3.0F, 1.8333F, 0.0F);
		this.real_head.addChild(this.antler);


		this.left = new ModelRenderer(this);
		this.left.setRotationPoint(0.0F, 0.0F, -1.0F);
		this.antler.addChild(this.left);


		this.cube_r1 = new ModelRenderer(this);
		this.cube_r1.setRotationPoint(0.0F, -1.5F, -6.0F);
		this.left.addChild(this.cube_r1);
		this.setRotationAngle(this.cube_r1, -0.5236F, 0.0F, 0.0F);
		this.cube_r1.setTextureOffset(60, 22).addBox(-1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);

		this.cube_r2 = new ModelRenderer(this);
		this.cube_r2.setRotationPoint(-4.0F, -1.8F, -4.3F);
		this.left.addChild(this.cube_r2);
		this.setRotationAngle(this.cube_r2, -0.0873F, 0.7418F, 0.0873F);
		this.cube_r2.setTextureOffset(118, 32).addBox(-3.0F, -1.0F, 0.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);

		this.cube_r3 = new ModelRenderer(this);
		this.cube_r3.setRotationPoint(-4.0F, -2.0F, -5.0F);
		this.left.addChild(this.cube_r3);
		this.setRotationAngle(this.cube_r3, 0.0873F, 0.0873F, 0.0F);
		this.cube_r3.setTextureOffset(60, 20).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		this.cube_r4 = new ModelRenderer(this);
		this.cube_r4.setRotationPoint(-4.0F, -1.7F, -5.0F);
		this.left.addChild(this.cube_r4);
		this.setRotationAngle(this.cube_r4, 0.0F, -0.6545F, -0.1745F);
		this.cube_r4.setTextureOffset(118, 30).addBox(-3.0F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);

		this.cube_r5 = new ModelRenderer(this);
		this.cube_r5.setRotationPoint(-2.3037F, -2.039F, -4.7347F);
		this.left.addChild(this.cube_r5);
		this.setRotationAngle(this.cube_r5, -0.1745F, 0.1309F, 0.3054F);
		this.cube_r5.setTextureOffset(118, 28).addBox(-2.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, 0.0F, false);

		this.cube_r6 = new ModelRenderer(this);
		this.cube_r6.setRotationPoint(5.0F, -3.25F, -2.1F);
		this.left.addChild(this.cube_r6);
		this.setRotationAngle(this.cube_r6, 0.0873F, -0.5236F, -0.5236F);
		this.cube_r6.setTextureOffset(124, 22).addBox(-1.0F, -5.0F, 0.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);

		this.cube_r7 = new ModelRenderer(this);
		this.cube_r7.setRotationPoint(5.0F, -3.5F, -1.5F);
		this.left.addChild(this.cube_r7);
		this.setRotationAngle(this.cube_r7, 0.0F, -0.5236F, -0.3491F);
		this.cube_r7.setTextureOffset(100, 28).addBox(-7.0F, -0.5F, -0.5F, 8.0F, 1.0F, 1.0F, 0.0F, false);

		this.right = new ModelRenderer(this);
		this.right.setRotationPoint(0.0F, 0.0F, -1.0F);
		this.antler.addChild(this.right);


		this.cube_r8 = new ModelRenderer(this);
		this.cube_r8.setRotationPoint(0.0F, -1.5F, 8.0F);
		this.right.addChild(this.cube_r8);
		this.setRotationAngle(this.cube_r8, 0.5236F, 0.0F, 0.0F);
		this.cube_r8.setTextureOffset(60, 28).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);

		this.cube_r9 = new ModelRenderer(this);
		this.cube_r9.setRotationPoint(-4.0F, -1.8F, 6.3F);
		this.right.addChild(this.cube_r9);
		this.setRotationAngle(this.cube_r9, 0.0873F, -0.7418F, 0.0873F);
		this.cube_r9.setTextureOffset(118, 38).addBox(-3.0F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);

		this.cube_r10 = new ModelRenderer(this);
		this.cube_r10.setRotationPoint(-4.0F, -2.0F, 7.0F);
		this.right.addChild(this.cube_r10);
		this.setRotationAngle(this.cube_r10, -0.0873F, -0.0873F, 0.0F);
		this.cube_r10.setTextureOffset(60, 26).addBox(-3.0F, -1.0F, -1.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

		this.cube_r11 = new ModelRenderer(this);
		this.cube_r11.setRotationPoint(-4.0F, -1.7F, 7.0F);
		this.right.addChild(this.cube_r11);
		this.setRotationAngle(this.cube_r11, 0.0F, 0.6545F, -0.1745F);
		this.cube_r11.setTextureOffset(118, 36).addBox(-3.0F, -1.0F, 0.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);

		this.cube_r12 = new ModelRenderer(this);
		this.cube_r12.setRotationPoint(-2.3037F, -2.039F, 6.7347F);
		this.right.addChild(this.cube_r12);
		this.setRotationAngle(this.cube_r12, 0.1745F, -0.1309F, 0.3054F);
		this.cube_r12.setTextureOffset(118, 34).addBox(-2.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, 0.0F, false);

		this.cube_r13 = new ModelRenderer(this);
		this.cube_r13.setRotationPoint(5.0F, -3.25F, 4.1F);
		this.right.addChild(this.cube_r13);
		this.setRotationAngle(this.cube_r13, -0.0873F, 0.5236F, -0.5236F);
		this.cube_r13.setTextureOffset(96, 30).addBox(-1.0F, -5.0F, -1.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);

		this.cube_r14 = new ModelRenderer(this);
		this.cube_r14.setRotationPoint(5.0F, -3.5F, 3.5F);
		this.right.addChild(this.cube_r14);
		this.setRotationAngle(this.cube_r14, 0.0F, 0.5236F, -0.3491F);
		this.cube_r14.setTextureOffset(100, 30).addBox(-7.0F, -0.5F, -0.5F, 8.0F, 1.0F, 1.0F, 0.0F, false);

		this.body = new ModelRenderer(this);
		this.body.setRotationPoint(-4.0F, 12.0F, 0.0F);
		this.root.addChild(this.body);
		this.body.setTextureOffset(0, 0).addBox(-9.0F, -21.0F, -5.0F, 24.0F, 10.0F, 10.0F, 0.0F, false);

		this.legs = new ModelRenderer(this);
		this.legs.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body.addChild(this.legs);


		this.cube_r15 = new ModelRenderer(this);
		this.cube_r15.setRotationPoint(13.0F, -11.0F, -3.0F);
		this.legs.addChild(this.cube_r15);
		this.cube_r15.setTextureOffset(84, 15).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.0F, false);

		this.cube_r16 = new ModelRenderer(this);
		this.cube_r16.setRotationPoint(-6.0F, -11.0F, -3.0F);
		this.legs.addChild(this.cube_r16);
		this.cube_r16.setTextureOffset(68, 15).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.0F, false);

		this.cube_r17 = new ModelRenderer(this);
		this.cube_r17.setRotationPoint(13.0F, -11.0F, 3.0F);
		this.legs.addChild(this.cube_r17);
		this.cube_r17.setTextureOffset(84, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.0F, false);

		this.cube_r18 = new ModelRenderer(this);
		this.cube_r18.setRotationPoint(-6.0F, -11.0F, 3.0F);
		this.legs.addChild(this.cube_r18);
		this.cube_r18.setTextureOffset(68, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void render(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		this.root.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	@Override
	public void setRotationAngles(@Nonnull Reindeer entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.setRotationAngle(this.root, 0, ((float) Math.PI / 2), 0);
		this.setRotationAngle(this.head, MathHelper.clamp(netHeadYaw, -35, 35) * ((float) Math.PI / 180f), 0, (headPitch + (entity.getAttackTarget() != null ? 90 : 75)) * ((float) Math.PI / 180f));
		this.setRotationAngle(this.cube_r15, 0, 0, MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount);
		this.setRotationAngle(this.cube_r16, 0, 0, MathHelper.cos(limbSwing * 0.6662f + (float) Math.PI) * 1.4f * limbSwingAmount);
		this.setRotationAngle(this.cube_r17, 0, 0, MathHelper.cos(limbSwing * 0.6662f + (float) Math.PI) * 1.4f * limbSwingAmount);
		this.setRotationAngle(this.cube_r18, 0, 0, MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}