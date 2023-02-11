package me.godofpro.timerplugin.managers;

import me.godofpro.timerplugin.TimerPlugin;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class Timer {

  private final BukkitScheduler scheduler;
  private BukkitTask task;
  private Instant timerEndTime;
  private final TimerPlugin plugin;
  private final Player player;
  private BossBar activeBossbar;

  private final TimerManager timerManager;

  private boolean pause = false;

  public Timer(TimerPlugin plugin, Player player, Instant whenEnds, TimerManager timerManager) {
    this.timerManager = timerManager;
    this.plugin = plugin;
    this.scheduler = Bukkit.getScheduler();
    this.player = player;

    activateTimer(whenEnds);
  }

  public void stopTimer() {
    hideActiveBossBar();
    task.cancel();
  }

  public void pauseTimer() {
    this.pause = true;
  }

  public void continueTimer() {
    this.pause = false;
  }

  private void activateTimer(Instant whenEnds) {
    this.timerEndTime = whenEnds;
    constructBossbar();
    task = scheduler.runTaskTimer(plugin, this::run, 20L, 20L);
  }

  private void run() {
    if (pause) return;

    Instant now = Instant.now();
    if (now.isAfter(this.timerEndTime)) {
      hideActiveBossBar();
      task.cancel();
      timerManager.removeTimer(player);
      return;
    }

    this.activeBossbar.name(makeBossbarTitle());

    player.showBossBar(this.activeBossbar);
  }

  private void constructBossbar() {
    TextComponent title = makeBossbarTitle();
    final BossBar bar = BossBar.bossBar(title, 1, BossBar.Color.BLUE, BossBar.Overlay.NOTCHED_20);

    this.activeBossbar = bar;
  }

  private TextComponent makeBossbarTitle() {
    return Component.text("TIMER RUNNING FOR " + getTimeRemaining()).color(TextColor.color(255, 245, 54));
  }

  private void hideActiveBossBar() {
    if (this.activeBossbar == null) return;

    Bukkit.getOnlinePlayers().forEach(player -> {
      player.hideBossBar(this.activeBossbar);
    });
    this.activeBossbar = null;
  }


  private String getTimeRemaining() {
    DateTimeFormatter formatter = new DateTimeFormatterBuilder()
      .appendValue(ChronoField.MINUTE_OF_HOUR)
      .appendLiteral(':')
      .appendValue(ChronoField.SECOND_OF_MINUTE)
      .toFormatter();

    String formatted = formatter.format(this.timerEndTime.minus(System.currentTimeMillis(), ChronoUnit.MILLIS).atOffset(ZoneOffset.UTC));
    return formatted;
  }
}
