package me.itz.kits.commands;

import me.itz.kits.Main;
import me.itz.kits.cool.CoolDownKit;
import me.itz.kits.cool.CoolDownPlayer;
import me.itz.kits.mesagens.Mensagens;
import me.itz.kits.objects.Categoria;
import me.itz.kits.objects.Kit;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CommandKit implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cComando apenas para players in-game!");
            return true;
        }
        Player p = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("kits")) {
            AbrirMenuCategoria(p);
        }
        if (cmd.getName().equalsIgnoreCase("darkit")) {
            if (p.hasPermission("kit.darkit")) {
                if (args.length == 2) {
                    Player p2 = Bukkit.getPlayer(args[0]);
                    if (p2 == null) {
                        p.sendMessage("§cEste jogador esta offline");
                        return true;
                    }
                    if (!Main.getInstance().kits.containsKey(args[1])) {
                        p.sendMessage("§cEste kit não existe");
                        return true;
                    }

                    Kit kit = Main.getInstance().kits.get(args[1]);

                    int sl = 0;
                    for (ItemStack it : p2.getInventory().getContents()) {
                        if (it == null) sl++;
                    }
                    if (kit.getItens().size() <= sl) {
                        for (ItemStack is : kit.getItens()) {
                            p2.getInventory().addItem(is);
                        }
                        p.sendMessage("§aVocê deu um kit '" + kit.getDisplay() + "§a' para " + args[0]);
                    } else {
                        p.sendMessage("§cEste jogador não tem espaço suficiente no inventario");
                    }

                } else {
                    p.sendMessage("§cUse: /darkit <jogador> <kit>");
                }
            } else {
                p.sendMessage("§cVocê não tem permissão para utilizar este comando!");
            }
        }
        return false;
    }

    public static void AbrirMenuCategoria(Player p) {
        Inventory inv = Bukkit.createInventory(null, Main.getInstance().getConfig().getInt("Menus.Categoria.linhas") * 9, Main.getInstance().getConfig().getString("Menus.Categoria.titulo")
                .replace('&', '§'));
        for (Categoria categoria : Main.getInstance().categoriaHashMap.values()) {
            inv.setItem(categoria.getSlot(), categoria.getIcon());
        }
        p.openInventory(inv);
    }
}
