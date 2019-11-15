package Ex4;

public class Pessoa implements Contribuinte{
	private int nif = 0;
	
	public Pessoa(int nif) {
		super();
		this.nif = nif;
	}

	@Override
	public int getNIF() {
		// TODO Auto-generated method stub
		return nif;
	}

}
