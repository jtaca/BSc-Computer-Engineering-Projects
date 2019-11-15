package Ex3;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Main {
public static void main(String[] args) {
List<Aluno> listaDeAlunos = new ArrayList<Aluno>();
Aluno aluno = new Aluno("José Silva", 45678);
listaDeAlunos.add(aluno);
aluno = new Aluno("Manuela Correia", 34567);
listaDeAlunos.add(aluno);
aluno = new Aluno("Ana Moreira", 23456);
listaDeAlunos.add(aluno);
aluno = new Aluno("Pedro Cipriano", 12345);
listaDeAlunos.add(aluno);
System.out.println(listaDeAlunos);

Collections.sort(listaDeAlunos, new NumberComparator());
System.out.println(listaDeAlunos);
Collections.sort(listaDeAlunos, new NameComparator());
System.out.println(listaDeAlunos);
}
 }