package co.lotc.lever.cmd;

import co.lotc.core.bukkit.util.ChatBuilder;
import co.lotc.core.bukkit.util.LocationUtil;
import co.lotc.core.command.annotate.Arg;
import co.lotc.core.command.annotate.Flag;
import co.lotc.lever.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;

public class Emote extends BaseCommand {

    @Flag(name="r",description="Emote broadcast radius",type=double.class)
    @Flag(name="rp",description="Show RP name at the start of the emote")
    public void invoke(Player source, @Arg("Emote Text") String emoteText) {
        final double broadcastRadius = hasFlag("r") ? getFlag("r") : 64.0;

        ChatBuilder b = new ChatBuilder(hasFlag("rp") ? source.getDisplayName() + " " : "[!] ").color(YELLOW).hover("Sent by " + source.getDisplayName() + ".")
                .append(emoteText).color(YELLOW);
        Bukkit.getOnlinePlayers().stream()
                .filter(player->LocationUtil.isClose(source, player, broadcastRadius))
                .forEach(b::send);
    }
}
