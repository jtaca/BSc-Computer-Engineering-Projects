package TiposDeDados;
public class ListaLigada implements Lista {

    private static class no {
        private Object item;
        private no seguinte;
    };

    private no primeiro;
    private no Ultimo;
    private int comprimento;

    public void nova() {
        primeiro = null;
        Ultimo = null;
	comprimento = 0;
    }

    public void acr(Object item) {
	no aux = new no();
	aux.item = item;
	aux.seguinte = null;

	if(primeiro != null)
	    Ultimo.seguinte = aux;
	else
	    primeiro = aux;
	Ultimo = aux;

	++comprimento;
    }

    public int comprimento() {
    	return comprimento;
    }

    public Object elementopos(int i) {
	if(i <= 0 || i > comprimento())
	    throw new IllegalArgumentException("Fora dos limites!");

	int IndiceAtual = 1;  
	no posicao = primeiro; 
	while(IndiceAtual != i){ 
	    posicao = posicao.seguinte; 
	    IndiceAtual++;
	} 
	return posicao.item; 
    }

    public void retirapos(int i) {
	if(i <= 0 || i > comprimento())
	    throw new IllegalArgumentException("Fora dos limites!");

	int IndiceAtual = 1;
	no anterior = null;
	no posicao = primeiro;
	while(IndiceAtual != i) {
	    anterior = posicao;
	    posicao = posicao.seguinte;
	    IndiceAtual++; 
	}

	// (Continua…)
	// (Continuação…)

	if(anterior != null) {
	    anterior.seguinte = posicao.seguinte; 
	    if(posicao == Ultimo)
		Ultimo = anterior; 
	} else { 
	    primeiro =posicao.seguinte;
	    if(primeiro == null)
		Ultimo = null; 
	}

	--comprimento;
    }

    public boolean existe(Object item) {
	boolean encontrei = false;
	no aux = primeiro;
	while(aux != null && !encontrei) {
	    if(item.equals(aux.item))
		encontrei = true;
	    aux = aux.seguinte;
	}
	return encontrei;
    }

    public boolean vazia() {
	return primeiro == null; 
    }
}