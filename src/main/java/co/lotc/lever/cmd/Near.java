package co.lotc.lever.cmd;

import static net.md_5.bungee.api.ChatColor.*;

import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import co.lotc.core.bukkit.util.ChatBuilder;
import co.lotc.core.bukkit.util.LocationUtil;
import co.lotc.core.command.annotate.Default;
import co.lotc.lever.BaseCommand;
import lombok.var;

public class Near extends BaseCommand {
	
	public void invoke(Player p, @Default("64") double radius) {
		var nears = Bukkit.getOnlinePlayers().stream()
		.filter(x->x!=p)
		.filter(x->LocationUtil.isClose(x, p, radius))
		.map(Player::getName)
		.collect(Collectors.toList());
		
		new ChatBuilder("Players nearby: ").color(GOLD).append( nears.size()).color(WHITE).send(p);
		String formatted = GRAY + StringUtils.join(nears, WHITE+", "+GRAY);
		p.sendMessage(formatted);
	}
}
