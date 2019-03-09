package co.lotc.lever.cmd;

import static org.bukkit.ChatColor.*;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import net.lordofthecraft.arche.command.CommandTemplate;

public class HorseStats extends CommandTemplate {
	
	
	public void invoke(Player p) {
		Entity v = p.getVehicle();
		
		validate(v instanceof AbstractHorse, "You need to be riding a horse");
		AbstractHorse horse = (AbstractHorse) v;
		
		msg(YELLOW + "The horse you are riding has the following stats:");
		msg(GRAY + "  Speed: " + GREEN + String.format("%.2f m/s", getHorseSpeed(horse)));
		msg(GRAY + "  Jump Strength: " + GREEN + String.format("%.2f blocks", getHorseJump(horse)));
	}
	
	private double getHorseSpeed(AbstractHorse entity) {
		double rawHorseSpeed = entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
		return rawHorseSpeed * 43.1;
	}

	private double getHorseJump(AbstractHorse entity) {
		double rawHorseJumpHeight = entity.getAttribute(Attribute.HORSE_JUMP_STRENGTH).getBaseValue();

		return -0.1817584952 * Math.pow(rawHorseJumpHeight, 3) +
				3.689713992 * Math.pow(rawHorseJumpHeight, 2) +
				2.128599134 * rawHorseJumpHeight - 0.343930367;
	}
}
