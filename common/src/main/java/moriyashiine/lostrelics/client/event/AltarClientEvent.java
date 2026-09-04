package moriyashiine.lostrelics.client.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;

public class AltarClientEvent implements ClientTickEvents.EndLevelTick {
	public static void init() {
		ClientTickEvents.END_LEVEL_TICK.register(new AltarClientEvent());
	}

	public static int clientTime = 0;

	@Override
	public void onEndTick(ClientLevel level) {
		if (!Minecraft.getInstance().isPaused()) {
			clientTime++;
		}
	}
}
