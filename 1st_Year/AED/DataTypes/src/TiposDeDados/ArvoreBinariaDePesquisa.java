package TiposDeDados;
public class ArvoreBinariaDePesquisa {
    
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

    private Comparable raiz() {
        if(vazia())
            throw new IllegalStateException("ArvoreBinariaDePesquisa.raiz(): A¡rvore vazia!");
        
        return raiz.item;
    }
    
    private ArvoreBinariaDePesquisa esquerda() {
        if(vazia())
            throw new IllegalStateException("ArvoreBinariaDePesquisa.esquerda(): A¡rvore vazia!");
        
        ArvoreBinariaDePesquisa t = new ArvoreBinariaDePesquisa();
        
        t.raiz = raiz.esquerda;
        
        return t;
    }
    
    private ArvoreBinariaDePesquisa direita() {
        if(vazia())
            throw new IllegalStateException("ArvoreBinariaDePesquisa.direita(): A¡rvore vazia!");
        
        ArvoreBinariaDePesquisa t = new ArvoreBinariaDePesquisa();
        
        t.raiz = raiz.direita;
        
        return t;
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

    public Comparable item(Comparable item) {
        if(!existe(item))
            throw new IllegalStateException("O item nA£o existe!");

        return item(raiz, item);
    }
    
    private static Comparable item(no raiz, Comparable item) {
        if(item.compareTo(raiz.item) == 0)
            return raiz.item;
        else
            if(item.compareTo(raiz.item) < 0)
                return item(raiz.esquerda, item);
            else
                return item(raiz.direita, item);
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
                raiz.esquerda = insere(raiz.esquerda, item);
            } else {
                raiz.direita = insere(raiz.direita, item);
            }
            return raiz;
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

    public void retira(Comparable item) {
        raiz = retira(raiz, item);
    }

    private static no retira(no raiz, Comparable item) {
        if(raiz != null) {
            if(item.compareTo(raiz.item) < 0) {
                raiz.esquerda = retira(raiz.esquerda, item);
                return raiz;
            } else if(item.compareTo(raiz.item) > 0) {
                raiz.direita = retira(raiz.direita, item);
                return raiz;
            } else {
                if(raiz.esquerda == null)
                    return raiz.direita;
                else if(raiz.direita == null)
                    return raiz.esquerda;
                else {
                    raiz.item = itemMaisADireita(raiz.esquerda);
                    raiz.esquerda = retiranoMaisADireita(raiz.esquerda);
                    return raiz;
                }
            }
        }
        return null;
    }
    
    private static Comparable itemMaisADireita(no raiz) {
        if(raiz == null)
            throw new IllegalStateException("Arvore vazia!");
            
        if(raiz.direita == null)
            return raiz.item;
        else
            return itemMaisADireita(raiz.direita);
    }
    
    private static no retiranoMaisADireita(no raiz) {
        if(raiz == null)
            throw new IllegalStateException("Arvore vazia!");
        
        if(raiz.direita == null)
            return raiz.esquerda;
        else {
            raiz.direita = retiranoMaisADireita(raiz.direita);
            return raiz;
        }
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
    
    private void cons(ArvoreBinariaDePesquisa esquerda,
                      Comparable item,
                      ArvoreBinariaDePesquisa direita) {
        raiz = new no();
        raiz.esquerda = esquerda.raiz;
        raiz.item = item;
        raiz.direita = direita.raiz;
    }
    
    public static void main(String[] args) {
        ArvoreBinariaDePesquisa a = new ArvoreBinariaDePesquisa();
        
        a.insere(new Integer(3));
        a.insere(new Integer(2));
        a.insere(new Integer(7));
        a.insere(new Integer(5));
        a.insere(new Integer(1));
        a.insere(new Integer(9));
        
        System.out.println("<                      , 3,                             >");
        System.out.println("  <          , 2, <>  >     <          , 7,           >  ");
        System.out.println("    <<>,1,<>>                 <<>,5,<>>     <<>,9,<>>    ");
        System.out.println("2: " + a.existe(new Integer(2)));
        System.out.println("3: " + a.existe(new Integer(3)));
        System.out.println("7: " + a.existe(new Integer(7)));
        System.out.println("1: " + a.existe(new Integer(1)));
        System.out.println("5: " + a.existe(new Integer(5)));
        System.out.println("4: " + a.existe(new Integer(4)));
        System.out.println("9: " + a.existe(new Integer(9)));
        
        System.out.println("# nos: " + a.numeroDenos());
        System.out.println("Prof.: " + a.profundidade());
        System.out.println("Pesq.: " + a.NoDePesquisa());
        
        System.out.println("Travessia infixa");
        a.travessiaInfixa();
        System.out.println();
        System.out.println("Travessia prefixa");
        a.travessiaPrefixa();
        System.out.println();
        System.out.println("Travessia sufixa");
        a.travessiaSufixa();
        System.out.println();
        
        a.retira(new Integer(3));
        System.out.println("Apos retirar o 3...");

        System.out.println("2: " + a.existe(new Integer(2)));
        System.out.println("3: " + a.existe(new Integer(3)));
        System.out.println("7: " + a.existe(new Integer(7)));
        System.out.println("1: " + a.existe(new Integer(1)));
        System.out.println("5: " + a.existe(new Integer(5)));
        System.out.println("4: " + a.existe(new Integer(4)));
        System.out.println("9: " + a.existe(new Integer(9)));
                
        System.out.println("# nos: " + a.numeroDenos());
        System.out.println("Prof.: " + a.profundidade());
        System.out.println("Pesq.: " + a.NoDePesquisa());
        
        System.out.println("Travessia infixa");
        a.travessiaInfixa();
        System.out.println();
        System.out.println("Travessia prefixa");
        a.travessiaPrefixa();
        System.out.println();
    }
    
}
