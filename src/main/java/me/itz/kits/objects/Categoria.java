package me.itz.kits.objects;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Categoria {

    private String titulo;
    private int slot;
    private int linhas;
    private String nome;
    private ItemStack icon;
    private List<String> lore;
    private ItemStack voltar_item;
    private String voltar_nome;
    private int voltar_slot;

    public Categoria(String titulo, int slot, int linhas, String nome, ItemStack icon, List<String> lore, ItemStack voltar_item, String voltar_nome, int voltar_slot) {
        this.titulo = titulo;
        this.slot = slot;
        this.linhas = linhas;
        this.nome = nome;
        this.icon = icon;
        this.lore = lore;
        this.voltar_item = voltar_item;
        this.voltar_nome = voltar_nome;
        this.voltar_slot = voltar_slot;
    }


    public ItemStack getVoltar_item() {
        return voltar_item;
    }

    public void setVoltar_item(ItemStack voltar_item) {
        this.voltar_item = voltar_item;
    }

    public String getVoltar_nome() {
        return voltar_nome;
    }

    public void setVoltar_nome(String voltar_nome) {
        this.voltar_nome = voltar_nome;
    }

    public int getVoltar_slot() {
        return voltar_slot;
    }

    public void setVoltar_slot(int voltar_slot) {
        this.voltar_slot = voltar_slot;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getLinhas() {
        return linhas;
    }

    public void setLinhas(int linhas) {
        this.linhas = linhas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }
}
