package me.godofpro.timerplugin.managers;

import me.godofpro.timerplugin.TimerPlugin;
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
    players.put(player.getUniqueId(), new Timer(plugin, player, whenEnds));
  }

  public Map<UUID, Timer> getPlayers() {
    return players;
  }
}
