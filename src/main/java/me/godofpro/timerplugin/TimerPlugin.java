package me.godofpro.timerplugin;

import me.godofpro.timerplugin.commands.TimerCommand;
import me.godofpro.timerplugin.managers.Timer;
import me.godofpro.timerplugin.managers.TimerManager;
import org.bukkit.plugin.java.JavaPlugin;

public class TimerPlugin extends JavaPlugin {

  private TimerManager timerManager;

  @Override
  public void onEnable() {
    this.timerManager = new TimerManager(this);
    getCommand("timer").setExecutor(new TimerCommand(this));
  }

  public TimerManager getTimerManager() {
    return timerManager;
  }
}
