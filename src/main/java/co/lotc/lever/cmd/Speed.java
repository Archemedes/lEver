package co.lotc.lever.cmd;

import static net.md_5.bungee.api.ChatColor.*;

import org.bukkit.entity.Player;

import co.lotc.core.command.annotate.Arg;
import co.lotc.core.command.annotate.Flag;
import co.lotc.core.command.annotate.Range;
import co.lotc.lever.BaseCommand;

public class Speed extends BaseCommand {
	private static final float DEFAULT_WALK_SPEED = 0.2f;
	private static final float DEFAULT_FLY_SPEED = 0.1f;
	
	@Flag(name="w",description="Set walking speed")
	@Flag(name="f",description="Set flying speed")
	public void invoke(Player p, @Arg("speed") @Range(min=0,max=2) float speed) {
		boolean fly = false;
		boolean walk = false;

		if(hasFlag("w")) walk = true;
		if(hasFlag("f")) fly = true;
		
		if(!walk && !fly) {
			fly = p.isFlying();
			walk = !fly;
		}
		
		if(walk) {
			float walkSpeed = DEFAULT_WALK_SPEED * speed;
			p.setWalkSpeed(walkSpeed);
			msg(AQUA + "Set walking speed to: " + YELLOW + speed);
		}
		
		if(fly) {
			float flySpeed = DEFAULT_FLY_SPEED * speed;
			p.setFlySpeed(flySpeed);
			msg(AQUA + "Set flying speed to: " + YELLOW + speed);
		}
	}

}
