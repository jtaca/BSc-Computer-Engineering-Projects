package TiposDeDados;
public class ArvoreBinaria {

    private static class no {
        private Object item;
        private no esquerda;
        private no direita;
    }
    
    private no raiz = null;

    public void nova() {
        raiz = null;
    }

    public void cons(ArvoreBinaria esquerda, Object item, ArvoreBinaria direita) {
        raiz = new no();
        raiz.esquerda = esquerda.raiz;
        raiz.item = item;
        raiz.direita = direita.raiz;
    }

    public Object raiz() {
        if(vazia())
            throw new IllegalStateException("ArvoreBinaria.raiz(): árvore vazia!");

        return raiz.item;
    }

    public ArvoreBinaria esquerda() {
        if(vazia())
            throw new IllegalStateException("ArvoreBinaria.esquerda(): árvore vazia!");

        ArvoreBinaria t = new ArvoreBinaria();

        t.raiz = raiz.esquerda;

        return t;
    }

    public ArvoreBinaria direita() {
        if(vazia())
            throw new IllegalStateException("ArvoreBinaria.direita(): árvore vazia!");

        ArvoreBinaria t = new ArvoreBinaria();

        t.raiz = raiz.direita;

        return t;
    }

    public boolean vazia() {
        return raiz == null;
    }

    public boolean existe(Object item){
	return existe(raiz, item);
    }

    public int numeroDenos() {
	return numeroDenos(raiz);
    }

    public int profundidade() {
	return profundidade(raiz);
    }

    private static boolean existe(no raiz, Object item) {
        if(raiz == null)
            return false;
        else
            if(item.equals(raiz.item))
                return true;
            else
                return existe(raiz.esquerda, item) || existe(raiz.direita,item);
    }

    private static int numeroDenos(no raiz) {
	if(raiz == null) {
	    return 0;
	}
	return 1 + numeroDenos(raiz.esquerda) + numeroDenos(raiz.direita);
    }
    
    private static int profundidade(no raiz) {
	if(raiz == null) {
	    return 0;
	}
	return 1 + Math.max(profundidade(raiz.esquerda), profundidade(raiz.direita));
    }

    public static void main(String[] args) {
	System.out.println("arvore vazia");
        ArvoreBinaria vazia = new ArvoreBinaria();
	System.out.println("numero de nos: " + vazia.numeroDenos());
	System.out.println("Profundidade: " + vazia.profundidade());

        ArvoreBinaria esquerda_b = new ArvoreBinaria();
        esquerda_b.cons(vazia, new Integer(2), vazia);

        ArvoreBinaria esquerda_m = new ArvoreBinaria();
        esquerda_m.cons(esquerda_b, new Integer(3), vazia);

	System.out.println("< <<>, 2, <>>, 3, <> >");
	System.out.println("numero de nos: " + esquerda_m.numeroDenos());
	System.out.println("Profundidade: " + esquerda_m.profundidade());
        
        ArvoreBinaria direita = new ArvoreBinaria();
        direita.cons(vazia, new Integer(7), vazia);

        ArvoreBinaria t = new ArvoreBinaria();
        t.cons(esquerda_m, new Integer(6), direita);

	System.out.println("< < <<>, 2, <>>, 3, <> >, 6, <<>, 7, <>> >");
        System.out.println("3: " + t.existe(new Integer(3)));
        System.out.println("6: " + t.existe(new Integer(6)));
        System.out.println("7: " + t.existe(new Integer(7)));
        System.out.println("5: " + t.existe(new Integer(5)));
        System.out.println("2: " + t.existe(new Integer(2)));

	System.out.println("numero de nos: " + t.numeroDenos());
	System.out.println("Profundidade: " + t.profundidade());
    }
    
}