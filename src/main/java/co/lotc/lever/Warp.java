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
	
	public Warp(String name, Location l) {
		this.name = name;
		loc = new WeakBlock(l);
	}
	
	public void teleport(Player p) {
		p.teleport(loc.toLocation());
	}

}
