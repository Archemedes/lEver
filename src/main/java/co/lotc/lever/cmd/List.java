package co.lotc.lever.cmd;

import co.lotc.core.bukkit.util.ChatBuilder;
import co.lotc.core.command.annotate.Flag;
import co.lotc.lever.BaseCommand;
import lombok.var;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

import static net.md_5.bungee.api.ChatColor.*;

public class List extends BaseCommand{

	
	@Flag(name="names", permission="lever.list.names", description="also print the names of ")
	public void invoke(CommandSender sender) {
		var on = Bukkit.getOnlinePlayers().stream()
				.map(Player::getName)
				.collect(Collectors.toList());
			
		new ChatBuilder("There are currently ").color(DARK_AQUA)
		.append(on.size()).color(GOLD)
		.append(" players online.").color(DARK_AQUA)
		.send(sender);
		
		if(sender.hasPermission("lever.list.names")) {
			if(hasFlag("names")) {
				on.sort(null);
				sender.sendMessage(GRAY + StringUtils.join(on, WHITE+", "+GRAY));
			}else {
				new ChatBuilder("Use ").color(LIGHT_PURPLE)
				.append("/list -names").color(GRAY).italic().command("/list -names")
				.append(" to see the names of online players.").color(LIGHT_PURPLE)
				.send(sender);
			}
		}
	}

}
