package Ex2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Main {
public static void main(String[] args) {
Rational r1 = new Rational (2, 3);
Rational r2 = new Rational (3, 4);
Rational r3 = new Rational (5, 3);
Rational r4 = new Rational (1, 4);
List<Rational> numeros= new ArrayList<Rational>();
numeros.add(r2);
numeros.add(r1);
numeros.add(r3);
numeros.add(r4);
Collections.sort(numeros); 
System.out.println(numeros);
if (r1.compareTo(r2) > 0)
System.out.println(r1 + " is bigger than " + r2);
else
System.out.println(r1 + " is not bigger than " + r2);

}
 }