package jogGalo;


import java.io.FileNotFoundException;

import tabularinterface.TableToTextConverter;
import tabularinterface.TabularStructure;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        char[][] m = {{' ' ,'|',' ' }, {4, 5, 6},{7,8,9}};
        TabularStructure matrix = new CharMatrix(m);
        String text = TableToTextConverter.toText(matrix, " ", false);
        System.out.println(text);
        
    }
}
