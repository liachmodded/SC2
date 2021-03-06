package vswe.stevescarts.models.realtimers;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import vswe.stevescarts.helpers.ResourceHelper;
import vswe.stevescarts.models.ModelCartbase;
import vswe.stevescarts.modules.ModuleBase;
import vswe.stevescarts.modules.realtimers.ModuleShooterAdvSide;

public class ModelSideGuns extends ModelCartbase {
	private static ResourceLocation texture;
	ModelRenderer[][] models;

	@Override
	public ResourceLocation getResource(final ModuleBase module) {
		return ModelSideGuns.texture;
	}

	@Override
	protected int getTextureWidth() {
		return 64;
	}

	@Override
	protected int getTextureHeight() {
		return 32;
	}

	public ModelSideGuns() {
		(this.models = new ModelRenderer[2][])[0] = this.createSide(false);
		this.models[1] = this.createSide(true);
	}

	private ModelRenderer[] createSide(final boolean opposite) {
		final ModelRenderer anchor = new ModelRenderer(this, 0, 0);
		this.AddRenderer(anchor);
		final int mult = opposite ? 1 : -1;
		final ModelRenderer handle = new ModelRenderer(this, 0, 8);
		anchor.addChild(handle);
		this.fixSize(handle);
		handle.addBox(-2.0f, -2.0f, -2.5f, 4, 4, 5, 0.0f);
		handle.setRotationPoint(6.0f, 0.0f, 0.0f);
		final ModelRenderer base = new ModelRenderer(this, 0, 0);
		handle.addChild(base);
		this.fixSize(base);
		base.addBox(-2.5f, -2.5f, -1.5f, 12, 5, 3, 0.0f);
		base.setRotationPoint(0.0f, 0.0f, 0.0f);
		final ModelRenderer gun = new ModelRenderer(this, 0, 0);
		base.addChild(gun);
		this.fixSize(gun);
		gun.addBox(-2.5f, -2.5f, -1.5f, 12, 5, 3, 0.0f);
		gun.setRotationPoint(7.005f, 0.005f, 0.005f);
		final ModelRenderer back = new ModelRenderer(this, 18, 8);
		gun.addChild(back);
		this.fixSize(back);
		back.addBox(-6.5f, -2.0f, -0.5f, 7, 4, 1, 0.0f);
		back.setRotationPoint(0.0f, 0.0f, -0.5f * mult);
		final ModelRenderer backAttacher = new ModelRenderer(this, 18, 8);
		back.addChild(backAttacher);
		this.fixSize(backAttacher);
		backAttacher.addBox(0.0f, -2.0f, -0.5f + 0.5f * mult, 7, 4, 1, 0.0f);
		backAttacher.setRotationPoint(-6.5f, 0.0f, 0.5f * mult + 0.005f);
		final ModelRenderer stabalizer = new ModelRenderer(this, 0, 8);
		back.addChild(stabalizer);
		this.fixSize(stabalizer);
		stabalizer.addBox(-0.5f, -1.5f, -0.5f, 1, 3, 1, 0.0f);
		stabalizer.setRotationPoint(-5.75f, 0.0f, 0.0f);
		final ModelRenderer[] missileStands = new ModelRenderer[6];
		for (int i = 0; i < missileStands.length; ++i) {
			missileStands[i] = new ModelRenderer(this, 0, 17);
			float posX;
			if (i < 3) {
				back.addChild(missileStands[i]);
				posX = -5.0f;
			} else {
				backAttacher.addChild(missileStands[i]);
				posX = 0.0f;
			}
			this.fixSize(missileStands[i]);
			missileStands[i].addBox(1.0f, -1.5f, -0.5f, 2, 3, 1, 0.0f);
			missileStands[i].setRotationPoint(posX, 0.0f, 0.0f);
		}
		final ModelRenderer missleArmBase = new ModelRenderer(this, 7, 17);
		base.addChild(missleArmBase);
		this.fixSize(missleArmBase);
		missleArmBase.addBox(-2.0f, -2.0f, -0.5f, 4, 4, 1, 0.0f);
		missleArmBase.setRotationPoint(0.0f, 0.0f, 0.75f * mult);
		final ModelRenderer missleArm = new ModelRenderer(this, 17, 17);
		missleArmBase.addChild(missleArm);
		this.fixSize(missleArm);
		missleArm.addBox(-0.5f, -2.0f, -0.5f, 11, 4, 1, 0.0f);
		missleArm.setRotationPoint(0.0f, 0.0f, 0.0f);
		final ModelRenderer missleArmBaseFake = new ModelRenderer(this);
		base.addChild(missleArmBaseFake);
		this.fixSize(missleArmBaseFake);
		missleArmBaseFake.setRotationPoint(0.0f, 0.0f, 0.75f * mult);
		final ModelRenderer missleArmFake = new ModelRenderer(this);
		missleArmBaseFake.addChild(missleArmFake);
		this.fixSize(missleArmFake);
		missleArmFake.setRotationPoint(0.0f, 0.0f, 0.0f);
		final ModelRenderer[] missiles = new ModelRenderer[2];
		for (int j = 0; j < 2; ++j) {
			final ModelRenderer missile = new ModelRenderer(this, 0, 22);
			missleArmFake.addChild(missiles[j] = missile);
			this.fixSize(missile);
			missile.addBox(-2.0f, -1.0f, -1.0f, 4, 2, 2, -0.2f);
			missile.setRotationPoint((j == 0) ? 9.5f : 4.0f, 0.0f, mult * -1.0f);
			missile.rotateAngleZ = 1.5707964f;
			for (int k = -1; k <= 1; ++k) {
				for (int l = -1; l <= 1; ++l) {
					if (k == 0 || l == 0) {
						final ModelRenderer missilePart = new ModelRenderer(this, 12, 22);
						missile.addChild(missilePart);
						this.fixSize(missilePart);
						missilePart.addBox(-0.5f, -0.5f, -0.5f, 1, 1, 1, 0.0f);
						missilePart.setRotationPoint(-1.0f, k * 0.6f, l * 0.6f);
					}
				}
			}
			final ModelRenderer missilePart2 = new ModelRenderer(this, 12, 24);
			missile.addChild(missilePart2);
			this.fixSize(missilePart2);
			missilePart2.addBox(-0.5f, -0.5f, -0.5f, 1, 1, 1, 0.0f);
			missilePart2.setRotationPoint(1.75f, 0.0f, 0.0f);
		}
		return new ModelRenderer[] { handle, base, gun, back, backAttacher, stabalizer, missileStands[0], missileStands[1], missileStands[2], missileStands[3], missileStands[4], missileStands[5],
				missleArmBase, missleArm, missleArmBaseFake, missleArmFake, missiles[0], missiles[1] };
	}

