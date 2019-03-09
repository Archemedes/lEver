package co.lotc.lever;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;

import net.lordofthecraft.omniscience.api.OmniApi;
import net.lordofthecraft.omniscience.api.data.DataKeys;
import net.lordofthecraft.omniscience.api.data.DataWrapper;
import net.lordofthecraft.omniscience.api.entry.OEntry;

public class OmniUtil {
	
	static void init() {
		OmniApi.registerEvent("trash", "trashed");
	}
	
	static void logItem(HumanEntity player, ItemStack is) {
		DataWrapper wrapper = DataWrapper.createNew();
		wrapper.set(DataKeys.ITEMSTACK, is);
		wrapper.set(DataKeys.TARGET, is.getType());
		wrapper.set(DataKeys.QUANTITY, is.getAmount());
		wrapper.set(DataKeys.DISPLAY_METHOD, "item");
		OEntry.create().player(player).customWithLocation("trash", wrapper, player.getLocation()).save();
	}

}
