package me.itz.kits.database.save;

import me.itz.kits.Main;
import me.itz.kits.cool.CoolDownKit;
import me.itz.kits.cool.CoolDownPlayer;
import me.itz.kits.objects.Kit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    //(player = iTzSqremoZz, cool = basico:412414321:vip:43424325:mvp:321412412)
    public static Connection con = Main.getInstance().connection.getConnection();

    public static void saveAll() {
        try {
            for (CoolDownPlayer cdp : Main.getInstance().coolDown.values()) {
                String kite = "";
                for (CoolDownKit cdk : Main.getInstance().coolDown.get(cdp.getPlayer()).getCoolDownKitList()) {
                    if (kite.isEmpty()) {
                        kite += cdk.getKit().getNome() + ":" + cdk.getTime();
                    } else {
                        kite += ":" + cdk.getKit().getNome() + ":" + cdk.getTime();
                    }
                }
                if (!hasPlayer(cdp.getPlayer())) {
                    save(cdp.getPlayer(), kite);
                } else {
                    delete(cdp.getPlayer());
                    save(cdp.getPlayer(), kite);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM kits");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                List<CoolDownKit> cdk = new ArrayList<>();
                for (int i = 0; i < rs.getString("cool").split(":").length; i++) {
                    long time = Long.valueOf(rs.getString("cool").split(":")[i + 1]);
                    Kit kit = Main.getInstance().kits.get(rs.getString("cool").split(":")[i]);
                    CoolDownKit c = new CoolDownKit(time, kit, rs.getString("player"));
                    cdk.add(c);
                    i++;
                }
                CoolDownPlayer cdp = new CoolDownPlayer(cdk, rs.getString("player"));
                Main.getInstance().coolDown.put(cdp.getPlayer(),cdp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void save(String player, String cool) {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO kits (player,cool) VALUES (?,?)");
            ps.setString(1, player);
            ps.setString(2, cool);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(String player) {
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM kits WHERE player = ?");
            ps.setString(1, player);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean hasPlayer(String player) {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM kits WHERE player = ?");
            ps.setString(1, player);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
