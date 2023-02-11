package me.godofpro.timerplugin.commands;

import me.godofpro.timerplugin.TimerPlugin;
import me.godofpro.timerplugin.utils.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class TimerCommand implements CommandExecutor {

  private final TimerPlugin plugin;

  public TimerCommand(TimerPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (!(sender instanceof Player player)) {
      sender.sendMessage(ChatUtil.translate("&cOnly players can use this command"));
      return true;
    }

    System.out.println(args.length);
    System.out.println(args);

    plugin.getTimerManager().addPlayer(player, Instant.now().plus(1, ChronoUnit.MINUTES));

    return true;
  }
}
