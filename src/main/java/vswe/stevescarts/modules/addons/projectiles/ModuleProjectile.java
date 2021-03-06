package vswe.stevescarts.modules.addons.projectiles;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import vswe.stevescarts.entitys.EntityMinecartModular;
import vswe.stevescarts.modules.addons.ModuleAddon;

public abstract class ModuleProjectile extends ModuleAddon {
	public ModuleProjectile(final EntityMinecartModular cart) {
		super(cart);
	}

	public abstract boolean isValidProjectile(final ItemStack p0);

	public abstract Entity createProjectile(final Entity p0, final ItemStack p1);
}
