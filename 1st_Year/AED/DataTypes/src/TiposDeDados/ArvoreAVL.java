package TiposDeDados;
public class ArvoreAVL {

    private static class no {
        private Comparable item;
        private no esquerda;
        private no direita;
    }

    private no raiz = null;

    public void nova() {
        raiz = null;
    }
    
    public boolean vazia() {
        return raiz == null;
    }

    public int numeroDenos() {
        return numeroDenos(raiz);
    }

    private static int numeroDenos(no raiz) {
        if(raiz == null) {
            return 0;
        }
        return 1 + numeroDenos(raiz.esquerda) + numeroDenos(raiz.direita);
    }
    
    public int profundidade() {
        return profundidade(raiz);
    }

    private static int profundidade(no raiz) {
        if(raiz == null) {
            return 0;
        }
        return 1 + Math.max(profundidade(raiz.esquerda), profundidade(raiz.direita));
    }

    public boolean NoDePesquisa() {
        return NoDePesquisa(raiz);
    }

    private static boolean NoDePesquisa(no raiz) {
        if(raiz == null) {
            return true;
        }
        return NoDePesquisa(raiz.esquerda) && NoDePesquisa(raiz.direita)
            && NoMenorQue(raiz.esquerda, raiz.item) && NoMaiorQue(raiz.direita, raiz.item);
    }
    
    
    private static boolean NoMenorQue(no raiz, Comparable item) {
        if(raiz == null) {
            return true;
        }
        return raiz.item.compareTo(item) <= 0
            && NoMenorQue(raiz.esquerda, item) && NoMenorQue(raiz.direita, item);
    }
    
    private static boolean NoMaiorQue(no raiz, Comparable item) {
        if(raiz == null) {
            return true;
        }
        return raiz.item.compareTo(item) > 0
            && NoMaiorQue(raiz.esquerda, item) && NoMaiorQue(raiz.direita, item);
    }

    public boolean NoEquilibrada() {
        return NoEquilibrada(raiz);
    }

    private static boolean NoEquilibrada(no raiz) {
        if(raiz == null) {
            return true;
        } else {
            return NoEquilibrada(raiz.esquerda) && NoEquilibrada(raiz.direita)
                && (Math.abs(profundidade(raiz.esquerda) - profundidade(raiz.direita)) <= 1);
        }
    }

    public void travessiaInfixa() {
        travessiaInfixa(raiz);
    }

    private static void travessiaInfixa(no raiz) {
        if(raiz != null) {
            travessiaInfixa(raiz.esquerda);
            System.out.print(" " + raiz.item);
            travessiaInfixa(raiz.direita);
        }
    }
    
    public void travessiaPrefixa() {
        travessiaPrefixa(raiz);
    }

    private static void travessiaPrefixa(no raiz) {
        if(raiz != null) {
            System.out.print(" " + raiz.item);
            travessiaPrefixa(raiz.esquerda);
            travessiaPrefixa(raiz.direita);
        }
    }

    public void travessiaSufixa() {
        travessiaSufixa(raiz);
    }

    private static void travessiaSufixa(no raiz) {
        if(raiz != null) {
            travessiaSufixa(raiz.esquerda);
            travessiaSufixa(raiz.direita);
            System.out.print(" " + raiz.item);
        }
    }

    public boolean existe(Comparable item){
        return existe(raiz, item);
    }

    private static boolean existe(no raiz, Comparable item) {
        System.out.print(".");
        if(raiz == null)
            return false;
        else
            if(item.compareTo(raiz.item) == 0)
                return true;
            else
                if(item.compareTo(raiz.item) < 0)
                    return existe(raiz.esquerda, item);
                else
                    return existe(raiz.direita, item);
    }

    public void insere(Comparable item) {
        raiz = insere(raiz, item);
    }

    private static no insere(no raiz, Comparable item) {

        if(raiz == null) {
            no aux = new no();
            aux.item = item;
            return aux;
        } else {
            if(item.compareTo(raiz.item) < 0) {

                raiz.esquerda = insere(raiz.esquerda, item); // INSERÇÃO

                if(profundidade(raiz.esquerda) - profundidade(raiz.direita) == 2) {

		    // DESEQUILIBRIO EXTERNO
                    if(item.compareTo(raiz.esquerda.item) <= 0) {
                        raiz = rodaLadoEsquerdo(raiz);
                    } else { // DESEQUILIBRIO INTERNO
                        raiz.esquerda = rodaLadoDireito(raiz.esquerda);
                        raiz = rodaLadoEsquerdo(raiz);
                    }
                }
            } else {
                raiz.direita = insere(raiz.direita, item); // INSERÇÃO

                if(profundidade(raiz.direita) - profundidade(raiz.esquerda) == 2) {

		    // DESEQUILIBRIO EXTERNO
                    if(item.compareTo(raiz.direita.item) > 0) {
                        raiz = rodaLadoDireito(raiz);
                    } else { // DESEQUILIBRIO INTERNO
                        raiz.direita = rodaLadoEsquerdo(raiz.direita);
                        raiz = rodaLadoDireito(raiz);
                    }
                }
            }
            return raiz;
        }
    }

