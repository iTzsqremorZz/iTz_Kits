package me.itz.kits.listeners;

import me.itz.kits.Main;
import me.itz.kits.objects.Categoria;
import me.itz.kits.objects.Kit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ClickMenuCategoria implements Listener {

    @EventHandler
    public void aoClicar(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(e.getCurrentItem() == null)return;
        ItemStack item = e.getCurrentItem();
        if (e.getView().getTitle().equals(Main.getInstance().getConfig().getString("Menus.Categoria.titulo").replace('&', '§'))) {
            for(Categoria categoria: Main.getInstance().categoriaHashMap.values()){
                if(item.hasItemMeta() && item.getItemMeta().hasDisplayName()){
                    if(item.getItemMeta().getDisplayName().equals(categoria.getNome())){
                        Inventory inv = Bukkit.createInventory(null,categoria.getLinhas()*9,categoria.getTitulo());
                        for(Kit kits : Main.getInstance().kits.values()){
                            if(kits.getCategoria().equals(categoria)){
                                inv.setItem(kits.getSlot(),kits.getIcon());
                            }
                        }
                        inv.setItem(categoria.getVoltar_slot(),categoria.getVoltar_item());
                        p.openInventory(inv);
                        break;
                    }
                }
            }
            e.setCancelled(true);
        }
    }
}
