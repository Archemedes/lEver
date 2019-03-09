package co.lotc.lever.cmd;

import static org.bukkit.ChatColor.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import co.lotc.core.bukkit.util.ChatBuilder;
import co.lotc.core.bukkit.util.LocationUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.var;
import net.lordofthecraft.arche.command.CommandTemplate;
import net.lordofthecraft.arche.command.annotate.Arg;
import net.lordofthecraft.arche.command.annotate.Default;
import net.lordofthecraft.arche.command.annotate.Range;

public class MobProximity extends CommandTemplate {

	@Data
	@AllArgsConstructor
	private class MobPack {
		Location loc;
		int num;
	}

	public void invoke(Player p,
			@Arg("Entity Type") EntityType type,
			@Arg("range") @Default("16") @Range(min=1,max=256)  double range,
			@Arg("alert") @Default("2") @Range(min=1) int alert) {
		World w = p.getWorld();
		var mobs = new ArrayList<MobPack>();

		for(LivingEntity e : w.getLivingEntities()) {
			if (type != e.getType())
				continue;
			MobPack pack = new MobPack(e.getLocation(), 1);
			for (LivingEntity e2 : w.getLivingEntities())
				if (type == e2.getType() && e != e2 && withinDistance(pack.loc, e2.getLocation(), range))
					pack.num++;
			if (pack.num >= alert)
				mobs.add(pack);
		}
		
		mobs.sort(Comparator.comparingInt(mob -> mob.num));

		List<MobPack> redundant = new ArrayList<>();
		for (MobPack mp : mobs)
			for (MobPack mp2 : mobs)
				if (mp != mp2 && !redundant.contains(mp) && !redundant.contains(mp2))
					if (withinDistance(mp.loc, mp2.loc, range))
						redundant.add(mp2);
		mobs.removeAll(redundant);

		String tp = type.toString();
		if (mobs.size() > 0) {
			for (MobPack mp : mobs) {
				int x = mp.loc.getBlockX();
				int y = mp.loc.getBlockY();
				int z = mp.loc.getBlockZ();
				new ChatBuilder("  ").command(String.format("/tp %d %d %d",x,y,z))
					.append(tp).color(DARK_AQUA)
					.append(": ").color(GRAY)
					.append(mp.num).color(RED)
					.append(" found at ").color(GRAY)
					.append(x).color(RED)
					.append(',').append(y)
					.append(',').append(z)
					.send(p);
			}
			msg(GRAY + "Found " + DARK_AQUA + mobs.size() + GRAY + " groups matching the specified arguments.");
		} else {
			msg(GRAY + "No mobs of type " + DARK_AQUA + tp + GRAY + " were found.");
		}
	}

	private static boolean withinDistance(Location l, Location l2, double dist) {
		return LocationUtil.isClose(l, l2, dist);
	}
}
