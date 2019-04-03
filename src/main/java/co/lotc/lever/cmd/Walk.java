package co.lotc.lever.cmd;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import co.lotc.lever.BaseCommand;

public class Walk extends BaseCommand {
	private static final List<UUID> WALKERS = new ArrayList<>();

	public void invoke(Player p) {
		walk(p);
	}
	
	public static void walk(Player player) {
		if (WALKERS.contains(player.getUniqueId())) {
			disableWalk(player);
		} else {
			player.setWalkSpeed(0.1F);
			WALKERS.add(player.getUniqueId());
			player.sendMessage(ChatColor.AQUA + "You are now walking!");
		}
	}

	public static boolean isWalking(Player p) {
		return WALKERS.contains(p.getUniqueId());
	}

	public static void disableWalk(Player player) {
		player.setWalkSpeed(0.2F);
		WALKERS.remove(player.getUniqueId());
		player.sendMessage(ChatColor.AQUA + "You have stopped walking");
	}

	
}
