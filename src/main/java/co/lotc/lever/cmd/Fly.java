package co.lotc.lever.cmd;

import org.bukkit.entity.Player;

import co.lotc.lever.BaseCommand;

public class Fly extends BaseCommand {

	public void invoke(Player p) {
		p.setAllowFlight(!p.getAllowFlight());
	}
}
