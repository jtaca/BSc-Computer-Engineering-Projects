import aguiaj.iscte.ColorImage;
import java.util.Random;

class Funçoes{ //Referente às Vinhetas

	static void copy(ColorImage img, Vinheta a){// Este procedimento copia a imgem dada como argumento para o parâmetro imgc 
		a.imgc = new ColorImage(img.getWidth(),img.getHeight());
		for(int x=0;x< a.imgc.getWidth(); x++){
			for(int y=0;y< a.imgc.getHeight();y++){
				a.imgc.setColor( x , y , img.getColor(x, y) );
			}
		}
		return;
	}	
	
	static 	void margem( Vinheta a){ // Este procedimento modifica uma das imagens que o objeto vinheta tem como parâmetro de modo a esta ficar com uma maoldura 
		a.img = new ColorImage(a.imgc.getWidth()+2*a.moldpix,a.imgc.getHeight()+2*a.moldpix);
		for(int x=0;x< a.img.getWidth(); x++){
			for(int y=0;y< a.img.getHeight();y++){
				if(x<a.imgc.getWidth()&&y<a.imgc.getHeight()){
					if(a.bw && a.imgc.getColor(x, y).getR()<a.imgc.getColor(x, y).getG()&&a.imgc.getColor(x, y).getR()<a.imgc.getColor(x, y).getB()){
						a.img.setColor( x + a.moldpix, y + a.moldpix , a.imgc.getColor(x, y).toGraytone());
					}else{
						a.img.setColor( x + a.moldpix, y + a.moldpix , a.imgc.getColor(x, y));
					}
				}
				if((x>=a.img.getWidth()-a.moldpix)||(y<a.moldpix)||(x<a.moldpix)||(y>=a.img.getHeight()-a.moldpix)){
					tipoMarg(x,y,a);
				}	
			}
		}
		return;
	}	
				
	private static void tipoMarg(int x, int y, Vinheta a){ // Este procedimento é um segmento da margem  que descreve o preenchimento da moldura
		if(a.moldtype==1){
			a.img.setColor(x,y, a.moldcol);
		}else if(a.moldtype==2){	
			if(x%(2*a.moldpix)==0){ // Neste caso x não representa a variavel x mas sim um valor que incrementa de modo a alternar os pixeis.
				fillSquare(a,a.img.getWidth()-a.moldpix,x);
				fillSquare(a,0,x);
				fillSquare(a,x,a.img.getWidth()-a.moldpix);
				fillSquare(a,x,0);
			}	
		}else if(a.moldtype==3)	{
			Random randomizer = new Random();
			if(randomizer.nextBoolean()){
				a.img.setColor(x,y, a.moldcol);	
			}
		}else{				
			a.moldtype = 0;
		}	
		return;
	}
	
	
	
	static void pb( Vinheta a){//Este procedimento faz com que a imagem fique a preto e branco ou a cores
			a.bw = !a.bw;
		/*for(int x = 0;x < a.imgc.getWidth(); x++){
			for(int y = 0;y < a.imgc.getHeight();y++){
				if(a.bw == true){
					a.img.setColor( x + a.moldpix, y + a.moldpix , a.imgc.getColor(x, y).toGraytone());
				}else{
					a.img.setColor( x + a.moldpix, y + a.moldpix , a.imgc.getColor(x, y));
				}
			}
		}*/
		return;
	}	
	
	
	private static void fillSquare(Vinheta a, int c, int b){// Este procedimento é um método auxiliar à construção da moldura ponteada
		for(int x=c; x<a.moldpix+c;x++){
			for(int y=b; y<a.moldpix+b;y++){
				if(x<a.img.getWidth()&&y<a.img.getHeight()){
					a.img.setColor(x,y,a.moldcol);	
				}
			}
		}
		return;
	}

}