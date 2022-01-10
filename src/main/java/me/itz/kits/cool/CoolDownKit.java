package me.itz.kits.cool;

import me.itz.kits.objects.Kit;

public class CoolDownKit {

    private long time;
    private Kit kit;
    private String player;

    public CoolDownKit(long time, Kit kit, String player) {
        this.time = time;
        this.kit = kit;
        this.player = player;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Kit getKit() {
        return kit;
    }

    public void setKit(Kit kit) {
        this.kit = kit;
    }

    public void destroy() {
        this.time = 0L;
        this.kit = null;
        this.player = null;
    }
}