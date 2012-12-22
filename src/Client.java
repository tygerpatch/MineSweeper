import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Client extends JPanel implements ActionListener {
	// start with just a 9x9 grid with 10 mines
	// (later allow user to specify these settings)
	final int NUM_ROWS = 9;
	final int NUM_COLUMNS = 9;

	char grid[][] = new char[NUM_ROWS][NUM_COLUMNS];

	int NUM_MINES = 10;

	// method to "splatter" grid with mines
	private void splatter() {
		int row, column;
		int count = NUM_MINES;

		Random random = new Random();
		// Where I learned about using Random
		// http://www.javapractices.com/topic/TopicAction.do?Id=62

		while(count > 0) {
			// get random location on grid
			row = random.nextInt(NUM_ROWS);
			column = random.nextInt(NUM_COLUMNS);

			// prevent mines from being placed on top of other mines
			// ensure their is a neighboring cell with a number in it
			if(hasNeighboringCounter(row, column)) {
				updateNeighboringCounters(row, column);
				grid[row][column] = 'M';
				count--;
			}
		}
	}

	private void updateNeighboringCounters(int row, int column) {
		updateCounter(row - 1, column - 1);
		updateCounter(row - 1, column);
		updateCounter(row - 1, column + 1);
		updateCounter(row, column - 1);
		updateCounter(row, column + 1);
		updateCounter(row + 1, column - 1);
		updateCounter(row + 1, column);
		updateCounter(row + 1, column + 1);
	}

	private void updateCounter(int row, int column){
		if(isOnGrid(row, column)){
			if(' ' == grid[row][column]) {
				grid[row][column] = '1';
			}
			else if('1' == grid[row][column]) {
					grid[row][column] = '2';
			}
			else if('2' == grid[row][column]) {
					grid[row][column] = '3';
			}
			else if('3' == grid[row][column]) {
					grid[row][column] = '4';
			}
			else if('4' == grid[row][column]) {
					grid[row][column] = '5';
			}
			else if('5' == grid[row][column]) {
					grid[row][column] = '6';
			}
			else if('6' == grid[row][column]) {
					grid[row][column] = '7';
			}
			else if('7' == grid[row][column]) {
					grid[row][column] = '8';
			}
		}
	}

	private boolean hasNeighboringCounter(int row, int column) {
		return isNeighboringCounter(row - 1, column - 1) ||
				isNeighboringCounter(row - 1, column) ||
				isNeighboringCounter(row - 1, column + 1) ||
				isNeighboringCounter(row, column - 1) ||
				isNeighboringCounter(row, column + 1) ||
				isNeighboringCounter(row + 1, column - 1) ||
				isNeighboringCounter(row + 1, column) ||
				isNeighboringCounter(row + 1, column + 1);
	}

	private boolean isNeighboringCounter(int row, int column){
		if(isOnGrid(row, column)) {
			// basically looking for anything but a mine
			return ('M' != grid[row][column]);
		}

		return false;
	}

	private boolean isOnGrid(int row, int column){
		boolean isValidRow = ((row >= 0) && (row < NUM_ROWS));
		boolean isValidColumn = ((column >= 0) && (column < NUM_COLUMNS));

		return (isValidRow && isValidColumn);
	}

	private void clearGrid(){
		for(int row = 0; row < NUM_ROWS; row++){
			for(int column = 0; column < NUM_COLUMNS; column++){
				grid[row][column] = ' ';	// empty square
			}
		}
	}

	public Client(){
		this.setLayout(new GridLayout(9, 9));
		for(int row = 0; row < NUM_ROWS; row++){
			for(int column = 0; column < NUM_COLUMNS; column++){
				this.add(new JButton(row + ", " + column));
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

		// Note: By checking if cell has shown, stacks become implicit sets.
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
		JPanel panel = new Client();
		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
