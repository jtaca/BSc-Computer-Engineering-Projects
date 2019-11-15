package pt.iscte.dcti.poo.sokoban.starter;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Position;

/**
 * The Class SokobanGame.
 * @author Alexandre Mendes e Joao Costa
 * @version 7
 */
public class SokobanGame implements Observer {
	
	/** The Constant numeroTotalDeNiveis. */
	private static final int numeroTotalDeNiveis = 4;
	
	/** The Constant NumberOfResultsToSave. */
	private static final int NumberOfResultsToSave = 10;
	
	/** The Constant GAME. */
	private static final SokobanGame INSTANCE = new SokobanGame();
 	
	/** The level. */
	private int level;
	
	private int numeroDeObjectivosPorConcluir;
	
	/** The objects. */
	private ArrayList<AbstractObject> objects;
	
	/**
	 * Instantiates a new sokoban game.
	 */
	private SokobanGame(){ // Construtor
		
		ImageMatrixGUI.getInstance().setName("Sokoban");
		level = 0;
		makeLevel();
	} // Fim do construtor
	
	/**
	 * Gets the game.
	 *
	 * @return the game
	 */
	public static SokobanGame getGame() {
		return INSTANCE;
	}
	
	/**
	 * Builds the level.
	 */
	private void makeLevel() { // Faz o nivel de acordo com o atributo nivel
		//if(objects != null) // Se o objects ja existirem, ou seja a tela ja foi pintada logo
		ImageMatrixGUI.getInstance().clearImages(); // Dar clear na tela
		Player.getPlayer().resetStats(); // reset nos stats e retirar observadores existentes
		//Fazer o level em si
		objects = new ArrayList<AbstractObject>();
		ArrayList<ImageTile> tiles = mapBuilder(makeFileOfLevel());
		ImageMatrixGUI.getInstance().setStatusMessage(statusMessage());
		ImageMatrixGUI.getInstance().addImages(tiles);
		ImageMatrixGUI.getInstance().update();
	} // Fim da funcao makeLevel()
	
	/**
	 * Gets the level.
	 *
	 * @return the level
	 */
	public int getLevel() {
		return level;
	} // Fim da funcao getLevel()
	
	/**
	 * Change to the next level.
	 */
	
	private void nextLevel() { // Passa para o proximo level, se ja nao existe mais niveis, entao o jogo foi ganho e volta para o primeiro mapa;
		if(level < numeroTotalDeNiveis - 1) {
			System.out.println("Next Level!");
		} else {
			System.out.println("You Won!");
		}
		saveScore(Player.getPlayer().getMovimentos() + 1, level);
		level = (level + 1) % numeroTotalDeNiveis; // Quando chegar ao ultimo nivel, voltar ao primeiro e por ai em diante;
		makeLevel();
	} // Fim da funcao nextLevel()
	
	/**
	 * Save score.
	 *
	 * @param movimentos the moviments to be saved
	 * @param level int of the level
	 */
	private void saveScore(int movimentos, int level) {
		Resultado.save("Scores/score_level" + level + ".dat", new Resultado(movimentos), NumberOfResultsToSave);
	}
	
	/**
	 * Make file of level.
	 *
	 * @return the file
	 */
	private File makeFileOfLevel() { // Buscar um ficheiro com o level que esta como atributo no jogo
		return new File("levels/level" + level + ".txt");
	} // Fim da funcao makeFileOfLevel()
	
	/**
	 * Status message.
	 *
	 * @return the string
	 */
	public String statusMessage() { // Barrinha que diz qual o level, movimentos e bateria restante
		return "Level: " + getLevel() + " Movimentos: " + Player.getPlayer().getMovimentos() + " Bateria: " + Player.getPlayer().getBateria();
	} // Fim da funcao statusMessage()
	
	/**
	 * Object in position.
	 *
	 * @param proprioObj object that we dont want to consider, in most cases the object that used such function
	 * @param position position we want to check which object is in there
	 * @param game the SokobanGame
	 * @return Returns a abstract Object or null if can't find any object in the position required.
	 */
	public static AbstractObject objectInPosition(AbstractObject proprioObj, Position position) { // Verifica qual o objecto em certa posi�ao;
		for(AbstractObject obj : getGame().getObjects()) {
			if(obj.getPosition().equals(position) && obj.getLevel() != 0 && !obj.equals(proprioObj)) {
				return obj;
			}
		}
		return null;
	} // Fim da funcao objectInPosition(Position position, SokobanGame game)
	
