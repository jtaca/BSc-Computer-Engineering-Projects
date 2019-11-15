package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Position;

public class Factory {
	
	public GameElement create(char object, Position position){
		GameElement newEl;
		switch (object) {
		case 'X':
			newEl = new Target(position);
			break;
			
		case 'O':
			newEl = new Hole(position);
			break;
		
		case '#':
			newEl = new Wall(position);
			break;
			
		case 'C':
			newEl = new Box(position);
			break;
			
		case 'b':
			newEl = new Battery(position); 
			break;
		
		case 'S':
			newEl = new BigStone(position);
			break;
			
		case 's':
			newEl = new SmallStone(position);
			break;
		
		case 'E':
			Player.instanceOf().setPosition(position);
			newEl = Player.instanceOf();
			break;

		default:
			newEl = new Floor(position);
			break;
			
		}
		return newEl;
	}	
	

}
