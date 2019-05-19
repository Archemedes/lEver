 package co.lotc.lever.cmd;

import co.lotc.core.bukkit.util.LocationUtil;
import co.lotc.core.command.annotate.Arg;
import co.lotc.core.command.annotate.Default;
import co.lotc.lever.BaseCommand;
import com.google.common.primitives.Ints;
import net.lordofthecraft.arche.interfaces.Persona;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;

import java.util.concurrent.ThreadLocalRandom;

import static org.bukkit.ChatColor.DARK_AQUA;
import static org.bukkit.ChatColor.GRAY;

public class Roll extends BaseCommand {
	private static final int BROADCAST_RADIUS = 24;
	
	public void invoke(Persona ps, @Default("20") @Arg("Dice")String rollString) {
		validate(rollString.matches("[0-9d\\+-]+"), "Could not understand the dice you're trying to roll");
		String input = rollString;
		rollString = rollString.replace("-", "+-");
		String[] dice = rollString.split("\\+");
		
		int[] roll;
		
		if(dice.length == 1 && NumberUtils.isDigits(dice[0])) {
			roll = roll("1d"+dice[0]);
		} else {
			roll = new int[2];
			for(String die : dice) {
				int[] otherRoll = roll(die);
				roll[0] += otherRoll[0];
				roll[1] += otherRoll[1];
			}
		}
		
		String personaName = ps.getName();
		String message = GRAY + personaName + DARK_AQUA + " has rolled a " + GRAY + roll[1]
				+ DARK_AQUA + " out of " + GRAY + input;
		Bukkit.getOnlinePlayers().stream()
			.filter(o->LocationUtil.isClose(ps.getPlayer(), o, BROADCAST_RADIUS))
			.forEach(o->o.sendMessage(message));
	}
	
	
	private int[] roll(String input) {
		int[] result = new int[2]; //Format as: [max, result]
		int multiplier = 1;
		
		if(input.startsWith("-")) {
			multiplier = -1;
			input = input.substring(1);
		}
		
		result[0] = result[1] = multiplier;
		
		if(input.matches("[0-9]+")) {
			Integer faces = Ints.tryParse(input);
			overflow(faces);
			result[0] = result[1] = multiplier*faces;
		} else if(input.matches("[0-9]+d[0-9]+")) {
			String[] hmm = input.split("d");
			Integer dice = Ints.tryParse(hmm[0]);
			Integer faces = Ints.tryParse(hmm[1]);
			overflow(dice);
			overflow(faces);
			result[0] *= dice * faces;
			result[1] *= ThreadLocalRandom.current().ints(dice, 1, faces+1).sum();
		} else if(input.matches("d\\d+")) {
			int faces = Ints.tryParse(input.substring(1));
			overflow(faces);
			result[0] *= faces;
			result[1] *= ThreadLocalRandom.current().nextInt(1, faces+1);
		} else {
			error("Could not parse the following dice roll:" + input);
		}
		
		return result;
	}
	
	private void overflow(Integer i) {
		validate(i!=null, "Number too high!");
		validate(i != 0, "Number can not be 0");
	}
	
}
