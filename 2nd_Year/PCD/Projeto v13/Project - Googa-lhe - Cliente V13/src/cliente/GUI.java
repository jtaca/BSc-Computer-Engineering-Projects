package cliente;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import classes_partilhadas.Noticia;
import classes_partilhadas.TituloDeNoticia;
import cliente_interfaces.GUI_Interface;


public class GUI implements GUI_Interface{
	
	private JFrame frame = new JFrame("Googa-lhe");
	private JTextField textField = new JTextField(40);
	private DefaultListModel<TituloDeNoticia> listModel = new DefaultListModel<TituloDeNoticia>();
	private JList<TituloDeNoticia> listField = new JList<TituloDeNoticia>(listModel);
//	private JTextArea textArea;
	private JTextPane textArea = new JTextPane();
	private Cliente client;
	
	private static final int DEFAULT_DIMENSIONS = 400;
	
	public GUI(Cliente cliente) {
//		frame = new JFrame("Googa-lhe");
		
		this.client = cliente;
//		listModel = new DefaultListModel<TituloDeNoticia>();
		
		// para que o botao de fechar a janela termine a aplicacao
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		frame.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				client.close();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
			}
		});
		
		// conteudo em sequencia da esquerda para a direita
		frame.setLayout(new BorderLayout());
		
		addFrameContent();
		
		// para que a janela se redimensione de forma a ter todo o seu conteudo visivel
		frame.pack();
//		frame.setSize(1000, 1000);
	}
	
	private void addFrameContent() {
//		JPanel panel = new JPanel();
//		panel.setLayout(new BorderLayout());
//		frame.add(panel);
		
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout());
		frame.add(northPanel, BorderLayout.NORTH);
		
//		textField = new JTextField(40);
		northPanel.add(textField);
		textField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				search();
			}
		});
		
		JButton searchButton = new JButton("Search");
		northPanel.add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				search();
			}
		});
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout());
		frame.add(centerPanel, BorderLayout.CENTER);
		
//		listField = new JList<TituloDeNoticia>();
		centerPanel.add(listField);
		listField.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting() && !listField.isSelectionEmpty()) {
					TituloDeNoticia selectedValue = listField.getSelectedValue();
					client.requestNews(selectedValue.getTitulo());
				}
			}
		});
//		listField.setPreferredSize(new Dimension(200, 200));
		
		JScrollPane scrollListField = new JScrollPane(listField);
		scrollListField.setPreferredSize(new Dimension(DEFAULT_DIMENSIONS, DEFAULT_DIMENSIONS));
		centerPanel.add(scrollListField);
		
//		textArea = new JTextPane();
		textArea.setEditable(false);
		textArea.setContentType("text/html");
		textArea.setPreferredSize(new Dimension(DEFAULT_DIMENSIONS, DEFAULT_DIMENSIONS));
		
		JScrollPane scrollArea = new JScrollPane(textArea);
		centerPanel.add(scrollArea);
		
//		textArea = new JTextArea(20, 40);
//		textArea.setEnabled(false);
//		textArea.setLineWrap(true);
//		textArea.setWrapStyleWord(true);
		
//		centerPanel.add(textArea);
		
//		StringBuilder sb = new StringBuilder();
//		for(int i = 0 ; i < 1000 ; i++) {
//			sb.append("A\n");
//		}
//		textArea.setText(sb.toString());
		
	}
	
	@Override
	public synchronized void writeOnTextArea(Noticia content) {
		textArea.setText(content.toString());
		textArea.setCaretPosition(0);
	}
	
	private void search() {
		String text = textField.getText();
		if(!text.equals("") && !text.equals(" ")) {
			listField.clearSelection();
			textArea.setText("");
			client.requestSearchOfWord(textField.getText());
		} else {
			System.out.println("Please insert a word");
		}
	}
	
	@Override
	public synchronized void refresh(TituloDeNoticia[] titulos) {
//		DefaultListModel<TituloDeNoticia> model = new DefaultListModel<TituloDeNoticia>();
//        listModel.clear();
//        listField.setModel(listModel);
//		listField.setListData(titulos);
		if(titulos != null) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					listModel.clear();
//			        listField.setModel(listModel);
					listField.setListData(titulos);
				}
			});
			 
			//listField = new JList<TituloDeNoticia>(titulos);
			System.out.println(listField.getModel().toString());
		}
//		frame.repaint();
	}
	
	@Override
	public void open() {
		frame.setVisible(true);
	}
}