	/**
	 * Check is finish.
	 *
	 * @return true, if successful
	 */
	private void checkIsFinish(ObjectiveObject obj) { // Verifica se os alvos estao todos preenchidos com caixas
		if(obj.isReady())
			numeroDeObjectivosPorConcluir--;
		else
			numeroDeObjectivosPorConcluir++;
		if(numeroDeObjectivosPorConcluir == 0)
			nextLevel();
			
	} // Fim da funcao checkIsFinish()
	
	/**
	 * Game over.
	 */
	public void gameOver() { // Reinicia o mapa
		System.out.println("Game Over!");
		makeLevel();
	} // Fim da funcao gameOver()
	
	/**
	 * Map builder.
	 *
	 * @param map the file with the map
	 * @return Return a ArrayList of ImageTiles.
	 */
	private ArrayList<ImageTile> mapBuilder(File map) {
		ArrayList<ImageTile> levelTiles = new ArrayList<ImageTile>();
		int y = -1;
		AbstractObject obj;
		ArrayList<ObjectiveObject> objectivesObj = new ArrayList<ObjectiveObject>();
		ArrayList<MakeDisapear> makeDisapearObj = new ArrayList<MakeDisapear>();
		Position p;
		try (Scanner scan = new Scanner(map);) {
			while(scan.hasNextLine()) {
				char[] v = scan.nextLine().toCharArray();
				y++;
				for(int i = 0 ; i < v.length ; i++) {
					p = new Position(i, y);
					obj = FabricaDeObjecto.createObject(v[i], p);
					if(!obj.getName().equals("Chao")) {
						levelTiles.add(FabricaDeObjecto.createObject(' ', p));
						levelTiles.add(obj);
						objects.add(obj);
						if(obj.isObjective()) {
							objectivesObj.add((ObjectiveObject) obj);
							obj.addObserver(this);
						}
						if(obj.makeDisapear()) {
							makeDisapearObj.add((MakeDisapear) obj);
							Player.getPlayer().addObserver((Observer) obj);
						}
					} else {
						levelTiles.add(obj);
					}
				} // Fim do for;
			} // Fim do while;
			numeroDeObjectivosPorConcluir = objectivesObj.size();
			prepareObservers(objectivesObj, makeDisapearObj);
		} catch (FileNotFoundException e) {
			System.err.println(e.getClass() + ": " + e.getMessage());
		}
		return levelTiles;
	} // Fim da funcao mapBuilder(File map);
	
	private void prepareObservers(ArrayList<ObjectiveObject> objectivesObj, ArrayList<MakeDisapear> makeDisapearObj) {
		for(AbstractObject obj : objects) {
			if(obj.isMovable()) {
				for(MakeDisapear makeDisapear : makeDisapearObj) {
					obj.addObserver(makeDisapear);
					if(!obj.canFall())
						((Observable) makeDisapear).addObserver((Observer) obj);
				}
				if(obj.isCaixa())
					for(ObjectiveObject objective : objectivesObj) {
						obj.addObserver(objective);
					}
			}
		}
	}
	
	/**
	 * Gets the objects.
	 *
	 * @return the objects
	 */
	public ArrayList<AbstractObject> getObjects() {
		return objects;
	} // Fim da funcao getObjects();

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable arg0, Object arg1) { // Recebe comandos do teclado;
		if(arg0 instanceof ImageMatrixGUI) {
			int lastKeyPressed = (Integer) arg1;
			//System.out.println("Key pressed " + lastKeyPressed);
			// VK_UP, VK_DOWN, VK_LEFT, VK_RIGHT, VK_ENTER
			if(Player.getPlayer() != null) {
				switch (lastKeyPressed) {
				case KeyEvent.VK_ENTER:
					gameOver(); // Faz restart do level;
					ImageMatrixGUI.getInstance().update(); // Faz repaint da tela quando feito gameover pelas teclas;
					break;
					
				case KeyEvent.VK_UP:
					Player.getPlayer().move(Direction.UP);
					break;
					
				case KeyEvent.VK_DOWN:
					Player.getPlayer().move(Direction.DOWN);
					break;
					
				case KeyEvent.VK_LEFT:
					Player.getPlayer().move(Direction.LEFT);
					break;
					
				case KeyEvent.VK_RIGHT:
					Player.getPlayer().move(Direction.RIGHT);
					break;
		
				default:
					break;
				}
			}
		} else if(arg0 instanceof ObjectiveObject) {
			 ObjectiveObject obj = (ObjectiveObject) arg1;
			 checkIsFinish(obj);
		}
	} // Fim da funcao update();
} // Fim da class SokobanGame;
