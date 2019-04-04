package co.lotc.lever;

import co.lotc.core.bukkit.command.Commands;
import lombok.var;
import net.lordofthecraft.arche.ArcheCore;
import org.bukkit.Location;

import java.util.*;

public class WarpManager {
	public static final String TABLE = "lever_warps";
	private final Map<String, Warp> warps = new TreeMap<>();

	public void load(String name, Warp warp) {
		warps.put(name, warp);
	}
	
	public void add(String name, Location location) {
		warps.put(name.toLowerCase(), new Warp(name, location));
		ArcheCore.getConsumerControls().replace(TABLE)
			.set("name", name.toLowerCase())
			.set("world", location.getWorld().getName())
			.set("x", location.getX())
			.set("y", location.getY())
			.set("z", location.getZ())
			.set("yaw", location.getYaw())
			.queue();
	}
	
	public void init() {
		Commands.defineArgumentType(Warp.class)
		.defaultName("warp name")
		.defaultError("Not a valid warp!")
		.completer(warps::keySet)
		.mapper(warps::get)
		.register();
		
	}
	
	public boolean remove(String name) {
		if(warps.remove(name) != null) {
			ArcheCore.getConsumerControls().delete(TABLE)
			.where("name", name).queue();
			return true;
		} else {
			return false;
		}
	}
	
	public Collection<String> getWarps(int offset, int amount){
		List<String> result = new ArrayList<>();
		int parsed = 0;
		
		var iter = warps.keySet().iterator();
		
		while(iter.hasNext() && parsed < offset + amount) {
			String aWarp = iter.next();
			if(parsed >= offset) result.add(aWarp);
			parsed++;
		}
		
		return result;
	}
	
	
}
