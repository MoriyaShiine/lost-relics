package moriyashiine.lostrelics.common.world.item;

import moriyashiine.lostrelics.common.world.entity.projectile.arrow.TaintedBloodCrystal;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import org.jspecify.annotations.Nullable;

public class TaintedBloodCrystalItem extends ArrowItem {
	public TaintedBloodCrystalItem(Properties properties) {
		super(properties);
		DispenserBlock.registerProjectileBehavior(this);
	}

	@Override
	public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity owner, @Nullable ItemStack firedFromWeapon) {
		return new TaintedBloodCrystal(level, owner, stack.copyWithCount(1), firedFromWeapon);
	}

	@Override
	public Projectile asProjectile(Level level, Position position, ItemStack stack, Direction direction) {
		TaintedBloodCrystal crystal = new TaintedBloodCrystal(level, position.x(), position.y(), position.z(), stack.copyWithCount(1), null);
		crystal.pickup = AbstractArrow.Pickup.ALLOWED;
		return crystal;
	}
}
