package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import classes_partilhadas.Connections;
import classes_partilhadas.Noticia;
import classes_partilhadas.TipoDeMensagem;
import classes_partilhadas.TituloDeNoticia;
import cliente_interfaces.Client_Interface;
import classes_partilhadas.Message;

public class Cliente implements Client_Interface {
	
	private GUI userGUI;
//	private Procura procura;
	private Socket socket;
	private ObjectOutputStream out;
	private ClientConnection clientConnection;
	
	public Cliente() {
//		procura = new Procura();
		userGUI = new GUI(this);
		runClient();
		userGUI.open();
	}
	
	public static void main(String[] args) {
		new Cliente();
	}
	
	public void runClient() {
		try {
			connectToServer();

		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void connectToServer() throws IOException {
		InetAddress endereco = InetAddress.getByName(null);
		System.out.println("Endereço = " + endereco);
		socket = new Socket(endereco, Connections.PORTO);
		
		System.out.println("Socket = " + socket);
		
//		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//		
//		out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
		
		out = new ObjectOutputStream(socket.getOutputStream());
		
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		
		clientConnection = new ClientConnection(in, this);
		clientConnection.start();
		
		out.writeObject(new Message<String>(TipoDeMensagem.ENTER_CLIENT, ""));
		
//		out.writeObject(new Message("entrar", username));
	}
	
	private void sendMessage(TipoDeMensagem tipo, String message) {
		try {
			out.writeObject(new Message<String>(tipo, message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void addNewsListToRefresh(TituloDeNoticia[] titulos) {
//		titulos.sort(new NumberOfOccurrencesSort());
		userGUI.refresh(titulos);
	}
	
	@Override
	public void requestNews(String title) {
		sendMessage(TipoDeMensagem.GET_NEWS, title);
//		if(content != null) {
//			userGUI.writeOnTextArea(content);
//		}
	}
	
	@Override
	public void requestSearchOfWord(String word) {
		sendMessage(TipoDeMensagem.SEARCH_WORD, word);
	}
	
	@Override
	public void writeNewsOnTextArea(Noticia content) {
		userGUI.writeOnTextArea(content);
	}
	
	public void close() {
		if(out != null) {
			try {
				System.out.println("a fechar...");
				out.writeObject(new Message<String>(TipoDeMensagem.EXIT, ""));
				clientConnection.join(1000); // Join com timeout pode ser uma solucao para problemas futuros (Pode ser removido (Maybe))
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	
}
