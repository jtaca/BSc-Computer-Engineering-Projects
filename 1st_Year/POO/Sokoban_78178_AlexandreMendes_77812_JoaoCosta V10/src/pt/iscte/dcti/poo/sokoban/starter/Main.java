package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;

public class Main {

	public static void main(String[] args) {
		ImageMatrixGUI.getInstance().addObserver(SokobanGame.getGame());
		ImageMatrixGUI.getInstance().go();

	}

}
