package me.itz.kits.methods;

import me.itz.kits.Main;
import me.itz.kits.apis.TimeAPI;
import me.itz.kits.cool.CoolDownKit;
import me.itz.kits.cool.CoolDownPlayer;
import me.itz.kits.mesagens.Mensagens;
import me.itz.kits.objects.Kit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Methods {

    public static CoolDownKit cdk;

    public static void setar(Player player, Kit kit) {
        if (Main.getInstance().coolDown.containsKey(player.getName())) {
            CoolDownPlayer cdp = null;
            cdp = Main.getInstance().coolDown.get(player.getName());
            for (CoolDownKit cd : cdp.getCoolDownKitList()) {
                if (cd.getKit() == kit) {
                    long tempo = cd.getTime() - System.currentTimeMillis();
                    if (tempo <= 0) {
                        if (cdp.getCoolDownKitList().size() == 1) {
                            Main.getInstance().coolDown.get(player.getName()).setCoolDownKitList(null);
                            Main.getInstance().coolDown.get(player.getName()).setPlayer(null);
                            Main.getInstance().coolDown.remove(player.getName());
                            cdp.destroy();
                            cd.destroy();
                            darItens(player, kit);
                        } else if (cdp.getCoolDownKitList().size() > 1) {
                            cdp.getCoolDownKitList().remove(cd);
                            Main.getInstance().coolDown.get(player.getName()).setCoolDownKitList(cdp.getCoolDownKitList());
                            cd.destroy();
                            cdp.destroy();
                            darItens(player, kit);
                        }
                    } else {
                        player.sendMessage(Mensagens.tentou_pegar_kit.replace("%tempo%", TimeAPI.getMSG(cd.getTime())));
                    }
                    return;
                }
            }
        }
        darItens(player, kit);
    }

    public static void darItens(Player player, Kit kit) {
        int sl = 0;
        for (ItemStack it : player.getInventory().getContents()) {
            if (it == null) sl++;
        }
        if (kit.getItens().size() <= sl) {
            player.sendMessage(Mensagens.kit_entregue.replace("%kit%", kit.getDisplay()));
            cdk = new CoolDownKit(kit.getTempo() + System.currentTimeMillis(), kit, player.getName());
            List<CoolDownKit> cds = new ArrayList<>();
            cds.add(cdk);
            player.closeInventory();
            if (Main.getInstance().coolDown.containsKey(player.getName())) {
                Main.getInstance().coolDown.get(player.getName()).getCoolDownKitList().add(cdk);
            } else {
                CoolDownPlayer cdpl = new CoolDownPlayer(cds, player.getName());
                Main.getInstance().coolDown.put(player.getName(), cdpl);
            }
            for (ItemStack its : kit.getItens()) {
                player.getInventory().addItem(its);
            }
            if(!kit.getComandos().isEmpty() || !kit.getComandos().contains("nulo")){
                for(String cmds : kit.getComandos()){
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),cmds.replace("%jogador%",player.getName()));
                }
            }
        } else {
            player.sendMessage(Mensagens.espaço_insuficiente);
        }
    }

}
