package me.itz.kits.database;

import me.itz.kits.Main;
import me.itz.kits.database.enums.Types;
import org.bukkit.Bukkit;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DatabaseConnection {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private Types type;
    private String user;
    private String password;
    private String host;
    private String database;

    public DatabaseConnection(Types type, String user, String password, String host, String database) {
        this.type = type;
        this.user = user;
        this.password = password;
        this.host = host;
        this.database = database;
        String createTable = "CREATE TABLE IF NOT EXISTS kits(player varchar(25),cool varchar)";
        if (this.type == Types.MYSQL) {
            long a = System.currentTimeMillis();
            try {
                Class.forName("com.mysql.jdbc.Driver");
                this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + "/" + this.database, this.user, this.password);
                this.preparedStatement = this.connection.prepareStatement(createTable);
                this.preparedStatement.execute();
            } catch (Exception e) {
                try {
                    Bukkit.getConsoleSender().sendMessage(Main.prefixError + "Não foi possível conectar ao MYSQL");
                    Bukkit.getConsoleSender().sendMessage(Main.prefixError + "Tentando abrir conexão com SQLITE");
                    File f = new File(Main.getInstance().getDataFolder(), "kits.db");
                    f.getParentFile().mkdir();
                    Class.forName("org.sqlite.JDBC");
                    this.connection = DriverManager.getConnection("jdbc:sqlite:" + f);
                    this.preparedStatement = this.connection.prepareStatement(createTable);
                    this.preparedStatement.execute();
                    long b = System.currentTimeMillis() - a;
                    Bukkit.getConsoleSender().sendMessage(Main.prefix + "Conexão com SQLITE aberta com sucesso " + b + "ms");
                } catch (Exception er) {
                    Bukkit.getConsoleSender().sendMessage(Main.prefixError + "Não foi possível conectar ao SQLITE desativando plugin.");
                    Bukkit.getPluginManager().disablePlugin(Main.getInstance());
                }
            }
        } else if (type == Types.SQLITE) {
            long a = System.currentTimeMillis();
            try {
                Bukkit.getConsoleSender().sendMessage(Main.prefix + "Abrindo conexão com SQLITE");
                File f = new File(Main.getInstance().getDataFolder(), "kits.db");
                f.getParentFile().mkdir();
                Class.forName("org.sqlite.JDBC");
                this.connection = DriverManager.getConnection("jdbc:sqlite:" + f);
                this.preparedStatement = this.connection.prepareStatement(createTable);
                this.preparedStatement.execute();
                long b = System.currentTimeMillis() - a;
                Bukkit.getConsoleSender().sendMessage(Main.prefix + "Conexão com SQLITE aberta com sucesso " + b + "ms");
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(Main.prefixError + "Não foi possível conectar ao SQLITE desativando plugin.");
                Bukkit.getPluginManager().disablePlugin(Main.getInstance());
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public void setPreparedStatement(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }

    public Types getType() {
        return type;
    }

    public void setTypes(Types type) {
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
