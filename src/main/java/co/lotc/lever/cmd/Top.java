package co.lotc.lever.cmd;

import co.lotc.lever.BaseCommand;
import org.bukkit.entity.Player;

import static net.md_5.bungee.api.ChatColor.AQUA;

public class Top extends BaseCommand {

	public void invoke(Player player) {
		player.teleportAsync(player.getWorld().getHighestBlockAt(player.getLocation()).getLocation());
		player.sendMessage(AQUA + "Teleported to the top block!");
	}
}
