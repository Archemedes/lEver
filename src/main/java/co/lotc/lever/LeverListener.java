package co.lotc.lever;

import java.util.Objects;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import co.lotc.core.bukkit.util.Run;
import co.lotc.core.bukkit.util.WeakBlock;
import co.lotc.lever.Lever.StaticInventory;
import co.lotc.lever.cmd.Back;
import co.lotc.lever.cmd.InvSearch;
import co.lotc.lever.cmd.Trash;
import co.lotc.lever.cmd.ViewDistance;
import lombok.var;

public class LeverListener implements Listener {

  @EventHandler
  public void onInvclick(final InventoryInteractEvent e) {
      if (e.getInventory().getHolder() instanceof StaticInventory) {
          Run.as(Lever.get()).sync(()->e.getInventory().getViewers().get(0).closeInventory());
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
  
  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
      event.getPlayer().setViewDistance(ViewDistance.viewDistance);
  }
  
  @EventHandler
  public void onInvClose(InventoryCloseEvent e) {
  	if(!Bukkit.getPluginManager().isPluginEnabled("Omniscience"))
  		return;
  	
  	var i = e.getInventory();
  	if(i.getHolder() instanceof Trash.TrashCan) {
  		
  		Stream.of(i.getContents())
  		.filter(Objects::nonNull)
  		.forEach(is->OmniUtil.logItem(e.getPlayer(), is));
  	}
  }

}
