package tabularinterface;

public class Matrix implements TabularStructure {

	private int[][] matrix;
	
	public Matrix(int[][] matrix) {
		this.matrix = matrix;
	}
	
	@Override
	public int numberOfLines() {		
		return matrix.length;
	}

	@Override
	public int numberOfColumns() {
		return matrix[0].length;
	}

	@Override
	public String getElement(int line, int column) {		
		return Integer.toString(matrix[line][column]);
	}

	// ...
}
