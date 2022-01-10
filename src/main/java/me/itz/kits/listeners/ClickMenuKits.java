package me.itz.kits.listeners;

import me.itz.kits.Main;
import me.itz.kits.apis.Scroller;
import me.itz.kits.commands.CommandKit;
import me.itz.kits.objects.Categoria;
import me.itz.kits.objects.Kit;
import me.itz.kits.methods.Methods;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ClickMenuKits implements Listener {

    @EventHandler
    public void aoClicar(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) return;
        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        for (Categoria categoria : Main.getInstance().categoriaHashMap.values()) {
            if (e.getView().getTitle().equals(categoria.getTitulo())) {
                e.setCancelled(true);
                if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                    if (item.getItemMeta().getDisplayName().equals(categoria.getVoltar_nome())) {
                        CommandKit.AbrirMenuCategoria(p);
                    }
                    for (Kit kit : Main.getInstance().kits.values()) {
                        if (kit.getDisplay().equals(item.getItemMeta().getDisplayName())) {
                            if (e.getClick() == ClickType.RIGHT) {
                                Scroller sc = new Scroller.ScrollerBuilder(kit)
                                        .withSize(kit.getVisualizar_linhas() * 9)
                                        .withName(Main.getInstance().getConfig().getString("Menus.Visualizar.titulo").replace("%kit%", kit.getDisplay()
                                                .replace("§a", "§r")
                                                .replace("§b", "§r")
                                                .replace("§c", "§r")
                                                .replace("§f", "§r")
                                                .replace("§e", "§r")
                                                .replace("§0", "§r")
                                                .replace("§1", "§r")
                                                .replace("§2", "§r")
                                                .replace("§3", "§r")
                                                .replace("§4", "§r")
                                                .replace("§5", "§r")
                                                .replace("§6", "§r")
                                                .replace("§7", "§r")
                                                .replace("§8", "§r")
                                                .replace("§9", "§r")))
                                        .withItems(kit.getItens())
                                        .withItemsSlots(kit.getVisualizar_slots())
                                        .withBackItem(kit.getVisualizar_voltar(), new Scroller.PlayerRunnable() {
                                            @Override
                                            public void run(Player player) {
                                                Inventory inv = Bukkit.createInventory(null, categoria.getLinhas() * 9, categoria.getTitulo());
                                                for (Kit kits : Main.getInstance().kits.values()) {
                                                    if (kits.getCategoria().equals(categoria)) {
                                                        inv.setItem(kits.getSlot(), kits.getIcon());
                                                    }
                                                }
                                                inv.setItem(categoria.getVoltar_slot(), categoria.getVoltar_item());
                                                p.openInventory(inv);
                                            }
                                        })
                                        .withArrowsSlots(kit.getVisualizar_anterior(), kit.getVisualizar_proximo() - 1)
                                        .withOnClick(new Scroller.ChooseItemRunnable() {
                                            @Override
                                            public void run(Player player, ItemStack itemm) {
                                                if (itemm.hasItemMeta() && itemm.getItemMeta().hasDisplayName()) {
                                                    if (itemm.getItemMeta().getDisplayName().equals(Main.getInstance().getConfig().getString("Menus.Visualizar.itens.receber.nome").replace('&', '§'))) {
                                                        if (kit.getPerm().equals("nulo")) {
                                                            Methods.setar(player,kit);
                                                            return;
                                                        }
                                                        if (player.hasPermission(kit.getPerm())) {
                                                            Methods.setar(player,kit);
                                                        } else {
                                                            player.sendMessage(kit.getSemPermissao());
                                                        }
                                                    }
                                                }
                                            }
                                        })
                                        .build(kit,p);
                                sc.open(p);

                            }
                            if (e.getClick() == ClickType.LEFT) {
                                e.setCancelled(true);
                                if (kit.getPerm().equals("nulo")) {
                                    Methods.setar(p,kit);
                                    return;
                                }
                                if (p.hasPermission(kit.getPerm())) {
                                    Methods.setar(p,kit);
                                } else {
                                    p.sendMessage(kit.getSemPermissao());
                                }
                            }
                        }
                    }
                }
                e.setCancelled(true);
            }
        }
    }
}
