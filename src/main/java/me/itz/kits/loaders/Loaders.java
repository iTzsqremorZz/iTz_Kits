package me.itz.kits.loaders;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.itz.kits.Main;
import me.itz.kits.apis.TimeAPI;
import me.itz.kits.objects.Categoria;
import me.itz.kits.objects.Kit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Loaders {

    public static void categorias(Main main) {
        long a = System.currentTimeMillis();
        FileConfiguration config = main.getConfig();
        for (String ls : config.getConfigurationSection("Categorias").getKeys(false)) {
            String get = "Categorias." + ls;
            String titulo = config.getString(get + ".titulo");
            int slot = config.getInt(get + ".slot");
            int linhas = config.getInt(get + ".linhas");
            ItemStack voltar_item = new ItemStack(Material.getMaterial(config.getString(get + ".voltar.item")));
            ItemMeta voltar_meta = voltar_item.getItemMeta();
            int voltar_slot = config.getInt(get + ".voltar.slot");
            String voltar_nome = config.getString(get + ".voltar.nome").replace('&', '§');
            voltar_meta.setDisplayName(voltar_nome);
            voltar_item.setItemMeta(voltar_meta);
            String nome = config.getString(get + ".nome").replace('&', '§');
            String icone = config.getString(get + ".icone");
            boolean skull = true;
            List<String> lore = new ArrayList<>();
            for (String l : config.getStringList(get + ".lore")) {
                lore.add(l.replace('&', '§'));
            }
            for (char c : icone.toCharArray()) {
                String c2 = c + "";
                if (c2.contains(":")) {
                    skull = false;
                }
            }
            ItemStack it = null;
            if (!skull) {
                it = new ItemStack(Material.getMaterial(icone.split(":")[0].toUpperCase()), 1, (short) Integer.parseInt(icone.split(":")[1]));
                ItemMeta meta = it.getItemMeta();
                meta.setDisplayName(nome);
                meta.setLore(lore);
                it.setItemMeta(meta);
            } else {
                it = createSkull(icone);
                ItemMeta meta = it.getItemMeta();
                meta.setDisplayName(nome);
                meta.setLore(lore);
                it.setItemMeta(meta);
            }
            Categoria categoria = new Categoria(titulo, slot, linhas, nome, it, lore, voltar_item, voltar_nome, voltar_slot);
            Main.getInstance().categoriaHashMap.put(ls, categoria);
        }
        long b = System.currentTimeMillis() - a;
        Bukkit.getConsoleSender().sendMessage(Main.prefix + "Categorias carregadas §f" + b + "ms");
    }

    public static void kits(Main main) {
        long a = System.currentTimeMillis();
        FileConfiguration config = main.getConfig();
        for (String ls : config.getConfigurationSection("Kits").getKeys(false)) {
            String get = "Kits." + ls;
            String categoria = config.getString(get + ".categoria");
            if (!Main.getInstance().categoriaHashMap.containsKey(categoria)) {
                Bukkit.getConsoleSender().sendMessage("§cA categoria do kit §f" + ls + " §cnão existe");
                return;
            }
            String nome = config.getString(get + ".nome").replace('&', '§');
            String perm = config.getString(get + ".perm");
            String sem_permissao = config.getString(get + ".sem_permissao").replace('&', '§');
            int slot = config.getInt(get + ".slot");
            String icone = config.getString(get + ".icone");
            boolean skull = true;
            List<String> lore = new ArrayList<>();
            for (String l : config.getStringList(get + ".lore")) {
                lore.add(l.replace('&', '§'));
            }
            for (char c : icone.toCharArray()) {
                String c2 = c + "";
                if (c2.contains(":")) {
                    skull = false;
                }
            }
            ItemStack it = null;
            if (!skull) {
                it = new ItemStack(Material.getMaterial(icone.split(":")[0].toUpperCase()), 1, (short) Integer.parseInt(icone.split(":")[1]));
                ItemMeta meta = it.getItemMeta();
                meta.setDisplayName(nome);
                meta.setLore(lore);
                it.setItemMeta(meta);
            } else {
                it = createSkull(icone);
                ItemMeta meta = it.getItemMeta();
                meta.setDisplayName(nome);
                meta.setLore(lore);
                it.setItemMeta(meta);
            }

            long tempo = TimeAPI.getTicks(config.getString(get + ".espera").split(":")[0], Integer.parseInt(config.getString(get + ".espera").split(":")[1]));
            int visualizar_linhas = config.getInt(get + ".visualizar_linhas");
            int visualizar_voltar = config.getInt(get + ".visualizar_voltar");
            int visualizar_receber = config.getInt(get + ".visualizar_receber");
            int visualizar_proximo = config.getInt(get + ".visualizar_proximo");
            int visualizar_anterior = config.getInt(get + ".visualizar_anterior");
            List<Integer> visualizar_slots = config.getIntegerList(get + ".visualizar_slots");
            Integer[] slots = new Integer[visualizar_slots.size()];
            for (int i = 0; i < visualizar_slots.size(); i++) {
                slots[i] = visualizar_slots.get(i);
            }
            List<ItemStack> its = new ArrayList<>();
            for (String itens : config.getStringList(get + ".items")) {
                ItemStack item = new ItemStack(Material.getMaterial(itens.split(":")[0].toUpperCase()), Integer.parseInt(itens.split(":")[2]), (short)Integer.parseInt(itens.split(":")[1]));
                ItemMeta meta = item.getItemMeta();
                for (int i = 3; i < itens.split(":").length; i++) {
                    int lvl = Integer.parseInt(itens.split(":")[i + 1]);
                    meta.addEnchant(Enchantment.getByName(itens.split(":")[i]), lvl, true);
                    i++;
                }
                item.setItemMeta(meta);
                its.add(item);
            }
            List<String> comandos = config.getStringList(get + ".comandos");
            Kit kit = new Kit(nome, Main.getInstance().categoriaHashMap.get(categoria), perm, sem_permissao, slot, it, lore, tempo, visualizar_linhas, visualizar_voltar, visualizar_receber, visualizar_proximo, visualizar_anterior, slots, its, comandos);
            kit.setNome(ls);
            Main.getInstance().kits.put(ls, kit);
        }
        long b = System.currentTimeMillis() - a;
        Bukkit.getConsoleSender().sendMessage(Main.prefix + "Kits carregados §f" + b + "ms");
    }


    public static ItemStack createSkull(String url) {
        String prefix = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv";
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        if (url.isEmpty()) return head;

        SkullMeta sm = (SkullMeta) head.getItemMeta();
        GameProfile gm = new GameProfile(UUID.randomUUID(), null);
        gm.getProperties().put("textures", new Property("textures", prefix + url));

        try {
            Field f = sm.getClass().getDeclaredField("profile");
            f.setAccessible(true);
            f.set(sm, gm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        head.setItemMeta(sm);
        return head;
    }
}
