package me.itz.kits.mesagens;

import me.itz.kits.Main;
import org.bukkit.configuration.file.FileConfiguration;

public class Mensagens {

    public static String espaço_insuficiente;
    public static String kit_entregue;
    public static String tentou_pegar_kit;

    public Mensagens() {
        FileConfiguration config = Main.getInstance().getConfig();
        espaço_insuficiente = config.getString("mensagens.espaço-insuficiente").replace('&','§');
        kit_entregue = config.getString("mensagens.kit-entregue").replace('&','§');
        tentou_pegar_kit = config.getString("mensagens.tentou-pegar-kit").replace('&','§');
    }
}
