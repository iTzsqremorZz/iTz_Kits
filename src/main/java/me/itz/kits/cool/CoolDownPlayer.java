package me.itz.kits.cool;

import java.util.List;

public class CoolDownPlayer {

    private List<CoolDownKit> coolDownKitList;
    private String player;

    public CoolDownPlayer(List<CoolDownKit> coolDownKitList, String player) {
        this.coolDownKitList = coolDownKitList;
        this.player = player;
    }

    public List<CoolDownKit> getCoolDownKitList() {
        return coolDownKitList;
    }

    public void setCoolDownKitList(List<CoolDownKit> coolDownKitList) {
        this.coolDownKitList = coolDownKitList;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void destroy(){
        this.player = null;
        this.coolDownKitList = null;
    }

}
