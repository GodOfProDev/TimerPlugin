package me.godofpro.timerplugin.utils;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class ChatUtil {

  public static @NotNull String translate(String str) {
    return ChatColor.translateAlternateColorCodes('&', str);
  }
}
