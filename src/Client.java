import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
			// while set is not empty:
				// pop first cell in set
				// show cell

				// row - 1, col - 1
				// if <some-method(row - 1, col -1)>, then add cell to set
				// if((grid[row - 1][col - 1] is blank) && (grid[row - 1][col - 1] hasn't been shown)) add cell to set
				// create two methods: isBlank(int row, int column), isShown(int row, int column)

				// row - 1, col
				// row - 1, col + 1

				// row, col - 1
				// row, col + 1

				// row + 1, col - 1
				// row + 1, col
				// row + 1, col + 1
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("~ minesweeper ~");
		JPanel panel = new JPanel();
		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
