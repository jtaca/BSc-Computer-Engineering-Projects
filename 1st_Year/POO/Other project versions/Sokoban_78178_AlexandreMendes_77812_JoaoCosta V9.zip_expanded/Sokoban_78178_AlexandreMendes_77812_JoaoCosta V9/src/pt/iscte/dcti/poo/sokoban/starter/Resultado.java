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
	
	private int val;
	
	public Resultado(int val) {
		this.val = val;
	}
	
	public int getVal() {
		return val;
	}

	@Override
	public int compareTo(Resultado obj) {
		return getVal() - obj.getVal();
	}
	
	@SuppressWarnings("unchecked")
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
		System.out.println(arrRep);
		
		arrRep.add(obj);
		Collections.sort(arrRep);
		while(arrRep.size() > numberOfResults) {
			arrRep.remove(arrRep.size()-1);
		}
		System.out.println(arrRep);
		
		try {
			guiMensage(prepareResults(obj, arrRep), "ScoreBoard");
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
		sb.append("<html><font color=#0000FF> O seu " + obj.toString() + "</font>" + System.getProperty("line.separator"));
		int pos = 1;
		for(Resultado result : arrRep) {
			if(result == obj)
				sb.append("<html><font color=#0000FF>" + pos + ". " + result.toString() + "</font>" + System.getProperty("line.separator"));
			else
				sb.append(pos + ". " + result.toString() + System.getProperty("line.separator"));
			pos++;
		}
		return sb.toString();
	}
	
	public static void guiMensage(String resultados, String windowText) {
		 
		 Component frame = null;
	     JOptionPane.showMessageDialog(frame, resultados, windowText, JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public String toString() {
		return "Resultado=" + val;
	}
	
	

}
