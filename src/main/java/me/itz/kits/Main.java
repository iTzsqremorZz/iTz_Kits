package me.itz.kits;

import me.itz.kits.cool.CoolDownPlayer;
import me.itz.kits.database.DatabaseConnection;
import me.itz.kits.database.enums.Types;
import me.itz.kits.database.save.DataManager;
import me.itz.kits.loaders.Loaders;
import me.itz.kits.mesagens.Mensagens;
import me.itz.kits.objects.Categoria;
import me.itz.kits.objects.Kit;
import me.itz.kits.registers.Registers;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.HashMap;

public final class Main extends JavaPlugin {

    public HashMap<String, CoolDownPlayer> coolDown = new HashMap<>();
    public HashMap<String, Categoria> categoriaHashMap = new HashMap<>();
    public HashMap<String, Kit> kits = new HashMap<>();
    public static String prefix = "§b[iTz_Kits] §f";
    public static String prefixError = "§c[iTz_Kits] §f";
    public DatabaseConnection connection = null;
    public String teste;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(Main.prefix + "Carregando recursos do plugin");
        saveDefaultConfig();
        new Mensagens();
        connection = new DatabaseConnection(
                Types.valueOf(getConfig().getString("Database.Type").toUpperCase()),
                getConfig().getString("Database.User"),
                getConfig().getString("Database.Password"),
                getConfig().getString("Database.Host"),
                getConfig().getString("Database.database"));
        Loaders.categorias(this);
        Loaders.kits(this);
        DataManager.load();
        Registers.comandos(this);
        Registers.eventos(this);
    }

    @Override
    public void onDisable() {
        if (connection != null) {
            try {
                if (!connection.getConnection().isClosed()) {
                    DataManager.saveAll();
                    connection.getConnection().close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Main getInstance() {
        return Main.getPlugin(Main.class);
    }

}
