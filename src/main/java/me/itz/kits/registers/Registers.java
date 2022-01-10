package me.itz.kits.registers;

import me.itz.kits.Main;
import me.itz.kits.commands.CommandKit;
import me.itz.kits.listeners.ClickMenuCategoria;
import me.itz.kits.listeners.ClickMenuKits;
import org.bukkit.Bukkit;

public class Registers {

    public static void comandos(Main main){
        main.getCommand("kits").setExecutor(new CommandKit());
        main.getCommand("darkit").setExecutor(new CommandKit());
    }
    public static void eventos(Main main){
        Bukkit.getPluginManager().registerEvents(new ClickMenuCategoria(),main);
        Bukkit.getPluginManager().registerEvents(new ClickMenuKits(),main);
    }

}
