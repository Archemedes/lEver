package co.lotc.lever.listener;

import co.lotc.core.bukkit.util.Run;
import co.lotc.lever.Lever;
import co.lotc.lever.cmd.Vanish;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.md_5.bungee.api.ChatColor.GREEN;

@RequiredArgsConstructor
public class SneakToggleListener implements Listener {
	private final Lever plugin;
	private final List<String> sneakAttempts = Lists.newArrayList();
	private final Map<Player, GameMode> previousGamemode = new HashMap<>();
	
	@EventHandler(ignoreCancelled = true, priority= EventPriority.MONITOR)
	public void onSneak(PlayerToggleSneakEvent e){
		final Player p = e.getPlayer();
		if(e.isSneaking()){
			if(Vanish.isVanished(p)){
				if(!sneakAttempts.contains(p.getName())){ //Shift pressed down once.
					sneakAttempts.add(p.getName());
					Run.as(plugin).delayed(8, ()->sneakAttempts.remove(p.getName()));
				} else { //Shift pressed down twice in short time
					p.sendMessage(GREEN + "Toggled gamemode");
					if(p.getGameMode() != GameMode.SPECTATOR) {
						previousGamemode.put(p, p.getGameMode());
						p.setGameMode(GameMode.SPECTATOR);
					} else if (previousGamemode.containsKey(p)) {
						p.setGameMode(previousGamemode.get(p));
						p.setAllowFlight(true);
						p.setFlying(true);
					} else {
						p.setGameMode(GameMode.SURVIVAL);
						p.setAllowFlight(true);
						p.setFlying(true);
					}
				}
			}
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		previousGamemode.remove(event.getPlayer());
	}
}
