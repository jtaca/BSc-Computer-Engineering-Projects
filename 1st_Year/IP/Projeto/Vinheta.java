import aguiaj.iscte.ColorImage;
import aguiaj.iscte.Color;

class Vinheta {
	int moldpix; // numero de pixeis na moldura
	int moldtype; // tipo de moldura
	Color moldcol; // cor da moldura
	ColorImage img; // Imagem da vinheta
	ColorImage imgc; // Cópia da imagem dada
	int height;
	int width;
	boolean bw; // Variavel que define se a imagem deve ser impressa a preto e
				// branco;
				// 1 => devolve a P/B; 0=> devolve a cores;
	// O significado dos valores da variavel moldtype.
	public static final int Sem_Moldura = 0;
	public static final int Linha = 1;
	public static final int Tracejado = 2;
	public static final int Desenhado = 3;

	// *******Construtores********//
	Vinheta(ColorImage img, int moldpix, int moldtype, Color moldcol) {
		this.moldpix = moldpix;
		this.moldtype = moldtype;
		this.moldcol = moldcol;
		Funçoes.copy(img, this);
		Funçoes.margem(this);
		this.height = getHeight();
		this.width = getWidth();
		this.bw = false; // predifine - se que a imagem é devolvida a cores
	}

	Vinheta(ColorImage img, int moldpix, int moldtype) {
		this(img, moldpix, moldtype, new Color(0, 0, 0)); // predifine - se que
															// a imagem é
															// devolvida a cores
	}

	// *********Métodos************//

	int getHeight() {
		return this.img.getHeight();
	}

	int getWidth() {
		return this.img.getWidth();
	}

	void setMoldCol(Color a) {
		this.moldcol = a;
		Funçoes.margem(this);

	}

	void setMoldCol2(int r, int g, int b) {

		this.moldcol = new Color(r, g, b);
		Funçoes.margem(this);

	}

	public String toString() {
		return "Height: " + this.img.getHeight() + " Width: " + this.img.getWidth();
	}

	void setMoldType(int moldtype) {// tipo 0: sem moldura; tipo 1:linha; tipo
									// 2:tracejado; tipo 3 desenhada;
		this.moldtype = moldtype;
		Funçoes.margem(this);
		return;
	}

	void setMoldPix(int moldpix) {
		this.moldpix = moldpix;
		Funçoes.margem(this);
		return;
	}

	void setBW() {
		Funçoes.pb(this);
		Funçoes.margem(this);
		return;
	}

	ColorImage getImg() {// Esta função devolve a imagem correspondente à
							// vinheta
		return this.img;
	}
}