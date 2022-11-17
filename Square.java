
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class Square extends JButton{
	public boolean mine = false;
	int mineCount = 0;
	private int row, col;
	boolean flagged = false;
	boolean exposed = false;


	public Square(int r, int c) {
		row = r;
		col = c;
		addMouseListener(new SquareListener());
	}

	class SquareListener implements MouseListener{
		public void mouseClicked(MouseEvent e) {
			if (e.getButton()==3) {
				if(!flagged) {
					flagged = true;
					setBackground(Color.yellow);
					setOpaque(true);
					setBorderPainted(false);
					setText("|>");
					
					MineSweeper.flaggedmine();
				}else {
					flagged = false;
					setBackground(Color.white);
					setOpaque(false);
					setBorderPainted(true);
					setText("");
					MineSweeper.minesleft++;
					MineSweeper.message.setText("Mines left: "+ MineSweeper.minesleft);
				}
			}else if(e.getButton()==1) {
				if(flagged)return;
				if (mine) {
					setBackground(Color.red);
					setOpaque(true);
					setBorderPainted(false);
					setText("*");
					MineSweeper.youloose();
				}else {
					if (mineCount == 0)MineSweeper.expose(row, col);
					else {
						exposed = true;
						Color lightBlue= new Color(240,248,255);
						setBackground(lightBlue);
						setOpaque(true);
						setBorderPainted(false);
						if (mineCount!=0)
							setText(Integer.toString(mineCount));
						if(getText()=="1>")setText("");
					}
				}
			}
			MineSweeper.checkwin();
		}
		public void mousePressed(MouseEvent e) {


		}
		public void mouseReleased(MouseEvent e) {


		}
		public void mouseEntered(MouseEvent e) {


		}
		public void mouseExited(MouseEvent e) {


		}

	}
}
