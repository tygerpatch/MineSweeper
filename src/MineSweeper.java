import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

// TODO: clean up code
public class MineSweeper extends JFrame implements MouseListener {

	private final int NUM_ROWS = 9;
	private final int NUM_COLUMNS = 9;

	private final int NUM_MINES = 10;

	private char grid[][] = new char[NUM_ROWS][NUM_COLUMNS];
	private JButton cells[][] = new JButton[NUM_ROWS][NUM_COLUMNS];

	private boolean isGameOver;
	private int numMinesLeft;

	public MineSweeper() {
		JPanel panel = new JPanel(new GridLayout(9, 9));

		// calculate the preferred size for the JPanel
		JButton button = new JButton("M");
		Font font = button.getFont();
		FontMetrics fontMetrics = button.getFontMetrics(font);
		// Where I learned about getting Font dimensions
		// http://stackoverflow.com/questions/1524855/how-to-calculate-the-fonts-width

		int width = fontMetrics.stringWidth("M");
		int height = fontMetrics.stringWidth("M");

		Dimension dimension = new Dimension((width + 2) * NUM_ROWS + 100, (height + 2) * NUM_COLUMNS + 50);
		// TODO: determine frame's border width, which is what the additional 100 and 50 are  supposed to represent

		this.setPreferredSize(dimension);

		for(int row = 0; row < NUM_ROWS; row++) {
			for(int column = 0; column < NUM_COLUMNS; column++) {
				button = new Cell(row, column);
				cells[row][column] = button;

				button.addMouseListener(this);

				// change margin size to reduce amount of space needed to display button's label
			    button.setMargin(new Insets(1, 1, 1, 1));

				// Where I learned about margins.
				// http://www.coderanch.com/t/345647/GUI/java/JButton-text-padding
				panel.add(button);
			}
		}

		newGame();

		setTitle("~ minesweeper ~");
		setContentPane(panel);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void newGame() {
		isGameOver = false;
		numMinesLeft = NUM_MINES;

		// every grid cell starts off empty
		for(int row = 0; row < NUM_ROWS; row++) {
			for(int column = 0; column < NUM_COLUMNS; column++) {
				grid[row][column] = ' ';
				cells[row][column].setText(" ");
				cells[row][column].setEnabled(true);
			}
		}

		// "splatter" grid with mines
		// splatter();

		grid[0] = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8'};
		grid[1] = new char[]{'0', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '8'};
		grid[2] = new char[]{'0', ' ', '2', '3', '4', '5', '6', ' ', '8'};
		grid[3] = new char[]{'0', ' ', '2', ' ', ' ', ' ', '6', ' ', '8'};
		grid[4] = new char[]{'0', ' ', '2', ' ', '4', ' ', '6', ' ', '8'};
		grid[5] = new char[]{'0', ' ', '2', ' ', '4', ' ', '6', ' ', '8'};
		grid[6] = new char[]{'0', ' ', '2', ' ', '4', '5', '6', ' ', '8'};
		grid[7] = new char[]{'0', ' ', '2', ' ', ' ', ' ', ' ', ' ', '8'};
		grid[8] = new char[]{'0', ' ', '2', '3', '4', '5', '6', '7', '8'};
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
	public void mouseClicked(MouseEvent mouseEvent) {
	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent) {
	}

	@Override
	public void mouseExited(MouseEvent mouseEvent) {
	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
		Cell cell = (Cell) mouseEvent.getSource();
		int row = cell.getRow();
		int column = cell.getColumn();

		if(SwingUtilities.isRightMouseButton(mouseEvent)) {
			if(cell.getText().equalsIgnoreCase("F")) {
				cell.setText(" ");
				numMinesLeft--;
			}
			else {
				cell.setText("F");

				// User has to flag mines in order to win, not a random selection of cells.
				if('M' == grid[row][column]){
					numMinesLeft--;

					if(0 == numMinesLeft) {
						int result = JOptionPane.showConfirmDialog(null, "Play Again?", "Congratulations! You Won!!!", JOptionPane.YES_NO_OPTION);

						if(JOptionPane.YES_OPTION == result) {
							newGame();
						}
						else {
							// close JfFrame window
							dispose();

							// Where I learned about dispose method
							// http://stackoverflow.com/questions/2352727/closing-jframe-with-button-click
						}
					}
				}
			}
		}
		// essentially disallow user from digging up flag
		else if(!cell.getText().equalsIgnoreCase("F") && !isGameOver) {
				char ch = grid[row][column];

				cell.setText("" + ch);
				cell.setEnabled(false);

				if('M' == ch) {
					isGameOver = true;

					int result = JOptionPane.showConfirmDialog(null, "Play Again?", "Game Over", JOptionPane.YES_NO_OPTION);

					if(JOptionPane.YES_OPTION == result) {
						newGame();
					}
					else {
						// close JfFrame window
						dispose();

						// Where I learned about dispose method
						// http://stackoverflow.com/questions/2352727/closing-jframe-with-button-click
					}
				}
				else if(' ' == ch) {
					showIfBlank(row - 1, column - 1);
					showIfBlank(row - 1, column);
					showIfBlank(row - 1, column + 1);
					showIfBlank(row, column - 1);
					showIfBlank(row, column + 1);
					showIfBlank(row + 1, column - 1);
					showIfBlank(row + 1, column);
					showIfBlank(row + 1, column + 1);
				}
		}
	}

	private void showIfBlank(int row, int column) {
		// if location is on the grid
		if(isOnGrid(row, column)) {

			// if given cell is blank and hasn't been visited
			if(isBlank(row, column) && cells[row][column].isEnabled()) {
				// mark cell as having been visited
				cells[row][column].setEnabled(false);

				showIfBlank(row - 1, column - 1);
				showIfBlank(row - 1, column);
				showIfBlank(row - 1, column + 1);
				showIfBlank(row, column - 1);
				showIfBlank(row, column + 1);
				showIfBlank(row + 1, column - 1);
				showIfBlank(row + 1, column);
				showIfBlank(row + 1, column + 1);
			}

			// mark cell as having been visited (redundant for blank cells, but can't be helped)
			cells[row][column].setEnabled(false);

			// show cell (including numbered cells)
			cells[row][column].setText("" + grid[row][column]);

			// Note: A blank cell will never be touching a mine.
		}
	}

	private boolean isBlank(int row, int column) {
		// Follow best practice of listing constant first in equality expressions.
		// This prevents accidentally assigning constant to variable, instead of checking for equality.
		return (' ' == grid[row][column]);
	}

	public static void main(String[] args) {
		new MineSweeper();
	}
}
