import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;

public class MineSweeper extends JFrame{
	public static int n = Integer.parseInt(JOptionPane.showInputDialog("How big would you like your board to be? Enter the number of rows: "));
	public static int row = n;
	public static int col = n;
	public static int flaggedbombs = 0;
	public static int mines = (int) ((int)row*col* .15);
	public static int minesleft = mines;
	public static Square[][] board = new Square[row+2][col+2];
	JPanel boardPanel = new JPanel();
	JButton clear = new JButton("Clear");
	JButton start = new JButton("Start");
	JPanel controlPanel = new JPanel();
	JPanel topPanel = new JPanel();
	public static JLabel label = new JLabel("Mines Left:");
	public static JTextField message = new JTextField(10);


	public MineSweeper() {
		int r, c;
		setTitle("MineSweeper");
		setSize(800, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		boardPanel.setLayout(new GridLayout(row,col));
		

		for (r=0; r<board.length; r++) {
			for (c=0; c<board[r].length;c++) {
				board[r][c] = new Square(r, c);
			}
		}
		for (r=1; r<board.length-1; r++) {
			for (c=1; c<board[r].length-1;c++) {
				boardPanel.add(board[r][c]);
			}
		}

		int i = 0;
		int low = 1;
		int high = row;
		Random rand = new Random();
		while (i<mines) {
			r = rand.nextInt(high-low)+low;
			System.out.println(r);
			c = rand.nextInt(high-low)+low;
			if(!board[r][c].mine) {
				board[r][c].mine = true;
				i++;
			}
		}

		for (r=1; r<board.length-1; r++) {
			for (c=1; c<board[r].length-1;c++) {
				if (board[r][c-1].mine) board[r][c].mineCount ++;
				if (board[r][c+1].mine) board[r][c].mineCount ++;
				if (board[r-1][c].mine) board[r][c].mineCount ++;
				if (board[r+1][c].mine) board[r][c].mineCount ++;
				if (board[r+1][c+1].mine) board[r][c].mineCount ++;
				if (board[r-1][c+1].mine) board[r][c].mineCount ++;
				if (board[r-1][c-1].mine) board[r][c].mineCount ++;
				if (board[r+1][c-1].mine) board[r][c].mineCount ++;
			}
		}
		add(boardPanel, BorderLayout.CENTER);


		controlPanel.add(clear);

		add(controlPanel, BorderLayout.SOUTH);
		clear.addActionListener(new ClearListener());

		message.setText("Mines left: "+ minesleft);
		topPanel.add(message);
		add(topPanel, BorderLayout.NORTH);

		setVisible(true);
	}

	public static void expose(int r, int c) {
		if(board[r][c].exposed || board[r][c].mine || r==0 || c==0 || r==board.length-1 || c==board[r].length-1) return;
		board[r][c].exposed = true;
		//if(board[r][c].mine) //explodes
		Color lightBlue= new Color(240,248,255);
		board[r][c].setBackground(lightBlue);
		board[r][c].setOpaque(true);
		board[r][c].setBorderPainted(false);
		if (board[r][c].mineCount !=0)
			board[r][c].setText(Integer.toString(board[r][c].mineCount));
		if (board[r][c].mineCount !=0) return;
		expose(r, c-1);
		expose(r, c+1);
		expose(r-1, c);
		expose(r+1, c);
		expose(r-1, c-1);
		expose(r+1, c-1);
		expose(r+1, c+1);
		expose(r-1, c+1);
	}

	public static void checkwin() {
		boolean win = true;

		for (int r=1; r<board.length-1; r++) 
			for (int c=1; c<board[r].length-1;c++) {
				if (board[r][c].mine && !board[r][c].flagged) {
					win = false;
				}
			}
		if (win) {
			JOptionPane.showMessageDialog(null, "You won!");
			minesleft =mines;
			new MineSweeper();

		}else {
			if (!win && minesleft ==0)
				youloose();

		}


	}

	public static void youloose() {
		for (int r=1; r<board.length-1; r++) 
			for (int c=1; c<board[r].length-1;c++) {
				if (board[r][c].mine) {
					board[r][c].setBackground(Color.red);
					board[r][c].setOpaque(true);
					board[r][c].setBorderPainted(false);
					board[r][c].setText("*");
				}
			}
		JOptionPane.showMessageDialog(null, "You Lost!");
		minesleft = mines;
		new MineSweeper();
	}
		public static void resetgame() {

		}
		public static void flaggedmine() {
			minesleft --;
			message.setText("Mines left: "+ MineSweeper.minesleft);
		}

		class ClearListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {

				for (int r=0; r<board.length; r++) {
					for (int c=0; c<board[r].length;c++) {
						board[r][c].setBackground(Color.WHITE);
						board[r][c].setOpaque(true);
						board[r][c].setBorderPainted(false);
						board[r][c].setText("");
					}
				}
			}

		}

		public static void main(String[] args) {
			//int n =Integer.parseInt(JOptionPane.showInputDialog("How big would you like your board to be? Enter the number of rows: "));
			//row = n;
			//col = n;
			System.out.println();
			new MineSweeper();

		}

	}
