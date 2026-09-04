package moriyashiine.lostrelics.common.world.item;

import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.callback.TrinketCallback;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class TurquoiseEyeItem extends ToggleableRelicItem implements TrinketCallback {
	public TurquoiseEyeItem(Properties properties) {
		super(properties, "tooltip.lost_relics.treasure_sense");
	}

	@Override
	public void onEquip(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
		entity.removeEffect(MobEffects.INVISIBILITY);
	}
}
