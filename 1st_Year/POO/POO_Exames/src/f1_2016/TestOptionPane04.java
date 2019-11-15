package f1_2016;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
public class TestOptionPane04 {

    public static void main(String[] args) {
            
              
                ImageIcon icon = new ImageIcon(TestOptionPane04.class.getResource("/Empilhadora_U.png"));
                JOptionPane.showMessageDialog(null,"O André é má pessoa","Fodasse", JOptionPane.INFORMATION_MESSAGE,icon);
          
            
        
    }
}