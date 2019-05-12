package co.lotc.lever.cmd;

import static org.bukkit.ChatColor.*;

import co.lotc.core.bukkit.util.Run;
import co.lotc.core.command.annotate.Arg;
import co.lotc.core.command.annotate.Default;
import co.lotc.core.command.annotate.Flag;
import co.lotc.core.command.annotate.Range;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import co.lotc.core.bukkit.util.LocationUtil;
import co.lotc.lever.BaseCommand;

import java.util.*;
import java.util.List;

public class Countdown extends BaseCommand {
    public static final String PEX_COUNTDOWN_RADIUS = "lever.countdown.radius";
    public static final Set<UUID> PLAYERS_ON_COOLDOWN = new HashSet<>();

    @Flag(name="r",permission=PEX_COUNTDOWN_RADIUS,description="Countdown broadcast radius",type=double.class)
    public void invoke(Player source, @Arg("Timer") @Default("5") @Range(min=3,max=10) int timer) {
        validate(!PLAYERS_ON_COOLDOWN.contains(source.getUniqueId()), "Command on cooldown.");

        final double broadcastRadius = source.hasPermission(PEX_COUNTDOWN_RADIUS) && hasFlag("r") ? getFlag("r") : 20.0;
        List<Player> playersNearby = new ArrayList<>();
        String sourceName = source.getName();
        UUID sourceId = source.getUniqueId();
        long delayInTicks = 0L;

        PLAYERS_ON_COOLDOWN.add(sourceId);

        Bukkit.getOnlinePlayers().stream()
                .filter(player -> LocationUtil.isClose(source, player, broadcastRadius))
                .forEach(playersNearby::add);

        playersNearby.stream().filter(Objects::nonNull).forEach(player -> player.sendMessage(ITALIC + "" + GOLD + sourceName + DARK_GREEN + " has initiated a pvp timer, get ready!"));
        for (int i = timer; i > 0; i--) {
            final int iteration = i;
            delayInTicks += i == timer ? 60L : 20L;
            Run.as(plugin).delayed(delayInTicks, () -> playersNearby.stream().filter(Objects::nonNull).forEach(player -> player.sendMessage(GREEN + "PvP starts in... " + BOLD + iteration)));
        }
        delayInTicks += 20L;
        Run.as(plugin).delayed(delayInTicks, () -> playersNearby.stream().filter(Objects::nonNull).forEach(player -> player.sendMessage(BOLD + "" + DARK_GREEN + "PvP Begins!")));

        delayInTicks += 100L;
        Run.as(plugin).delayed(delayInTicks, () -> PLAYERS_ON_COOLDOWN.remove(sourceId));
    }

}
