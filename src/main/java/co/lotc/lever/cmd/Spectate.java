package co.lotc.lever.cmd;

import static org.bukkit.ChatColor.*;

import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import co.lotc.core.bukkit.util.Run;
import co.lotc.core.command.annotate.Cmd;
import co.lotc.core.command.annotate.Flag;
import co.lotc.lever.BaseCommand;
import co.lotc.lever.Lever;

public class Spectate extends BaseCommand {

	@Flag(name = "t", description="Specify a player to spectate", type=Player.class)
	public void invoke(Player p) {
		if(hasFlag("t")) {
			p.setGameMode(GameMode.SPECTATOR);
			Player you = getFlag("t");
			Run.as(Lever.get()).delayed(3, ()->p.setSpectatorTarget(you));
			msg(GREEN + "You are now spectating " + WHITE + you.getName());
			return;
		}
		
		if(p.getGameMode() == GameMode.SPECTATOR) {
			p.setGameMode(GameMode.SURVIVAL);
			msg(LIGHT_PURPLE + "You are no longer spectating!");
			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 4));
		} else {
			p.setGameMode(GameMode.SPECTATOR);
			msg(GREEN + "You are now spectating!");
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
