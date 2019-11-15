package pt.iscte.dcti.poo.sokoban.starter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;


import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.text.StyledEditorKit.ForegroundAction;


public class Scores implements Comparable<Scores>{
	
	
	private String name;
	private int score;
	
	public Scores(String name, int score) {
		
		this.name = name;
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	@Override
	public String toString() {
		return  name + "=> " + score ;
	}

	public static int calcScore(int moves, int battery){
		return (battery*1000 - moves);
	}

	public static void saveScore(String pathName, Scores obj, int numberOfResults) throws ClassNotFoundException{
		LinkedList<Scores> HighScores = new LinkedList<>();
		try {
			Scanner  in = new Scanner(new File(pathName));
			while (in.hasNextLine()){
				String line = in.nextLine();
				String [] spliter = line.split(":");
				int val = Integer.parseInt(spliter[spliter.length-1]);
				HighScores.add(new Scores(spliter[0], val));
			}
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		System.out.println(HighScores);
		HighScores.add(obj);
		Collections.sort(HighScores);
		while(HighScores.size()>10){
			HighScores.remove(HighScores.size()-1);
		}
		System.out.println(HighScores);
		try {
			PrintWriter out = new PrintWriter(new File(pathName));
			StringBuffer buffer = new StringBuffer();
			buffer.append("<html><font color=#FF0000>HIGHSCORE</font><html>" + System.getProperty("line.separator"));
			int place = 0;
			for (Scores scores : HighScores) {
				place++;
				out.println(scores.getName() + ":" + scores.score);
				
				if(scores.getName() == SokobanGame.getPlayerName()){
					String dude = SokobanGame.getPlayerName();
					buffer.append("<html><font color=#0000FF> " + place + "º :" + dude  + "</font>" +"<html><font color=#00BA00> "+ " score: " +"</font>"+
							"<html><font color=#FF00FF> "+ scores.score +"</font>");
				}else{
					buffer.append(place + "º :" + scores.getName() + " score: " + scores.score );
				}
				buffer.append(System.lineSeparator());
			}
			out.close();
			showScore(buffer);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	public static void showScore(StringBuffer result){
		
		 ImageIcon icon = new ImageIcon("image/Empilhadora.png");
		 
         JOptionPane.showMessageDialog(null,result,"HIGHSCORE" , JOptionPane.INFORMATION_MESSAGE,new ImageIcon("image/Empilhadora.png"));
	}

	@Override
	public int compareTo(Scores o) {
		if(o.score != this.score){
			return o.score-this.score;
		}else {
			return 0;
		}
		
	}

}
