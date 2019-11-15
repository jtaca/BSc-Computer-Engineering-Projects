package TiposDeDados;
public class TabelaBaseadaEmEncadeamentoDirecto implements Tabela {

    private static int TAMANHO_DA_TABELA = 10007;

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

    private Lista[] itens = new Lista[TAMANHO_DA_TABELA];

    public TabelaBaseadaEmEncadeamentoDirecto() {
        nova();
    }

    public void nova() {
        for(int i = 0; i != TAMANHO_DA_TABELA; ++i) {
            itens[i] = new ListaLigada();
            itens[i].nova();
        }
    }

    public boolean existe(Object chave) {
	
        int h = hash(chave, TAMANHO_DA_TABELA);

        int i = 0;
        while(i != itens[h].comprimento()
              && !((Item)(itens[h].elementopos(i+1))).chave().equals(chave))
            ++i;
        return i != itens[h].comprimento();
    }

    public void insere(Item item) {
        if(existe(item.chave()))
            throw new IllegalArgumentException("Chave já existente!");

	int h = hash(item.chave(), TAMANHO_DA_TABELA);

        itens[h].acr(item);
    }

    public void retira(Object chave) {
        int h = hash(chave, TAMANHO_DA_TABELA);

        int i = 0;
        while(i != itens[h].comprimento()
              && !((Item)(itens[h].elementopos(i+1))).chave().equals(chave))
            ++i;

        if(i != itens[h].comprimento())
            itens[h].retirapos(i+1);
    }

    public Item item(Object chave) {
        if(!existe(chave))
            throw new IllegalArgumentException("Não há nenhum item com esta chave!");

        int h = hash(chave, TAMANHO_DA_TABELA);

        int i = 0;
        while(!((Item)(itens[h].elementopos(i+1))).chave().equals(chave))
            ++i;

        return (Item)itens[hash(chave, TAMANHO_DA_TABELA)].elementopos(i + 1);
    }

    public void histograma() {
        for(int i = 0; i != TAMANHO_DA_TABELA; ++i) {
            if(itens[i].comprimento() != 0) {
                System.out.print(i + ": ");
                for(int j = 0; j != itens[i].comprimento(); ++j)
                    System.out.print("x");
                System.out.println();
            }
        }
    }

    public static void main(String[] argumentos) {
        TabelaBaseadaEmEncadeamentoDirecto t = new TabelaBaseadaEmEncadeamentoDirecto();

        t.nova();

        t.insere(new Pessoa(10102241, "Ana"));
        t.insere(new Pessoa(12343809, "Bela"));
        t.insere(new Pessoa(11347893, "Manel"));
        t.insere(new Pessoa(9354430, "Xico"));
        t.insere(new Pessoa(12354430, "Vanessa"));
        t.insere(new Pessoa(9999998, "David"));
        t.insere(new Pessoa(9999999, "Zeca"));

        System.out.println(t.item(new Long(10102241)));
        System.out.println(t.item(new Long(12343809)));
        System.out.println(t.item(new Long(11347893)));
        System.out.println(t.item(new Long(9354430)));
        System.out.println(t.item(new Long(12354430)));
        System.out.println(t.item(new Long(9999999)));
        System.out.println(t.item(new Long(9999998)));

        t.histograma();
    }

}