package TiposDeDados;
public interface Lista {
    public void nova();
    public void acr(Object item);
    public int comprimento();
    public void retirapos(int i);
    public Object elementopos(int i);
    public boolean existe(Object item);
    public boolean vazia();
}