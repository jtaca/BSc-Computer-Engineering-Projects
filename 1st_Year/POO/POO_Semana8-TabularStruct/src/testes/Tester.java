package testes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import tabularinterface.Matrix;
import tabularinterface.TableToTextConverter;
import tabularinterface.TabularStructure;

public class Tester {
    public static void main(String[] args) {
    	
        int[][] m = {{1, 2, 3}, {4, 5, 6},{7,8,9}};
        TabularStructure matrix = new Matrix(m);
        String text = TableToTextConverter.toText(matrix, " ", false);
        System.out.println(text);
        
        List<Integer> inteiros = new LinkedList<Integer>();

        inteiros.add(1);
        inteiros.add(2);
        inteiros.add(3);

        Iterator<Integer> it = inteiros.iterator();
     	

        //Iterator<Integer> it = Iterator(inteiros);

        while (it.hasNext())
        	System.out.println(it.next());
        
        List<String> courseNames = new LinkedList <String> ();

        courseNames.add("IP");
        courseNames.add("POO");

        courseNames.add("PCD");
        while(!courseNames.isEmpty()) {
            //System.out.println(courseNames.poll());
        } 
 		

       // Movable m1 = new Carro();
        //Movable m2 = new Planeta();
        
        ArrayList<Movable> lista = new ArrayList<Movable>();

        lista.add(new Planeta());

        //lista.add(new Carro());
    }
    
}
