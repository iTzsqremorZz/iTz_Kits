package me.itz.kits.apis;

import me.itz.kits.Main;
import me.itz.kits.cool.CoolDownKit;
import me.itz.kits.cool.CoolDownPlayer;
import me.itz.kits.loaders.Loaders;
import me.itz.kits.objects.Kit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.ParseException;
import java.util.*;

public class Scroller {
    static {
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onClick(InventoryClickEvent e) throws ParseException {
                if (e.getInventory().getHolder() instanceof ScrollerHolder) {
                    e.setCancelled(true);
                    ScrollerHolder holder = (ScrollerHolder) e.getInventory().getHolder();
                    if (e.getSlot() == holder.getScroller().previousPage) {
                        if (holder.getScroller().hasPage(holder.getPage() - 1)) {
                            holder.getScroller().open((Player) e.getWhoClicked(), holder.getPage() - 1);
                        }
                    } else if (e.getSlot() == holder.getScroller().nextPage) {
                        if (holder.getScroller().hasPage(holder.getPage() + 1)) {
                            holder.getScroller().open((Player) e.getWhoClicked(), holder.getPage() + 1);
                        }
                    } else if (e.getSlot() == holder.getScroller().backSlot) {
                        e.getWhoClicked().closeInventory();
                        holder.getScroller().backRunnable.run((Player) e.getWhoClicked());
                    } else if (holder.getScroller().slots.contains(e.getSlot())
                            && holder.getScroller().onClickRunnable != null) {
                        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR)
                            return;
                        holder.getScroller().onClickRunnable.run((Player) e.getWhoClicked(), e.getCurrentItem());
                    }
                }
            }
        }, Main.getPlugin(Main.class));
    }

    private List<ItemStack> items;
    private HashMap<Integer, Inventory> pages;
    private String name;
    private int inventorySize;
    private List<Integer> slots;
    private int backSlot, previousPage, nextPage;
    private PlayerRunnable backRunnable;
    private ChooseItemRunnable onClickRunnable;

    public Scroller(ScrollerBuilder builder, Kit kit, Player p) {
        this.items = builder.items;
        this.pages = new HashMap<>();
        this.name = builder.name;
        this.inventorySize = builder.inventorySize;
        this.slots = builder.slots;
        this.backSlot = builder.backSlot;
        this.backRunnable = builder.backRunnable;
        this.previousPage = builder.previousPage;
        this.nextPage = builder.nextPage;
        this.onClickRunnable = builder.clickRunnable;
        createInventories(kit, p);
    }

    public void createInventories(Kit kit, Player p) {
        List<List<ItemStack>> lists = getPages(items, slots.size() - 1);
        int page = 1;
        for (List<ItemStack> list : lists) {
            Inventory inventory = Bukkit.createInventory(new ScrollerHolder(this, page), inventorySize, name);
            int slot = 0;
            for (ItemStack it : list) {
                inventory.setItem(slots.get(slot), it);
                slot++;
            }

            if (page != 1) inventory.setItem(previousPage, getPageFlecha(page - 1));

            inventory.setItem(nextPage, getPageFlecha(page + 1));
            if (backRunnable != null) inventory.setItem(backSlot, getBackFlecha());
            pages.put(page, inventory);
            page++;
            boolean skull = true;
            ItemStack it = null;
            if (Main.getInstance().coolDown.containsKey(p.getName())) {
                CoolDownPlayer cp = Main.getInstance().coolDown.get(p.getName());
                for (CoolDownKit ck : cp.getCoolDownKitList()) {
                    if (ck.getKit() == kit) {
                        if ((ck.getTime() - System.currentTimeMillis()) <= 0) {
                            for (char c : Main.getInstance().getConfig().getString("Menus.Visualizar.itens.receber.item").toCharArray()) {
                                String c2 = c + "";
                                if (c2.contains(":")) {
                                    skull = false;
                                }
                            }
                            String icone = Main.getInstance().getConfig().getString("Menus.Visualizar.itens.receber.item");
                            if (!skull) {
                                it = new ItemStack(Material.getMaterial(icone.split(":")[0].toUpperCase()), 1, (short) Integer.parseInt(icone.split(":")[1]));
                                ItemMeta meta = it.getItemMeta();
                                meta.setDisplayName(Main.getInstance().getConfig().getString("Menus.Visualizar.itens.receber.nome").replace('&', '§'));
                                List<String> lore = new ArrayList<>();
                                for (String s : Main.getInstance().getConfig().getStringList("Menus.Visualizar.itens.receber.lore")) {
                                    lore.add(s.replace('&', '§')
                                            .replace("%tempo%", TimeAPI.getMSG(ck.getTime())));
                                }
                                meta.setLore(lore);
                                it.setItemMeta(meta);
                            } else {
                                it = Loaders.createSkull(icone);
                                ItemMeta meta = it.getItemMeta();
                                meta.setDisplayName(Main.getInstance().getConfig().getString("Menus.Visualizar.itens.receber.nome").replace('&', '§'));
                                List<String> lore = new ArrayList<>();
                                for (String s : Main.getInstance().getConfig().getStringList("Menus.Visualizar.itens.receber.lore")) {
                                    lore.add(s.replace('&', '§')
                                            .replace("%tempo%", TimeAPI.getMSG(ck.getTime())));
                                }
                                meta.setLore(lore);
                                it.setItemMeta(meta);
                            }
                        } else {
                            for (char c : Main.getInstance().getConfig().getString("Menus.Visualizar.itens.receber_recebeu.item").toCharArray()) {
                                String c2 = c + "";
                                if (c2.contains(":")) {
                                    skull = false;
                                }
                            }
                            String icone = Main.getInstance().getConfig().getString("Menus.Visualizar.itens.receber_recebeu.item");
                            if (!skull) {
                                it = new ItemStack(Material.getMaterial(icone.split(":")[0].toUpperCase()), 1, (short) Integer.parseInt(icone.split(":")[1]));
                                ItemMeta meta = it.getItemMeta();
                                meta.setDisplayName(Main.getInstance().getConfig().getString("Menus.Visualizar.itens.receber_recebeu.nome").replace('&', '§'));
                                List<String> lore = new ArrayList<>();
                                for (String s : Main.getInstance().getConfig().getStringList("Menus.Visualizar.itens.receber_recebeu.lore")) {
                                    lore.add(s.replace('&', '§')
                                            .replace("%tempo%", TimeAPI.getMSG(ck.getTime())));
                                }
                                meta.setLore(lore);
                                it.setItemMeta(meta);
                            } else {
                                it = Loaders.createSkull(icone);
                                ItemMeta meta = it.getItemMeta();
                                meta.setDisplayName(Main.getInstance().getConfig().getString("Menus.Visualizar.itens.receber_recebeu.nome").replace('&', '§'));
                                List<String> lore = new ArrayList<>();
                                for (String s : Main.getInstance().getConfig().getStringList("Menus.Visualizar.itens.receber_recebeu.lore")) {
                                    lore.add(s.replace('&', '§')
                                            .replace("%tempo%", TimeAPI.getMSG(ck.getTime())));
                                }
                                meta.setLore(lore);
                                it.setItemMeta(meta);
                            }
                        }
                    }
                }
            }else{
                for (char c : Main.getInstance().getConfig().getString("Menus.Visualizar.itens.receber.item").toCharArray()) {
                    String c2 = c + "";
                    if (c2.contains(":")) {
                        skull = false;
                    }
                }
                String icone = Main.getInstance().getConfig().getString("Menus.Visualizar.itens.receber.item");
                if (!skull) {
                    it = new ItemStack(Material.getMaterial(icone.split(":")[0].toUpperCase()), 1, (short) Integer.parseInt(icone.split(":")[1]));
                    ItemMeta meta = it.getItemMeta();
                    meta.setDisplayName(Main.getInstance().getConfig().getString("Menus.Visualizar.itens.receber.nome").replace('&', '§'));
                    List<String> lore = new ArrayList<>();
                    for (String s : Main.getInstance().getConfig().getStringList("Menus.Visualizar.itens.receber.lore")) {
                        lore.add(s.replace('&', '§'));
                    }
                    meta.setLore(lore);
                    it.setItemMeta(meta);
                } else {
                    it = Loaders.createSkull(icone);
                    ItemMeta meta = it.getItemMeta();
                    meta.setDisplayName(Main.getInstance().getConfig().getString("Menus.Visualizar.itens.receber.nome").replace('&', '§'));
                    List<String> lore = new ArrayList<>();
                    for (String s : Main.getInstance().getConfig().getStringList("Menus.Visualizar.itens.receber.lore")) {
                        lore.add(s.replace('&', '§'));
                    }
                    meta.setLore(lore);
                    it.setItemMeta(meta);
                }
            }
            inventory.setItem(kit.getVisualizar_receber(), it);
        }
        pages.get(pages.size()).setItem(nextPage, new ItemStack(Material.AIR));

    }

    private ItemStack getBackFlecha() {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Voltar");
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack getPageFlecha(int page) {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Página " + page);
        item.setItemMeta(meta);
        return item;
    }

    public int getPages() {
        return pages.size();
    }

    public boolean hasPage(int page) {
        return pages.containsKey(page);
    }

    public void open(Player player) {
        open(player, 1);
    }

    public void open(Player player, int page) {
        // player.closeInventory();
        player.openInventory(pages.get(page));
    }

    private <T> List<List<T>> getPages(Collection<T> c, Integer pageSize) {
        List<T> list = new ArrayList<T>(c);
        if (pageSize == null || pageSize <= 0 || pageSize > list.size())
            pageSize = list.size();
        int numPages = (int) Math.ceil((double) list.size() / (double) pageSize);
        List<List<T>> pages = new ArrayList<List<T>>(numPages);
        for (int pageNum = 0; pageNum < numPages; )
            pages.add(list.subList(pageNum * pageSize, Math.min(++pageNum * pageSize, list.size())));
        return pages;
    }

    private class ScrollerHolder implements InventoryHolder {
        private Scroller scroller;
        private int page;

        public ScrollerHolder(Scroller scroller, int page) {
            super();
            this.scroller = scroller;
            this.page = page;
        }

        @Override
        public Inventory getInventory() {
            return null;
        }

        /**
         * @return the scroller
         */
        public Scroller getScroller() {
            return scroller;
        }

        /**
         * @return the page
         */
        public int getPage() {
            return page;
        }
    }

    public interface PlayerRunnable {

        public void run(Player player);

    }

    public interface ChooseItemRunnable {
        public void run(Player player, ItemStack item);
    }

    public static class ScrollerBuilder {
        private List<ItemStack> items;
        private String name;
        private int inventorySize;
        private List<Integer> slots;
        private int backSlot, previousPage, nextPage;
        private PlayerRunnable backRunnable;
        private ChooseItemRunnable clickRunnable;
        private Kit kit;

        private final static List<Integer> ALLOWED_SLOTS = Arrays.asList(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23,
                24, 25, 28, 29, 30, 31, 32, 33, 34
                /* ,37,38,39,40,41,42,43 */); // slots para caso o inventário tiver 6 linhas

        public ScrollerBuilder(Kit kit) {
            // default values
            this.items = new ArrayList<>();
            this.name = "";
            this.inventorySize = 45;
            this.slots = ALLOWED_SLOTS;
            this.backSlot = -1;
            this.previousPage = 18;
            this.nextPage = 26;
            this.kit = kit;
        }

        public ScrollerBuilder withItems(List<ItemStack> items) {
            this.items = items;
            return this;
        }

        public ScrollerBuilder withOnClick(ChooseItemRunnable clickRunnable) {
            this.clickRunnable = clickRunnable;
            return this;
        }

        public ScrollerBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ScrollerBuilder withSize(int size) {
            this.inventorySize = size;
            return this;
        }

        public ScrollerBuilder withArrowsSlots(int previousPage, int nextPage) {
            this.previousPage = previousPage;
            this.nextPage = nextPage;
            return this;
        }

        public ScrollerBuilder withBackItem(int slot, PlayerRunnable runnable) {
            this.backSlot = slot;
            this.backRunnable = runnable;
            return this;
        }

        public ScrollerBuilder withItemsSlots(Integer[] slots) {
            Integer[] sl = new Integer[slots.length + 1];
            int a = 0;
            for (Integer s : slots) {
                sl[a] = s;
                a++;
            }
            sl[a] = kit.getVisualizar_receber();
            this.slots = Arrays.asList(sl);
            return this;
        }

        public Scroller build(Kit kit, Player p) {
            return new Scroller(this, kit, p);
        }

    }

}