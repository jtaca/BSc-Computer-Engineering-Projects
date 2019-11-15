package pt.iscte.dcti.poo.sokoban.starter;

import java.awt.event.KeyEvent;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import javax.swing.JOptionPane;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Position;

public class SokobanGame implements Observer {
 	 
	private static int currentLevel = 0;
	private static int numberOfLevels = new File("levels/").listFiles().length;
	private static  LinkedList<GameElement> elements ;
	public final static int batteryMax = 100;
	private static String PlayerName ;
	
	public SokobanGame(){
		startANewLevel();
	}
	
	private static void startANewLevel(){
		System.out.println(numberOfLevels);
		ArrayList<ImageTile> tiles = buildSampleLevel(new File("levels/level"+currentLevel+".txt"));
		ImageMatrixGUI.getInstance().setStatusMessage(statusMessage());
		ImageMatrixGUI.getInstance().addImages(tiles);
		Player.instanceOf().setBattery(batteryMax);
		Player.instanceOf().setMovements(0);
		ImageMatrixGUI.getInstance().update();
		
	}
	
	static String statusMessage() {
		return "Level: "+ currentLevel + " Movements: " + Player.instanceOf().getMovements()
				+ " Battery: " + Player.instanceOf().getBattery() +" Score: " +
				 Scores.calcScore(Player.instanceOf().getMovements(), Player.instanceOf().getBattery());
	}

	public static void gameOver(boolean won){
		elements.clear();
		ImageMatrixGUI.getInstance().clearImages();
		elements = null;
		startANewLevel();
		
		
	}
	
	private static ArrayList<ImageTile> buildSampleLevel(File map){
		elements = new LinkedList<GameElement>();
		GameElement Elem;
		ArrayList<ImageTile> LevelTiles = new ArrayList<ImageTile>();
		Factory factory = new Factory();
		try(Scanner s = new Scanner(map)){
			for(int y = 0; s.hasNextLine(); y++){
				char[] reading = s.nextLine().toCharArray();
				for(int x = 0; x < reading.length; x++){
					Position position = new Position(x,y);
					Elem = factory.create(reading[x], position);
					LevelTiles.add(Elem);
					if(!Elem.getName().equals("Chao")){
						elements.add(Elem);
						LevelTiles.add(factory.create('-', position));
					}
				}
				
			}
			
		}catch (FileNotFoundException e) {
			System.err.println(e.getClass() +":"+ e.getMessage());
		}		
		return LevelTiles;	
		
	}
	
	public static GameElement elementInPosition(GameElement Ele, Position p){
		for (GameElement gameElement : elements) {
			if(gameElement.getPosition().equals(p) && !gameElement.equals(Ele) &&
					(gameElement.isMovable() || gameElement.isFallable() || gameElement.isCatchable())){
				return gameElement;
			}
			
		}
		return null;
	}
	
	public static void checkWin(){
		boolean result = true;
		for(GameElement Elem : elements){
			if(Elem.isObjective()){
				if(!((Objective) Elem).checkBox()){
					result = false;
				}
			}
		}
		System.out.println(result);
		if(result){
			ImageMatrixGUI.getInstance().update();
			try {
				Scores.saveScore("scores/level"+ currentLevel +".txt", new Scores(getPlayerName(),Scores.
						calcScore(Player.instanceOf().getMovements(),Player.instanceOf().getBattery())), 10);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			nextLevel();
		}	
	}
	
	private static void nextLevel() {
		currentLevel = (currentLevel+1)%numberOfLevels;
		gameOver(true);
		
	}
	private void previousLevel() {
		currentLevel = currentLevel-1;
		if(currentLevel<0){
			currentLevel = numberOfLevels-1;
		}	
		gameOver(true);	
		
	}

	public static int getCurrentLevel() {
		return currentLevel;
	}

	public static void setCurrentLevel(int currentLevel) {
		SokobanGame.currentLevel = currentLevel;
	}

	
	
	public static LinkedList<GameElement> getElements() {
		return elements;
	}

	public static void setElements(LinkedList<GameElement> elements) {
		SokobanGame.elements = elements;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		int lastKeyPressed = (Integer) arg1;
		System.out.println("Key pressed " + lastKeyPressed);
		switch (lastKeyPressed) {
		case KeyEvent.VK_UP:
			Player.instanceOf().move(Direction.UP);
			break;
		case KeyEvent.VK_DOWN:
			Player.instanceOf().move(Direction.DOWN);
			break;
		case KeyEvent.VK_LEFT:
			Player.instanceOf().move(Direction.LEFT);
			break;
		case KeyEvent.VK_RIGHT:
			Player.instanceOf().move(Direction.RIGHT);
			break;
		case KeyEvent.VK_W:
			Player.instanceOf().move(Direction.UP);
			break;
		case KeyEvent.VK_S:
			Player.instanceOf().move(Direction.DOWN);
			break;
		case KeyEvent.VK_A:
			Player.instanceOf().move(Direction.LEFT);
			break;
		case KeyEvent.VK_D:
			Player.instanceOf().move(Direction.RIGHT);
			break;
		case KeyEvent.VK_ENTER:
			gameOver(false);
			break;	
		case KeyEvent.VK_L:	
			 nextLevel();
			 break;
		case KeyEvent.VK_K:	
			 previousLevel();
			 break;	
		default:
			break;
		}		
	}

	public static String getPlayerName() {
		return PlayerName;
	}

	public static void setPlayerName(String playerName) {
		PlayerName = playerName;
	}

	
}
