package co.lotc.lever.cmd;

import co.lotc.core.bukkit.util.ChatBuilder;
import co.lotc.core.bukkit.util.LocationUtil;
import co.lotc.core.command.annotate.Flag;
import co.lotc.lever.BaseCommand;
import lombok.var;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

import static net.md_5.bungee.api.ChatColor.*;

public class Near extends BaseCommand {
	
	@Flag(name="r",description="radius to search in",type=double.class)
	public void invoke(Player p) {
		final double radius = hasFlag("r")? getFlag("r") : 64.0;
		
		var nears = Bukkit.getOnlinePlayers().stream()
		.filter(x->x!=p)
		.filter(x->LocationUtil.isClose(x, p, radius))
		.map(Player::getName)
		.collect(Collectors.toList());
		
		new ChatBuilder("Players nearby: (" + radius + ")").color(GOLD).append( nears.size()).color(WHITE).send(p);
		String formatted = GRAY + StringUtils.join(nears, WHITE+", "+GRAY);
		p.sendMessage(formatted);
	}
}
