package TiposDeDados;

public class HeapBinario {

    private static final int MAX = 100000;

    private int numeroDeElementos = 0;
    private Comparable[] itens = new Comparable[MAX+1];

    public void inicial() {
        numeroDeElementos = 0;
    }

    public boolean estaVazio() {
        return numeroDeElementos == 0;
    }

    public Comparable menor() {
        if(estaVazio())
            throw new IllegalStateException("menor(): heap vazio!");

        return itens[1];
    }

    public void insere(Comparable item) {
        if(numeroDeElementos == MAX)
            throw new IllegalStateException("insere(): heap cheio!");

        int i = numeroDeElementos + 1;
        while(i > 1 && itens[i/2].compareTo(item) > 0) {
            itens[i] = itens[i/2];
            i = i/2;
        }

        itens[i] = item;

        ++numeroDeElementos;
    }

    public void retiraMenor() {
        if(estaVazio())
            throw new IllegalStateException("retiraMenor(): heap vazio!");
        
        boolean encontreiOLocalOndeColocarOÚltimo = false;
        
        int i = 1;
        while(i*2 < numeroDeElementos && !encontreiOLocalOndeColocarOÚltimo ){

            // Qual é o menor descendente?
            int menorDescendente = i*2;
            if(menorDescendente != numeroDeElementos - 1
               && itens[menorDescendente + 1].compareTo(itens[menorDescendente]) < 0)
                menorDescendente = menorDescendente + 1;
            
            // Se o ultimo elemento da arvore é menor que o menor descendente...
            if(itens[numeroDeElementos].compareTo(itens[menorDescendente]) < 0)
                encontreiOLocalOndeColocarOÚltimo = true;
            else {
                itens[i] =itens[menorDescendente];
                i = menorDescendente;
            }

        }

        itens[i] = itens[numeroDeElementos];
        --numeroDeElementos;
    }

    public static void main(String[] argumentos) {
        HeapBinario h = new HeapBinario();

        h.insere(new Integer(3));
        h.insere(new Integer(5));
        h.insere(new Integer(23));
        h.insere(new Integer(4));
        h.insere(new Integer(32));
        h.insere(new Integer(7));
        h.insere(new Integer(8));

        while(!h.estaVazio()) {
            System.out.println(h.menor());
            h.retiraMenor();
        }
    }
    
}