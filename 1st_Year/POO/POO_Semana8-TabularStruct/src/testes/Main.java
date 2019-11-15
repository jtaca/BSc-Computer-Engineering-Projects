package testes;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		
		/*Caixa <Integer> c1 = new Caixa <Integer> (1);
		/*Caixa <Integer> c2 = new Caixa <Integer>(new Integer(2));
		Caixa <Integer> c3 = new Caixa <Integer>(new Integer(3));
		
		System.out.println(c1.toString());
		System.out.println(c2.toString());
		System.out.println(c3.toString());
		
        FileOutputStream fos = new FileOutputStream("t.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeInt(12345);
        oos.writeObject("Today");
        //oos.writeObject();

        oos.close();*/
		
		 Scanner s = new Scanner(new File("texto.txt"));
		 String token = "";
		 while(s.hasNext()){
		     token += s.next();

		 }
		 System.out.print(token);
		
	}

}
