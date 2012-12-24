import javax.swing.JButton;


public class Cell extends JButton {
	private int row, column;

	public Cell(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
}
