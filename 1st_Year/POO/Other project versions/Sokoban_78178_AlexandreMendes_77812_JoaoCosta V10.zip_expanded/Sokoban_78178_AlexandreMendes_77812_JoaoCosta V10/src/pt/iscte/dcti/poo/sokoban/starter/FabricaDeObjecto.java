package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Position;

public class FabricaDeObjecto {
	
	public static AbstractObject createObject(char charact, Position p) {
		AbstractObject obj;
		switch (charact) {
		case 'X':
			obj = new Alvo(p);
			break;
			
		case 'O':
			obj = new Buraco(p);
			break;
		
		case '#':
			obj = new Parede(p);
			break;
			
		case 'C':
			obj = new Caixa(p);
			break;
			
		case 'b':
			obj = new Bateria(p, 50); // quanto carrega
			break;
		
		case 'S':
			obj = new BigStone(p);
			break;
			
		case 's':
			obj = new SmallStone(p);
			break;
		
		case 'E':
			Player.getPlayer().setPosition(p);
			obj = Player.getPlayer();
			break;

		default:
			obj = new Chao(p);
			break;
			
		}
		return obj;
	}

}