	@Override
	public void applyEffects(final ModuleBase module, final float yaw, final float pitch, final float roll) {
		if (module == null) {
			for (int i = 0; i < 2; ++i) {
				final ModelRenderer[] models = this.models[i];
			}
		} else {
			final ModuleShooterAdvSide shooter = (ModuleShooterAdvSide) module;
			for (int j = 0; j < 2; ++j) {
				final ModelRenderer[] models2 = this.models[j];
				final int mult = (j != 0) ? 1 : -1;
				models2[0].rotationPointZ = shooter.getHandlePos(mult);
				models2[1].rotationPointZ = shooter.getBasePos(mult);
				models2[0].rotateAngleZ = shooter.getHandleRot(mult);
				models2[2].rotateAngleZ = shooter.getGunRot(mult);
				models2[3].rotationPointX = shooter.getBackPos(mult);
				models2[3].rotateAngleY = shooter.getBackRot(mult);
				models2[4].rotateAngleY = shooter.getAttacherRot(mult);
				models2[5].rotationPointZ = shooter.getStabalizerOut(mult);
				models2[5].rotationPointY = shooter.getStabalizerDown(mult);
				for (int k = 0; k < 2; ++k) {
					for (int l = 0; l < 3; ++l) {
						models2[6 + k * 3 + l].rotationPointZ = shooter.getStandOut(mult, k, l - 1);
						models2[6 + k * 3 + l].rotationPointY = shooter.getStandUp(mult, k, l - 1);
					}
				}
				models2[12].rotationPointX = shooter.getArmBasePos(mult, false);
				models2[13].rotateAngleY = shooter.getArmRot(mult, false);
				models2[13].rotationPointX = shooter.getArmPos(mult, false);
				models2[14].rotationPointX = shooter.getArmBasePos(mult, true);
				models2[15].rotateAngleY = shooter.getArmRot(mult, true);
				models2[15].rotationPointX = shooter.getArmPos(mult, true);
				for (int k = 0; k < 2; ++k) {
					models2[16 + k].rotationPointY = shooter.getMissilePos(mult);
					models2[16 + k].rotateAngleY = shooter.getMissileRot(mult);
				}
			}
		}
	}

	static {
		ModelSideGuns.texture = ResourceHelper.getResource("/models/sidegunsModel.png");
	}
}
