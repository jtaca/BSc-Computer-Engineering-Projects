package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;

import javax.swing.JTextArea;

import classes_partilhadas.Message;
import classes_partilhadas.Noticia;
import classes_partilhadas.TituloDeNoticia;
import interfaces_partilhadas.Message_Interface;

public class ClientConnection extends Thread {

	private ObjectInputStream in;
	private Cliente client;
	
	private int id;
	
	public ClientConnection(ObjectInputStream in, Cliente client) {
		this.in = in;
		this.client = client;
	}
	
	@Override
	public void run() {
		Message_Interface obj;
		while(true) {	
			try {
				obj = (Message_Interface) in.readObject();
				if(obj.getTipo().name().equals("EXIT"))
					break;
				treatMessage(obj);
				
				
//				System.out.println(isInterrupted());
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Im here " + isInterrupted());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void treatMessage(Message_Interface obj) {
		switch (obj.getTipo()) {
		case SEARCH_WORD:
			client.addNewsListToRefresh((TituloDeNoticia[]) obj.getContent());
			break;
			
		case GET_NEWS:
			client.writeNewsOnTextArea((Noticia) obj.getContent());
			break;


		default:
			break;
		}
		
	}
	
	
}
