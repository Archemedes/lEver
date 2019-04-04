package co.lotc.lever;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import co.lotc.core.bukkit.util.WeakBlock;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor
public class Warp {
	@Getter private final String name;
	private final WeakBlock loc;
	private final float yaw;
	
	public Warp(String name, Location l) {
		this.name = name;
		loc = new WeakBlock(l);
		yaw = l.getYaw();
	}
	
	public void teleport(Player p) {
		Location location = loc.toLocation();
		location.setYaw(yaw);
		location.add(0.5, 0.3, 0.5); // Center the player on the block they're teleporting to
		p.teleportAsync(location);
	}

}
