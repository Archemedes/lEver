package co.lotc.lever.cmd;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.destroystokyo.paper.profile.PlayerProfile;

import co.lotc.lever.BaseCommand;

public class Impersonate extends BaseCommand {

	public static Map<UUID, PlayerProfile> REDIRECTS = new HashMap<>();
	
	public void invoke(Player p, Player player) {
		REDIRECTS.put(p.getUniqueId(), Bukkit.createProfile(player.getUniqueId(), player.getName()));
		player.kickPlayer("You are being rescued. Please remain calm!");
		p.kickPlayer("Now when you login you will log in as your target.");
	}
}
