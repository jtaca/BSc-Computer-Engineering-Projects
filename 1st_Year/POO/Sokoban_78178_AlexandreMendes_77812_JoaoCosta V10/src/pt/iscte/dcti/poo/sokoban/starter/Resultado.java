package pt.iscte.dcti.poo.sokoban.starter;

import java.awt.Component;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

public class Resultado implements Serializable, Comparable<Resultado>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private int val;
	
	public Resultado(String name, int val) {
		this.name = name;
		this.val = val;
	}
	
	public Resultado(int val) {
		this(null, val);
	}
	
	public int getVal() {
		return val;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(Resultado obj) {
		return getVal() - obj.getVal();
	}
	
	public static void save(String filePath, Resultado obj, int numberOfResults) {
		ArrayList<Resultado> arrRep = new ArrayList<>();
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath));) {
			arrRep = (ArrayList<Resultado>) in.readObject();
		} catch (FileNotFoundException e) {
			System.err.println(e.getClass() + ": " + e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getClass() + ": " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.err.println(e.getClass() + ": " + e.getMessage());			
		}
		
		arrRep.add(obj);
		Collections.sort(arrRep);
		while(arrRep.size() > numberOfResults) {
			arrRep.remove(arrRep.size()-1);
		}
		
		try {
			guiMensage(prepareResults(obj, arrRep), "Leaderboard");
		} catch (Exception e) {
			
		}
	
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath));) {
			out.writeObject(arrRep);
			out.flush();
		} catch (FileNotFoundException e) {
			System.err.println(e.getClass() + ": " + e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getClass() + ": " + e.getMessage());
		}
	}
	
	private static String prepareResults(Resultado obj, ArrayList<Resultado> arrRep) {
		StringBuffer sb = new StringBuffer();
		if(SokobanGame.getGame().getLevel() < SokobanGame.NumeroTotalDeNiveis - 1)
			sb.append("<html><font color=#FF0000>Next Level!</font><html>" + System.getProperty("line.separator"));
		else
			sb.append("<html><font color=#FF0000>Congrats you Finished the Game!</font><html>" + System.getProperty("line.separator"));
		sb.append("<html><font color=#0000FF> Your score is " + obj.getVal() + "</font>" + System.getProperty("line.separator"));
		int pos = 1;
		for(Resultado result : arrRep) {
			if(result == obj) {
				result.setName(inputName());
				sb.append("<html><font color=#0000FF>" + pos + ". " + result.toString() + "</font>" + System.getProperty("line.separator"));
			} else
				sb.append(pos + ". " + result.toString() + System.getProperty("line.separator"));
			pos++;
		}
		return sb.toString();
	}
	
	public static String inputName() {
		String scoreName;
		do {
			scoreName = JOptionPane.showInputDialog(null, "Please Insert Name (1 to 3 letters): ");
		} while(scoreName == null || scoreName.length() < 1 || scoreName.length() > 3);
		
		return scoreName.toUpperCase();
		
	}
	
	public static void guiMensage(String resultados, String windowText) {
		 
		 Component frame = null;
	     JOptionPane.showMessageDialog(frame, resultados, windowText, JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public String toString() {
		return name + " score is " + val;
	}
	
	

}
