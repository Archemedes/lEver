package co.lotc.lever.cmd;

import co.lotc.core.command.annotate.Default;
import co.lotc.lever.BaseCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static net.md_5.bungee.api.ChatColor.*;

public class Fly extends BaseCommand {
	public static final String FLY_PERSIST_TAG = "lever_flying";
	
	public void invoke(CommandSender s, @Default("@p") Player target) {
		target.setAllowFlight(!target.getAllowFlight());
		
		if(target.getAllowFlight()) target.sendMessage(GREEN + "You are now flying");
		else {
			target.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 4));
			target.sendMessage(LIGHT_PURPLE + "You are no longer flying");
		}

		if (s != target) {
			s.sendMessage(AQUA + "Fly toggled " + (target.getAllowFlight() ? GREEN + "ON" : RED + "OFF") + AQUA + " for " + WHITE + target.getName());
		}
	}
}
