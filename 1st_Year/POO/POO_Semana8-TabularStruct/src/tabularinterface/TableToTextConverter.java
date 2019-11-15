package tabularinterface;

/**
 * Esta classe contém métodos utilitários para impressão de tabelas
 */
public class TableToTextConverter {
	
	private static final String newline = System.getProperty("line.separator");
	private static char lineSeparatorChar = '-';
		
	/**
	 * Devolve o texto da tabela sem separador de colunas nem separador de linhas
	 */
	public static String toText(TabularStructure matrix) {
		return toText(matrix, "", false);
	}
	
	/**
	 * Devolve o texto da tabela utilizando dado um separador de colunas.
	 * Caso o parâmetro lineSeparator seja igual a true, é incluido um separador 
	 * com comprimento igual ao comprimento da maior linha da matriz.
	 */
	public static String toText(TabularStructure matrix, String colSeparator, boolean lineSeparator) {
		String[] lines = buildLines(matrix, colSeparator);
		
		String sep = newline;
		if(lineSeparator) {
			int maxLength = 0;
			for(String line : lines) {
				if(maxLength < line.length())
					maxLength = line.length();
			}
			sep = buildLineSeparator(maxLength);
		}
		
		String text = mergeLines(lines, sep);
		return text;				
	}

	
	
	
	private static String[] buildLines(TabularStructure m, String columnSeparator) {
		String[] lines = new String[m.numberOfLines()];
		for(int i = 0; i < m.numberOfLines(); i++) {
			String lineText = "";
			for(int col = 0; col < m.numberOfColumns(); col++) {
				if(col != 0)
					lineText += columnSeparator;

				lineText += m.getElement(i, col);
			}
			lines[i] = lineText;		
		}
		return lines;
	}

	private static String mergeLines(String[] lines, String sep) {
		String text = "";
		for(int line = 0; line < lines.length; line++) {
			text += lines[line];
			if(line + 1 != lines.length) {
				text += sep;
			}
		}
		return text;
	}

	private static String buildLineSeparator(int maxLentgh) {
		String sep = newline;
		for(int i = 0; i < maxLentgh; i++)
			sep += lineSeparatorChar;

		sep += newline;
		return sep;
	}
}
