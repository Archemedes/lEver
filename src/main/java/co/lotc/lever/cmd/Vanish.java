package co.lotc.lever.cmd;

import static org.bukkit.ChatColor.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import co.lotc.core.command.annotate.Cmd;
import co.lotc.lever.BaseCommand;

public class Vanish extends BaseCommand {
	public static final Set<UUID> VANISHED = new HashSet<>();

	public void invoke(Player p) {
		UUID u = p.getUniqueId();
		if(VANISHED.contains(u)) {
			deactivate(p);
			msg(LIGHT_PURPLE + "You are no longer invisible");
		} else {
			VANISHED.add(u);
			applyInvis(p);
			p.setSilent(true);
			p.setAllowFlight(true);
			msg(GREEN + "You are now invisible!");
		}
	}
	
	@Cmd("List people in vanish mode")
	public void list() {
		String names = VANISHED.stream()
			.map(Bukkit::getPlayer)
			.filter(Objects::nonNull)
			.map(Player::getName)
			.collect(Collectors.joining(", "));
			
		if(StringUtils.isEmpty(names)) names = "none...";
		msg(AQUA + "Players in vanish mode: " + names);
	}
	
	public static void deactivate(Player p) {
		p.removePotionEffect(PotionEffectType.INVISIBILITY);
		p.setSilent(false);
		VANISHED.remove(p.getUniqueId());
	}
	
	public static void applyInvis(Player p) {
		p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 200_000, 2, true, false), true);
	}
}
