package Ex3;

import java.util.Comparator;

public class NumberComparator implements Comparator<Aluno> {
	public int compare(final Aluno one, final Aluno another){
	return one.getNumero()-another.getNumero(); }
}
