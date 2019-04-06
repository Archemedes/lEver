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

import co.lotc.core.command.annotate.Cmd;
import co.lotc.lever.BaseCommand;
import co.lotc.lever.Lever;

public class Vanish extends BaseCommand {
	public static final String PEX_SEE_VANISHED = "lever.vanish.cansee";
	public static final String VANISH_PERSIST_TAG = "lever_vanished";
	public static final Set<UUID> VANISHED = new HashSet<>();

	public static boolean isVanished(Player p) {
		return VANISHED.contains(p.getUniqueId());
	}
	
	public static void persist(Player p) {
		if(!isVanished(p)) return;
		p.addScoreboardTag(VANISH_PERSIST_TAG);
	}
	
	public void invoke(Player p) {
		UUID u = p.getUniqueId();
		if(VANISHED.contains(u)) {
			deactivate(p);
			msg(LIGHT_PURPLE + "You are no longer invisible");
		} else {
			Bukkit.getOnlinePlayers().forEach(x->maybeHide(p, x));
			VANISHED.add(u);
			p.setInvulnerable(true);
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
		Bukkit.getOnlinePlayers().stream().filter(x->x!=p).forEach(x->x.showPlayer(Lever.get(), p));
		VANISHED.remove(p.getUniqueId());
		p.setInvulnerable(false);
		p.setAllowFlight(false);
	}
	
	public static void maybeHide(Player who, Player from) {
		if(who == from) return;
		if(!from.hasPermission(PEX_SEE_VANISHED)) {
			from.hidePlayer(Lever.get(), who);
		}
	}
}
