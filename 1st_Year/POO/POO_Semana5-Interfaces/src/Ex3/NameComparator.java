package Ex3;

import java.util.Comparator;

public class NameComparator implements Comparator<Aluno> {
		public int compare(final Aluno one, final Aluno another){
		return one.getNome().compareTo(another.getNome()); }
		
}
