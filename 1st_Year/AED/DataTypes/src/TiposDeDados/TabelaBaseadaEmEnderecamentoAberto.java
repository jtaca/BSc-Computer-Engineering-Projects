package TiposDeDados;

public class TabelaBaseadaEmEnderecamentoAberto implements Tabela {

    private static final int TAMANHO_DA_TABELA = 10007;
    
    private static int hash(Object chave, int tamanhoDaTabela) {

	
        if(chave instanceof Long)
            return ((Long)chave).intValue()%tamanhoDaTabela;


        if(chave instanceof String) {
            int hashValue = 0;
            for(int i = 0; i != ((String)chave).length(); i++)
                hashValue = 37*hashValue + ((String)chave).charAt(i);
            hashValue = hashValue%tamanhoDaTabela;
            if(hashValue < 0)
                hashValue += tamanhoDaTabela;
            return hashValue;
        }

        // Função não definida para o tipo de chaves utilizado.
        return 0;
    }

    private static class Componente {
        private Item item;
        private boolean vazio;
    }

    private Componente[] itens = new Componente[TAMANHO_DA_TABELA];

    public TabelaBaseadaEmEnderecamentoAberto() {
        nova();
    }

    public void nova() {
        for(int i = 0; i != TAMANHO_DA_TABELA; ++i) {
            itens[i] = new Componente();
            itens[i].vazio = true;
        }
    }

    public boolean existe(Object chave) {
        boolean encontrei = false, stop = false;
        int h = hash(chave, TAMANHO_DA_TABELA);
        int númeroDeTentativas = 0;
        while(!stop && númeroDeTentativas != TAMANHO_DA_TABELA) {
            if(itens[h].vazio) // Posição vazia, logo não encontrado!
                stop = true;
            else if(chave.equals(itens[h].item.chave())) { // Encontrado!
                encontrei = true;
                stop = true;
            } else { // Colisão!
                ++númeroDeTentativas;
                h = seg(h, TAMANHO_DA_TABELA);
            }
        }
        return encontrei;
    }

    public void insere(Item item) {
        if(existe(item.chave()))
            throw new IllegalArgumentException("O item ja existe!");

        // Pesquisa //////////////////////////////////////////////
        boolean stop = false;
        int h = hash(item.chave(), TAMANHO_DA_TABELA);
        int númeroDeTentativas = 0;
        while(!stop && númeroDeTentativas != TAMANHO_DA_TABELA) {
            if(itens[h].vazio) // Posição livre!
                stop = true;
            else { // Colisão!
                ++númeroDeTentativas;
                h = seg(h, TAMANHO_DA_TABELA);
            }
        }

        // Inserção ////////////////////////////////////////////////////
        if(stop) {
            itens[h].item = item;
            itens[h].vazio = false;
        } else
            throw new IllegalStateException("Não foi possível inserir!");
    }

    public Item item(Object chave) {
        if(!existe(chave))
            throw new IllegalArgumentException("O item não existe!");

        boolean encontrei = false;
        int h = hash(chave, TAMANHO_DA_TABELA);
        while(!encontrei) {
            if(chave.equals(itens[h].item.chave())) { // Encontrado!
                encontrei = true;
            } else { // Colisão!
                h = seg(h, TAMANHO_DA_TABELA);
            }
            
        }

        return itens[h].item;
    }

    public void retira(Object chave) {
        // Pesquisa ////////////////////////////////////////////////////
        boolean encontrei = false, stop = false;
        int h = hash(chave, TAMANHO_DA_TABELA);
        int númeroDeTentativas = 0;
        while(!stop && númeroDeTentativas != TAMANHO_DA_TABELA) {
            if(itens[h].vazio) // Posição vazia, logo não encontrado!
                stop = true;
            else if(chave.equals(itens[h].item.chave())) { // Encontrado!
                encontrei = true;
                stop = true;
            } else { // Colisão!
                ++númeroDeTentativas;
                h = seg(h, TAMANHO_DA_TABELA);
            }
        }
        // Remoção ////////////////////////////////////////////////////
        if(encontrei) {
            itens[h].vazio = true;
            int j = seg(h, TAMANHO_DA_TABELA);
            while(!itens[j].vazio) {
                int aux = hash(itens[j].item.chave(), TAMANHO_DA_TABELA);
                if(estaEntre (aux, h, j, TAMANHO_DA_TABELA)) // Não muda!
                    j = seg(j, TAMANHO_DA_TABELA);
                else { // Muda de posição!
                    itens[h].item = itens[j].item;
                    itens[h].vazio = false;
                    itens[j].vazio = true;
                    h = j;
                    j = seg(j, TAMANHO_DA_TABELA);
                }
            }
        }
    }

    // Sequência de pesquisa: endereçamento linear
    private static int seg(int indice, int tableSize) {
        return (indice + 1)%tableSize;
    }

    private static boolean estaEntre(int indice,
                                     int limiteInferior,
                                     int limiteSuperior,
                                     int tableSize) {
        return (limiteInferior < limiteSuperior &&
                limiteInferior <indice &&indice <= limiteSuperior)

            || (limiteSuperior < limiteInferior &&
                (limiteInferior <indice &&indice < tableSize
                 ||indice <= limiteSuperior));
    }

    public static void main(String[] argumentos) {
        TabelaBaseadaEmEnderecamentoAberto t = new TabelaBaseadaEmEnderecamentoAberto();
        t.nova();
        t.insere(new Pessoa(10102241, "Ana"));
        t.insere(new Pessoa(12343809, "Bela"));
        t.insere(new Pessoa(11347893, "Manel"));
        t.insere(new Pessoa(9354430, "Xico"));
        t.insere(new Pessoa(12354430, "Vanessa"));

        System.out.println("-[ Consulta ]-------------------------------------");

        System.out.println(t.item(new Long(12354430)));
        System.out.println(t.item(new Long(9354430)));
        System.out.println(t.item(new Long(11347893)));
        System.out.println(t.item(new Long(12343809)));
        System.out.println(t.item(new Long(10102241)));

        t.retira(new Long(10102241));

        System.out.println("-[ Consulta depois de apagar o 10102241 (Ana) ]---");

        System.out.print("12354430: ");
	System.out.println(t.existe(new Long(12354430)) ? t.item(new Long(12354430)) : "x" );
        System.out.print("9354430:  ");
	System.out.println(t.existe(new Long(9354430))? t.item(new Long(9354430)): "x");
        System.out.print("11347893: ");
	System.out.println(t.existe(new Long(11347893))?t.item(new Long(11347893)):"x");
        System.out.print("12343809: ");
	System.out.println(t.existe(new Long(12343809))?t.item(new Long(12343809)):"x");
        System.out.print("10102241: ");
	System.out.println(t.existe(new Long(10102241))?t.item(new Long(10102241)):"x");

    }

}