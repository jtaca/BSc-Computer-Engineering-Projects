package Ex4;

public abstract class Empresa implements Contribuinte{
	private int nif;

	public Empresa(int nif) {
		super();
		this.nif = nif;
	}
	
	@Override
	public int getNIF() {
		// TODO Auto-generated method stub
		return nif;
	}
	
	

}
