package co.lotc.lever.cmd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.lotc.core.command.annotate.Cmd;
import co.lotc.lever.BaseCommand;

public class ViewDistance extends BaseCommand {
	public static int viewDistance = Bukkit.getViewDistance();

  @Cmd("Set a target's view distance")
  public void set(CommandSender sender, Player target, int distance) {
      target.setViewDistance(distance);
      sender.sendMessage(ChatColor.AQUA + target.getName() + "'s" + ChatColor.WHITE + "view distance set to " + ChatColor.GOLD + distance);
  }

  @Cmd("Set global view distance")
  public void set(int distance) {
      viewDistance = distance;
      for (Player p : Bukkit.getOnlinePlayers()) {
          p.setViewDistance(distance);
      }
      msg(ChatColor.AQUA + "Global view distance set to " + ChatColor.GOLD + distance);
  }

  @Cmd("Show a target's view distance")
  public void info(CommandSender sender, Player target) {
     msg(ChatColor.AQUA + target.getName() + "'s" + ChatColor.WHITE + "view distance is " + ChatColor.GOLD + target.getViewDistance());
  }

  @Cmd(value = "Get server's current view distance")
  public void info() {
      msg(ChatColor.AQUA + "Server's view distance is currently " + ChatColor.GOLD + viewDistance);
  }
	
}
