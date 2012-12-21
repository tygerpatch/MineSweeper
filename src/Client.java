import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Client implements ActionListener {
	// start with just a 9x9 grid with 10 mines
	// (later allow user to specify these settings)
	final int NUM_ROWS = 9;
	final int NUM_COLUMNS = 9;

	char grid[][] = new char[NUM_ROWS][NUM_COLUMNS];

	private void clearGrid(){
		for(int row = 0; row < NUM_ROWS; row++){
			for(int column = 0; column < NUM_COLUMNS; column++){
				grid[row][column] = ' ';	// empty square
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		actionEvent.getSource();
		// if cell is number
			// show number
		// else if cell is mine
			// end game
		// else

			// add cell to set
			Stack<Integer> rows = new Stack<Integer>();
			Stack<Integer> columns = new Stack<Integer>();
			// Note: This is a good reason to make Cell a class.

			int row, column;

			while(!rows.isEmpty() && !columns.isEmpty()){

				row = rows.pop();
				column = columns.pop();

				// TODO: show cell

				checkNeighbor(row - 1, column - 1, rows, columns);
				checkNeighbor(row - 1, column, rows, columns);
				checkNeighbor(row - 1, column + 1, rows, columns);
				checkNeighbor(row, column - 1, rows, columns);
				checkNeighbor(row, column + 1, rows, columns);
				checkNeighbor(row + 1, column - 1, rows, columns);
				checkNeighbor(row + 1, column, rows, columns);
				checkNeighbor(row + 1, column + 1, rows, columns);
			}
	}


	private void checkNeighbor(int row, int column, Stack<Integer> rows, Stack<Integer> columns){
		// test for index out of range
		boolean isValidRow = ((row >= 0) && (row < NUM_ROWS));
		boolean isValidColumn = ((column >= 0) && (column < NUM_COLUMNS));
		boolean isBlankCell = isBlank(row - 1, column - 1);
		boolean isShownCell = isShown(row - 1, column - 1);

		if(isValidRow && isValidColumn && isBlankCell && !isShownCell) {
			rows.push(row - 1);
			columns.push(column  - 1);
		}
	}

	private boolean isBlank(int row, int column) {
		// Follow best practice of listing constant first in equality expressions.
		// This prevents accidentally assigning constant to variable, instead of checking for equality.
		return (' ' == grid[row][column]);
	}

	private boolean isShown(int row, int column) {
		// TODO:
		return false;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("~ minesweeper ~");
		JPanel panel = new JPanel();
		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
