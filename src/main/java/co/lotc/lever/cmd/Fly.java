package co.lotc.lever.cmd;

import static net.md_5.bungee.api.ChatColor.*;

import org.bukkit.entity.Player;

import co.lotc.lever.BaseCommand;

public class Fly extends BaseCommand {

	public void invoke(Player p) {
		
		p.setAllowFlight(!p.getAllowFlight());
		
		if(p.getAllowFlight()) msg(GREEN + "You are now flying");
		else msg(LIGHT_PURPLE + "You are no longer flying");
	}
}
