package pt.iscte.dcti.poo.sokoban.starter;

import javax.swing.JOptionPane;

import pt.iul.ista.poo.gui.ImageMatrixGUI;

public class Main {

	public static void main(String[] args) {
		SokobanGame s = new SokobanGame();
		s.setPlayerName(JOptionPane.showInputDialog("Nome por favor :)"));
		ImageMatrixGUI.getInstance().addObserver(s);
		ImageMatrixGUI.getInstance().go();

	}


}
