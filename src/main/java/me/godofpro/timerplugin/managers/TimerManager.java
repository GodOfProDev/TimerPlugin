package me.godofpro.timerplugin.managers;

import me.godofpro.timerplugin.TimerPlugin;
import me.godofpro.timerplugin.utils.ChatUtil;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TimerManager {

  private final Map<UUID, Timer> players = new HashMap<>();

  private final TimerPlugin plugin;

  public TimerManager(TimerPlugin plugin) {
    this.plugin = plugin;
  }

  public void addPlayer(Player player, Instant whenEnds) {
    if (!players.containsKey(player.getUniqueId())) {
      players.put(player.getUniqueId(), new Timer(plugin, player, whenEnds, this));
    }else {
      player.sendMessage(ChatUtil.translate("&cThere is already a timer for you"));
    }
  }

  public void stopTimer(Player player) {
    if (players.containsKey(player.getUniqueId())) {
      players.get(player.getUniqueId()).stopTimer();
      players.remove(player.getUniqueId());
    } else {
      player.sendMessage(ChatUtil.translate("&cThere isn't any timer for you"));
    }
  }

  public void pauseTimer(Player player) {
    if (players.containsKey(player.getUniqueId())) {
      players.get(player.getUniqueId()).pauseTimer();
    } else {
      player.sendMessage(ChatUtil.translate("&cThere isn't any timer for you"));
    }
  }

  public void continueTimer(Player player) {
    if (players.containsKey(player.getUniqueId())) {
      players.get(player.getUniqueId()).continueTimer();
    } else {
      player.sendMessage(ChatUtil.translate("&cThere isn't any timer for you"));
    }
  }

  public void removeTimer(Player player) {
    players.remove(player.getUniqueId());
    player.sendMessage(ChatUtil.translate("&cThe timer has ended"));
  }

  public Map<UUID, Timer> getPlayers() {
    return players;
  }
}
