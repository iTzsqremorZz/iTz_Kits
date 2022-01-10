package me.itz.kits.objects;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Kit {

    private String display;
    private String nome;
    private Categoria categoria;
    private String perm;
    private String semPermissao;
    private int slot;
    private ItemStack icon;
    private List<String> lore;
    private long tempo;
    private int visualizar_linhas;
    private int visualizar_voltar;
    private int visualizar_receber;
    private int visualizar_proximo;
    private int visualizar_anterior;
    private Integer[] visualizar_slots;
    private List<ItemStack> itens;
    private List<String> comandos;

    public Kit(String display, Categoria categoria, String perm, String semPermissao, int slot, ItemStack icon, List<String> lore, long tempo, int visualizar_linhas, int visualizar_voltar, int visualizar_receber, int visualizar_proximo, int visualizar_anterior, Integer[] visualizar_slots, List<ItemStack> itens, List<String> comandos) {
        this.display = display;
        this.categoria = categoria;
        this.perm = perm;
        this.semPermissao = semPermissao;
        this.slot = slot;
        this.icon = icon;
        this.lore = lore;
        this.tempo = tempo;
        this.visualizar_linhas = visualizar_linhas;
        this.visualizar_voltar = visualizar_voltar;
        this.visualizar_receber = visualizar_receber;
        this.visualizar_proximo = visualizar_proximo;
        this.visualizar_anterior = visualizar_anterior;
        this.visualizar_slots = visualizar_slots;
        this.itens = itens;
        this.comandos = comandos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getPerm() {
        return perm;
    }

    public void setPerm(String perm) {
        this.perm = perm;
    }

    public String getSemPermissao() {
        return semPermissao;
    }

    public void setSemPermissao(String semPermissao) {
        this.semPermissao = semPermissao;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
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

    public long getTempo() {
        return tempo;
    }

    public void setTempo(long tempo) {
        this.tempo = tempo;
    }

    public int getVisualizar_linhas() {
        return visualizar_linhas;
    }

    public void setVisualizar_linhas(int visualizar_linhas) {
        this.visualizar_linhas = visualizar_linhas;
    }

    public int getVisualizar_voltar() {
        return visualizar_voltar;
    }

    public void setVisualizar_voltar(int visualizar_voltar) {
        this.visualizar_voltar = visualizar_voltar;
    }

    public int getVisualizar_receber() {
        return visualizar_receber;
    }

    public void setVisualizar_receber(int visualizar_receber) {
        this.visualizar_receber = visualizar_receber;
    }

    public int getVisualizar_proximo() {
        return visualizar_proximo;
    }

    public void setVisualizar_proximo(int visualizar_proximo) {
        this.visualizar_proximo = visualizar_proximo;
    }

    public int getVisualizar_anterior() {
        return visualizar_anterior;
    }

    public void setVisualizar_anterior(int visualizar_anterior) {
        this.visualizar_anterior = visualizar_anterior;
    }

    public Integer[] getVisualizar_slots() {
        return visualizar_slots;
    }

    public void setVisualizar_slots(Integer[] visualizar_slots) {
        this.visualizar_slots = visualizar_slots;
    }

    public List<ItemStack> getItens() {
        return itens;
    }

    public void setItens(List<ItemStack> itens) {
        this.itens = itens;
    }

    public List<String> getComandos() {
        return comandos;
    }

    public void setComandos(List<String> comandos) {
        this.comandos = comandos;
    }
}
