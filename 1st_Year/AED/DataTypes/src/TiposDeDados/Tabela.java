package TiposDeDados;

public interface Tabela {

    public void nova();

    public boolean existe(Object chave);

    public void insere(Item item);
    public void retira(Object chave);
    public Item item(Object chave); // Consulta

}