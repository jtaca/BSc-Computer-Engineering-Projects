import aguiaj.iscte.ColorImage;

class Prancha {
	// numero de tiras da prancha
	ColorImage imgc;
	Vinheta[][] vinhetas; // --> numero de tiras vinhetas.length;; --> numero de
							// vinhetas por tira n vinhetas.length[n];;
	int espaço;
	private int maxcol;

	Prancha(int numTiras) {

		// this.vinhetas = new Vinheta [];

		this.espaço = 0;
		this.vinhetas = new Vinheta[numTiras][1];

		for (int x = 0; x < numTiras; x++) {

			vinhetas[x][0] = new Vinheta(new ColorImage(200, 100), 1, 1);

			// this.imgc = new ColorImage(this.getWidth(),img.getHeight());

		}

		// Image();

		draw();

	}

	void draw(){
		this.imgc = new ColorImage(this.getWidth(), this.getHeight());

		for (int i = 0; i < vinhetas.length; i++) {
			for (int y = 0; y < vinhetas[i][0].img.getHeight(); y++) {
				for (int x = 0; x < vinhetas[i][0].img.getWidth(); x++) {

					imgc.setColor(x, y + 102 * i, vinhetas[i][0].img.getColor(x, y));

				}
			}
		}
	}
	int getHeight() {

		int heightd = 0;
		int height = 0;

		for (int x = 0; x < vinhetas.length; x++) {

			for (int y = 0; y < vinhetas[x].length; y++) {

				if (heightd < vinhetas[x][y].getHeight()) {

					heightd = Math.max(heightd, vinhetas[x][y].getHeight());

				}

			}

			height += heightd;

		}
		height += (vinhetas.length + 1) * espaço;
		return height;
	}

	int getWidth() {

		int widthd = 0;
		int width = 0;

		for (int x = 0; x < vinhetas.length; x++) {
			for (int y = 0; y < vinhetas[x].length; y++) {

				if (widthd < vinhetas[x][y].getWidth()) {

					widthd = Math.max(widthd, vinhetas[x][y].getWidth());
					this.maxcol = x;

				}

			}
		}
		width += widthd;
		width += (vinhetas[maxcol].length + 1) * espaço;
		return width;
	}
	
	public Vinheta[][] ola(int a){
		Vinheta[][] b = new Vinheta[a][this.maxcol];
		for (int x = 0; x < a; x++) {
			for (int y = 0; y < maxcol; y++) {
				b[x][y] = vinhetas[x][y];
			}
		}
		return b;
	}
	
	public Vinheta[][] hihi(){
		Vinheta[][] b = new Vinheta[1][this.maxcol];
		b[0][0]=vinhetas[0][1];
		return b;
	}

	void setNumberOfTiras(int a) {
		Vinheta[][] b = new Vinheta[a][this.maxcol];
		for (int x = 0; x < a; x++) {
			for (int y = 0; y < maxcol; y++) {
				b[x][y] = vinhetas[x][y];
			}
		}
		vinhetas = b;
		//draw();
		
		/*
		 * if(a<vinhetas.length){ vinhetas [vinhetas.length -1]=null;
		 * 
		 * }
		 * 
		 * while(a>vinhetas.length){
		 * 
		 * 
		 * 
		 * for(int x=0; x<vinhetas.length; x++){
		 * 
		 * for(int y=0; y<vinhetas[x].length; y++){
		 * 
		 * b[x][y]=vinhetas[x][y];
		 * 
		 * } }
		 * 
		 * Vinheta[][] vinhetas = new Vinheta[b.length][this.maxcol];
		 * vinhetas[b.length-1][0] = new Vinheta(new ColorImage(200,100), 1 ,
		 * 1);
		 * 
		 * 
		 * } return;
		 */
	}

	void Image() {
		this.imgc = new ColorImage(this.getWidth() + (vinhetas[this.maxcol].length + 1) * espaço,
				this.getHeight() + (vinhetas.length + 1) * espaço); // Nunca vai
																	// ter o
																	// espaço
																	// aqui

		for (int ey = 0; ey < imgc.getHeight(); ey++) {
			for (int ex = 0; ex < imgc.getWidth(); ex++) {

				for (int i = 0; i < vinhetas.length; i++) {
					for (int j = 0; j < vinhetas[i].length; j++) {

						for (int y = 0; y < vinhetas[i][j].img.getHeight(); y++) {
							for (int x = 0; x < vinhetas[i][j].img.getWidth(); x++) {

								imgc.setColor(ex + espaço * i, ey + espaço * j, vinhetas[i][j].img.getColor(x, y));

							}
						}
					}
				}
			}
		}

	}

}