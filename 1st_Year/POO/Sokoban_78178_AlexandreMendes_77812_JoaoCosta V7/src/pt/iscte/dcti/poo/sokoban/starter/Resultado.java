package pt.iscte.dcti.poo.sokoban.starter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import org.junit.internal.runners.model.EachTestNotifier;

import java.awt.*;
import java.awt.TrayIcon.MessageType;

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
	
	public static void save(String filePath, Resultado obj, int numberOfResults){
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
		
		System.out.println(arrRep);
		try {
			guiMeaasge(arrRep);
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
	
	 private static void guiMeaasge(ArrayList<Resultado> arrRep) {
		 
		 Component frame = null;
	     JOptionPane.showMessageDialog(frame, arrRep, "ScorBoard", JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public String toString() {
		return "Resultado=" + val;
	}
	
	

}
