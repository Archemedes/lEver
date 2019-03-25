package co.lotc.lever.cmd;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import co.lotc.lever.BaseCommand;

public class Sneak extends BaseCommand {

	public void invoke(Player p) {
		validate(!p.isSprinting(), "You cannot be sprinting and sneak!");
		
    p.setSneaking(!p.isSneaking());
    if (p.isSneaking()) {
        p.sendMessage(ChatColor.AQUA + "You are now sneaking.");
    } else {
        p.sendMessage(ChatColor.AQUA + "You are no longer sneaking.");
    }
	}
	
}
