import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

// TODO: rename class to Minesweeper
public class Client extends JPanel implements ActionListener {

	private final int NUM_ROWS = 9;
	private final int NUM_COLUMNS = 9;

	private final int NUM_MINES = 10;

	private char grid[][] = new char[NUM_ROWS][NUM_COLUMNS];

	public Client() {
		// start with just a 9x9 grid with 10 mines
		// (later allow user to specify these settings)
		this.setLayout(new GridLayout(9, 9, 10, 10));

		// Where I learned about using Layout Manager to add padding
		// http://stackoverflow.com/questions/5328405/jpanel-padding-in-java

		JButton button;

		for(int row = 0; row < NUM_ROWS; row++) {
			for(int column = 0; column < NUM_COLUMNS; column++) {
				grid[row][column] = ' ';	// empty square
			}
		}

		// "splatter" grid with mines
		splatter();

		for(int row = 0; row < NUM_ROWS; row++) {
			for(int column = 0; column < NUM_COLUMNS; column++) {
				button = new Cell("" + grid[row][column], row, column);
				// TODO: Cell should display what's on the grid at that location only when the user clicks on it
				button.addActionListener(this);
				this.add(button);
			}
		}
	}

	// method to "splatter" grid with mines
	private void splatter() {
		// temporarily store location of placed mines
		int rows[] = new int[NUM_MINES];
		int columns[] = new int[NUM_MINES];

		// variables to hold a particular mine's grid location
		int row, column;

		Random random = new Random();
		// Where I learned about using Random
		// http://www.javapractices.com/topic/TopicAction.do?Id=62

		// variable used in checking if mine was placed at a valid location
		boolean valid;

		for(int count = 0; count < NUM_MINES; count++) {
			// get random grid location
			row = random.nextInt(NUM_ROWS);
			column = random.nextInt(NUM_COLUMNS);

			// remember grid location
			rows[count] = row;
			columns[count] = column;

			// prevent mines from being placed on top of other mines
			if('M' != grid[row][column]) {
				grid[row][column] = 'M';
			}
			else {
				// repeat placement of mine at random location
				count--;
			}
		}

		for(int count = 0; count < NUM_MINES; count++) {
			// assume placed mine was invalid (even though it should rarely occur)
			// that is, it doesn't have a neighboring cell with a number in it
			valid = false;

			// recall mine's grid location
			row = rows[count];
			column = columns[count];

			// if at least one neighboring cell has it's mine count updated, then mine's location was valid
			valid = updateCounter(row - 1, column - 1) || valid;
			valid = updateCounter(row - 1, column) || valid;
			valid = updateCounter(row - 1, column + 1) || valid;
			valid = updateCounter(row, column - 1) || valid;
			valid = updateCounter(row, column + 1) || valid;
			valid = updateCounter(row + 1, column - 1) || valid;
			valid = updateCounter(row + 1, column) || valid;
			valid = updateCounter(row + 1, column + 1) || valid;

			// Note: valid variable is placed as second operand of OR operator to prevent short-circuiting.
			// That is, if valid is true then updateCounter would not be called.
			// Following the same logic, separate OR statements were used as well.

			if(!valid) {
				// remove invalid mine
				grid[row][column] = ' ';

				// get next mine's location
				row = rows[count + 1];
				column = columns[count + 1];

				// prevent mines from being placed on top of other mines
				while('M' == grid[row][column]) {
					// get random grid location
					row = random.nextInt(NUM_ROWS);
					column = random.nextInt(NUM_COLUMNS);
				}

				// remember grid location
				rows[count] = row;
				columns[count] = column;

				// repeat validation check
				count--;
			}
		}
	}

	private boolean isOnGrid(int row, int column) {
		boolean isValidRow = ((row >= 0) && (row < NUM_ROWS));
		boolean isValidColumn = ((column >= 0) && (column < NUM_COLUMNS));

		return (isValidRow && isValidColumn);
	}

	private boolean updateCounter(int row, int column) {
		if(isOnGrid(row, column)) {
			if(' ' == grid[row][column]) {
				grid[row][column] = '1';
				return true;
			}

			if('1' == grid[row][column]) {
				grid[row][column] = '2';
				return true;
			}

			if('2' == grid[row][column]) {
				grid[row][column] = '3';
				return true;
			}

			if('3' == grid[row][column]) {
				grid[row][column] = '4';
				return true;
			}

			if('4' == grid[row][column]) {
				grid[row][column] = '5';
				return true;
			}

			if('5' == grid[row][column]) {
				grid[row][column] = '6';
				return true;
			}

			if('6' == grid[row][column]) {
				grid[row][column] = '7';
				return true;
			}

			if('7' == grid[row][column]) {
				grid[row][column] = '8';
				return true;
			}
		}

		return false;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		JButton button = (JButton) actionEvent.getSource();
		button.setEnabled(false);

//		Cell cell = (Cell)actionEvent.getSource();
//		int row = cell.getRow();
//		int column = cell.getColumn();
//
//		cell.setText("" + grid[row][column]);

//		// TODO: how to get location of button pressed?
//
//		// if cell is number
//			// show number
//		// else if cell is mine
//			// end game
//		// else
//
//			// add cell to set
//			Stack<Integer> rows = new Stack<Integer>();
//			Stack<Integer> columns = new Stack<Integer>();
//			// Note: This is a good reason to make Cell a class.
//
//			int row, column;
//
//			while(!rows.isEmpty() && !columns.isEmpty()){
//
//				row = rows.pop();
//				column = columns.pop();
//
//				// TODO: show cell
//
//				checkNeighbor(row - 1, column - 1, rows, columns);
//				checkNeighbor(row - 1, column, rows, columns);
//				checkNeighbor(row - 1, column + 1, rows, columns);
//				checkNeighbor(row, column - 1, rows, columns);
//				checkNeighbor(row, column + 1, rows, columns);
//				checkNeighbor(row + 1, column - 1, rows, columns);
//				checkNeighbor(row + 1, column, rows, columns);
//				checkNeighbor(row + 1, column + 1, rows, columns);
//			}
	}

	// ****

//	private void clearGrid(){
//		for(int row = 0; row < NUM_ROWS; row++){
//			for(int column = 0; column < NUM_COLUMNS; column++){
//				grid[row][column] = ' ';	// empty square
//			}
//		}
//	}



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
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
