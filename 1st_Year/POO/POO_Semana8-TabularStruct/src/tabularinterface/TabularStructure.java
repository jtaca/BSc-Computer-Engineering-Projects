package tabularinterface;

public interface TabularStructure {
	int numberOfLines();
	int numberOfColumns();
	String getElement(int line, int column);
}
