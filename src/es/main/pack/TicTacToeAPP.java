package es.main.pack;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;



public class TicTacToeAPP {

	public static void main(String[] args) {
		
			new Frame();
	}

}


// MAIN FRAME
	class Frame extends JFrame{

		private static final long serialVersionUID = 1L;
		
		private static Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
		
		public Frame() {
		    setIconImage(new ImageIcon("src/IMG/chessIcon.png").getImage());
		    
			setVisible(true);
			
			setResizable(false);
			
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			add(Board.getInstance());// Add JPanel
			
			setBounds(resWidth() * 550 /1920,resHeight() * 200 / 1080,resWidth() * 800 /1920, resHeight() * 700/1080);
			
		}
		
		// Return width of the screen
		public static int resWidth() {
			
			return (int)resolution.getWidth();
			
		}
		
		// Return height of the screen
		public static int resHeight() {
			
			return (int)resolution.getHeight();
		}
		// Return a rescaled image based on 1920 x 1080 resolution
		public static Image getScaledImage(Image image,int width,int height) {
			
			return image.getScaledInstance(resWidth()*width/1920,resHeight()* height/1080, 0);
			
		}
	}
