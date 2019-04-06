package co.lotc.lever.listener;

import co.lotc.lever.Lever;
import co.lotc.lever.Warp;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import static net.md_5.bungee.api.ChatColor.BLUE;

public class WarpSignListener implements Listener {

	private Lever plugin;

	public WarpSignListener(Lever plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	void onSign(SignChangeEvent event) {
		if (event.getPlayer().hasPermission("lever.warpsign")) {
			if (event.getLine(0).equals("[Warp]") && plugin.getWarpManager().hasWarp(event.getLine(1))) {
				event.setLine(0, BLUE + "[Warp]");
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	void signRightClick(PlayerInteractEvent event) {
		if (event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK
				&& event.getHand() == EquipmentSlot.HAND
				&& event.getClickedBlock().getState() instanceof Sign) {
			Sign sign = (Sign) event.getClickedBlock().getState();
			String[] lines = sign.getLines();
			Player player = event.getPlayer();

			if (lines[0].equals(ChatColor.BLUE + "[Warp]") && plugin.getWarpManager().hasWarp(lines[1])) {
				Warp warp = plugin.getWarpManager().getWarp(lines[1]);
				warp.teleport(player);
			}
		}
	}
}
