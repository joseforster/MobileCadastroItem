package model;

public class ItemModel {
    private int Id;
    private String Descricao;
    private int Quantidade;

    public ItemModel(String descricao, int quantidade) {
        Descricao = descricao;
        Quantidade = quantidade;
    }

    public ItemModel(int id, String descricao, int quantidade) {
        Id = id;
        Descricao = descricao;
        Quantidade = quantidade;
    }

    public ItemModel() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public int getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(int quantidade) {
        Quantidade = quantidade;
    }
}
