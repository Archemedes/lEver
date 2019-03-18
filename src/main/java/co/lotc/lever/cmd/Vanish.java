package co.lotc.lever.cmd;

import static org.bukkit.ChatColor.*;

import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import co.lotc.core.command.annotate.Cmd;
import co.lotc.lever.BaseCommand;

public class Vanish extends BaseCommand {

	public void invoke(Player p) {
		if(p.getGameMode() == GameMode.SPECTATOR) {
			p.setGameMode(GameMode.SURVIVAL);
			msg(LIGHT_PURPLE + "You are no longer invisible!");
			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 4));
		} else {
			p.setGameMode(GameMode.SPECTATOR);
			msg(GREEN + "You are now invisible!");
		}
	}
	
	@Cmd("List people in spectator mode")
	public void list() {
		String names = Bukkit.getOnlinePlayers().stream()
			.filter(p->p.getGameMode() == GameMode.SPECTATOR)
			.map(Player::getName)
			.collect(Collectors.joining(", "));
		if(StringUtils.isEmpty(names)) names = "none...";
		
		msg(AQUA + "Players in spectator mode: " + names);
	}
}
