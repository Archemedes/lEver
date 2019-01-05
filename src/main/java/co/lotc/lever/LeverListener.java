package co.lotc.lever;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import co.lotc.lever.Lever.StaticInventory;
import co.lotc.lever.cmd.Back;
import co.lotc.lever.cmd.InvSearch;
import lombok.var;
import net.lordofthecraft.arche.util.WeakBlock;

public class LeverListener implements Listener {

  @EventHandler
  public void onInvclick(final InventoryInteractEvent e) {
      if (e.getInventory().getHolder() instanceof StaticInventory) {
          e.getInventory().getViewers().get(0).closeInventory();
          e.setCancelled(true);
      }
  }
  
  @EventHandler
  public void onLog(final PlayerQuitEvent e) {
      InvSearch.requests.remove(e.getPlayer().getUniqueId());
  }
  
  @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
  public void tp(PlayerTeleportEvent e) {
  	var u = e.getPlayer().getUniqueId();
  	var wb = new WeakBlock(e.getFrom());
  	
  	Back.BACKS.put(u, wb);
  }

}
