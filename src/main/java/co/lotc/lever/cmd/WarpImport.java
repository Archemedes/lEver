package co.lotc.lever.cmd;

import co.lotc.lever.BaseCommand;
import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.Warps;
import com.earth2me.essentials.commands.WarpNotFoundException;
import net.ess3.api.InvalidWorldException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WarpImport extends BaseCommand {

    public void invoke(Player player) {
        Essentials ess = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        Warps warps = ess.getWarps();
        int imported = 0;
        for (String warpName : warps.getList()) {
            try {
                Location location = warps.getWarp(warpName);
                plugin.getWarpManager().add(warpName, location);
            } catch (WarpNotFoundException | InvalidWorldException ignored) {
            }
        }
    }
}