    private static no rodaLadoEsquerdo(no raiz) {
        if(raiz == null)
            throw new IllegalStateException("rodaLadoEsquerdo(): Arvore vazia!");
        if(raiz.esquerda == null)
            throw new IllegalStateException("rodaLadoEsquerdo(): Sub-Arvore esquerda vazia!");

        no novaRaiz = raiz.esquerda;
        raiz.esquerda = novaRaiz.direita;
        novaRaiz.direita = raiz;
        
        return novaRaiz;
    }

    private static no rodaLadoDireito(no raiz) {
        if(raiz == null)
            throw new IllegalStateException("rodaLadoDireito(): Arvore vazia!");
        if(raiz.direita == null)
            throw new IllegalStateException("rodaLadoDireito(): Sub-Arvore direita vazia!");

        no novaRaiz = raiz.direita;
        raiz.direita = novaRaiz.esquerda;
        novaRaiz.esquerda = raiz;

        return novaRaiz;
    }

    public void retira(Comparable item) {
        raiz = retira(raiz, item);      
    }

    private static no retira(no raiz, Comparable item) {
        if(raiz != null) {

            if(item.compareTo(raiz.item) < 0) {

                raiz.esquerda = retira(raiz.esquerda, item);

                if(!NoEquilibrada(raiz)) {
                    if(profundidade(raiz.direita.esquerda) > profundidade(raiz.direita.direita)) {
                        raiz.direita = rodaLadoEsquerdo(raiz.direita);
                    }
                    raiz = rodaLadoDireito(raiz);
                }
                return raiz;
            } else if(item.compareTo(raiz.item) > 0) {
                raiz.direita = retira(raiz.direita, item);
                if(!NoEquilibrada(raiz)) {
                    if(profundidade(raiz.esquerda.direita) > profundidade(raiz.esquerda.esquerda)) {
                        raiz.esquerda = rodaLadoDireito(raiz.esquerda);
                    }
                    raiz = rodaLadoEsquerdo(raiz);
                }
                return raiz;
            } else {
                if(raiz.esquerda == null)
                    return raiz.direita;
                else if(raiz.direita == null)
                    return raiz.esquerda;
                else {
                    raiz.item = itemMaisÀDireita(raiz.esquerda);
                    raiz.esquerda = retiranoMaisÀDireita(raiz.esquerda);

                    if(!NoEquilibrada(raiz)) {
                        if(profundidade(raiz.direita.esquerda) > profundidade(raiz.direita.direita)) {
                            raiz.direita = rodaLadoEsquerdo(raiz.direita);
                        }
                        raiz = rodaLadoDireito(raiz);
                    }
                    return raiz;
                }
            }
        }
        return null;
    }
    
    private static Comparable itemMaisÀDireita(no raiz) {
        if(raiz == null)
            throw new IllegalStateException("Arvore vazia!");
            
        if(raiz.direita == null)
            return raiz.item;
        else
            return itemMaisÀDireita(raiz.direita);
    }
    
    private static no retiranoMaisÀDireita(no raiz) {
        if(raiz == null)
            throw new IllegalStateException("Arvore vazia!");
        
        if(raiz.direita == null)
            return raiz.esquerda;
        else {
            raiz.direita = retiranoMaisÀDireita(raiz.direita);
            return raiz;
        }
    }
    
    private Comparable raiz() {
        if(vazia())
            throw new IllegalStateException("raiz(): Arvore vazia!");

        return raiz.item;
    }

    private ArvoreAVL esquerda() {
        if(vazia())
            throw new IllegalStateException("esquerda(): Arvore vazia!");

        ArvoreAVL t = new ArvoreAVL();

        t.raiz = raiz.esquerda;

        return t;
    }

    private ArvoreAVL direita() {
        if(vazia())
            throw new IllegalStateException("direita(): Arvore vazia!");

        ArvoreAVL t = new ArvoreAVL();

        t.raiz = raiz.direita;

        return t;
    }

