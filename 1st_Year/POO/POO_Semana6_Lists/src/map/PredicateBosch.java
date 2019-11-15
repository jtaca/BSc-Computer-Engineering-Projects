package map;

import java.util.function.Predicate;

public class PredicateBosch implements Predicate<Eletrodomestico>{

	@Override
	public boolean test(Eletrodomestico t) {
		return t.getMarca().contains("Bosch");
	}
	

}
