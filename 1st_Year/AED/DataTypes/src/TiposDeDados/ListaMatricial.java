package TiposDeDados;
public class ListaMatricial implements Lista {

    private static final int MAX = 100000;

    private Object[] itens = new Object[MAX];
    private int primeiro = -1;
    private int último = -1;

    private static int seguinte(int i) {
	return (i + 1)%MAX;
    }
    private static int anterior(int i) {
	if(i == 0) return MAX - 1; else return i - 1;
    }	



    public void nova() {
	primeiro = -1;
	último = -1;
    }

    public boolean vazia(){
	return primeiro == -1;
    }


    public void acr(Object n) {
	if(seguinte(último) == primeiro)
	    throw new IllegalStateException("acr: Lista cheia!");

	último = seguinte(último);
	itens[último] = n;
	if(primeiro == -1)
	    primeiro = 0;
    }


    public void retirapos(int i){
	int comp = comprimento();
	if(i <= 0 || i > comp)
	    throw new IllegalArgumentException("Fora dos limites");

	if(primeiro != último) { // Se há mais do que um elemento
	    int posicao = (primeiro + i - 1)%MAX;
	    if(comp - i < i) {
		for(int j = posicao ; j != último; j = seguinte(j))
		    itens[j] = itens[seguinte(j)];
		último = anterior(último);
	    } else {
		for(int j = posicao ; j != primeiro; j = anterior(j))
		    itens[j] = itens[anterior(j)];
		primeiro = seguinte(primeiro);
	    }
	} else{ // Se não há elementos ou só há um
	    primeiro = -1; último = -1;
	}
    }

    public int comprimento(){
	if(primeiro == -1)
	    return 0;
	else
	    if(primeiro <= último)
		return último - primeiro + 1;
	    else
		return MAX - primeiro + último + 1;
    }

    public Object elementopos(int i) {
	if(i <= 0 || i > comprimento())
	    throw new IllegalArgumentException("Fora dos limites");

	return itens[(primeiro + i - 1)%MAX];
    }


    public boolean existe(Object item) {
	if(primeiro != -1) {
	    boolean encontrei = item.equals(itens[primeiro]);
	    for(int j = primeiro; j != último && !encontrei; j = seguinte(j))
		if(item.equals(itens[seguinte(j)]))
		    encontrei = true;
	    return encontrei;
	} else
	    return false;
    }

}