    public static void main(String[] args) {
        ArvoreAVL a = new ArvoreAVL();
        a.insere(new Integer(5));
        System.out.println("Eq.  : " + a.NoEquilibrada());
        System.out.println("Prof.: " + a.profundidade());
        System.out.print("Travessia prefixa: ");
        a.travessiaPrefixa();
        System.out.println();

        a.insere(new Integer(2));
        System.out.println("Eq.  : " + a.NoEquilibrada());
        System.out.println("Prof.: " + a.profundidade());
        System.out.print("Travessia prefixa: ");
        a.travessiaPrefixa();
        System.out.println();

        a.insere(new Integer(3));
        System.out.println("Eq.  : " + a.NoEquilibrada());
        System.out.println("Prof.: " + a.profundidade());
        System.out.print("Travessia prefixa: ");
        a.travessiaPrefixa();
        System.out.println();

        a.insere(new Integer(1));
        System.out.println("Eq.  : " + a.NoEquilibrada());
        System.out.println("Prof.: " + a.profundidade());
        System.out.print("Travessia prefixa: ");
        a.travessiaPrefixa();
        System.out.println();

        a.insere(new Integer(0));
        System.out.println("Eq.  : " + a.NoEquilibrada());
        System.out.println("Prof.: " + a.profundidade());
        System.out.print("Travessia prefixa: ");
        a.travessiaPrefixa();
        System.out.println();

        System.out.print("2");
        System.out.println(a.existe(new Integer(2)));
        System.out.print("3");
        System.out.println(a.existe(new Integer(3)));
        System.out.print("7");
        System.out.println(a.existe(new Integer(7)));
        System.out.print("1");
        System.out.println(a.existe(new Integer(1)));
        System.out.print("5");
        System.out.println(a.existe(new Integer(5)));
        System.out.print("0");
        System.out.println(a.existe(new Integer(0)));

        ArvoreAVL b = new ArvoreAVL();

        b.insere(10);
        b.insere(5);
        b.insere(15);
        b.insere(0);
        b.insere(20);
        b.insere(6);
        b.insere(7);

        System.out.println("# nos: " + b.numeroDenos());
        System.out.println("Prof.: " + b.profundidade());
        System.out.println("Pesq.: " + b.NoDePesquisa());
        System.out.println("Eq.  : " + b.NoEquilibrada());
        b.travessiaInfixa();
        System.out.println();

        b.retira(20);

        System.out.println("A apagar o 20...");
        System.out.println("# nos: " + b.numeroDenos());
        System.out.println("Prof.: " + b.profundidade());
        System.out.println("Pesq.: " + b.NoDePesquisa());
        System.out.println("Eq.  : " + b.NoEquilibrada());
        b.travessiaInfixa();
        System.out.println();

        b.retira(6);

        System.out.println("A apagar o 6...");
        System.out.println("# nos: " + b.numeroDenos());
        System.out.println("Prof.: " + b.profundidade());
        System.out.println("Pesq.: " + b.NoDePesquisa());
        System.out.println("Eq.  : " + b.NoEquilibrada());
        b.travessiaInfixa();
        System.out.println();

        b.retira(5);

        System.out.println("A apagar o 5...");
        System.out.println("# nos: " + b.numeroDenos());
        System.out.println("Prof.: " + b.profundidade());
        System.out.println("Pesq.: " + b.NoDePesquisa());
        System.out.println("Eq.  : " + b.NoEquilibrada());
        b.travessiaInfixa();
        System.out.println();

        ArvoreAVL c = new ArvoreAVL();

        c.insere(10);
        c.insere(3);
        c.insere(21);
        c.insere(2);
        c.insere(8);
        c.insere(17);
        c.insere(32);
        c.insere(9);
        c.insere(1);
        c.insere(7);
        c.insere(14);
        c.insere(6);
        c.insere(18);
        c.insere(28);
        c.insere(41);
        c.insere(16);
        System.out.println("# nos: " + c.numeroDenos());
        System.out.println("Prof.: " + c.profundidade());
        System.out.println("Pesq.: " + c.NoDePesquisa());
        System.out.println("Eq.  : " + c.NoEquilibrada());
        c.travessiaInfixa();
        System.out.println();

        // c.retira(28);
        // c.retira(41);

        // c.retira(10);
        // c.retira(9);
        // c.retira(8);
        // c.retira(17);

        // c.retira(9);

        c.retira(1);
        c.retira(9);

        System.out.println("# nos: " + c.numeroDenos());
        System.out.println("Prof.: " + c.profundidade());
        System.out.println("Pesq.: " + c.NoDePesquisa());
        System.out.println("Eq.  : " + c.NoEquilibrada());
        c.travessiaInfixa();
        System.out.println();
        c.travessiaInfixa();
        System.out.println();
        c.travessiaInfixa();
        System.out.println();
        c.travessiaInfixa();
        System.out.println();
        c.travessiaInfixa();
    }

}
