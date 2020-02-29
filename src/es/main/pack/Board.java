package es.main.pack;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.Timer;

public class Board extends JPanel implements MouseListener{
	
	private static final long serialVersionUID = 1L;
	
	private Insets defaultInsets;
	
	private static boolean isStart;
	private static boolean isRandomStart;
	
	private static int countOfTokens;
	
	private transient Image backgroundForTiles;
	
	private static transient List<Token> tokenColection;
	private static String[] ocupatedPositions = new String[9];
	
	private  String gameMode;
	private static  String stateMessage;
	private static String tokenFromTurn;
	private  String tokenChooseForPlayer;
	
	private JCheckBox randomlyTurn;
	
	private static ButtonGroup gameModeGroup = new ButtonGroup();
	private static ButtonGroup tokenFromTurnGroup = new ButtonGroup();
	
	private JLabel start;
	private static Board board = Board.getInstance();
	
	static {
		
		isStart = false;
		isRandomStart = false;
		stateMessage ="Choose gamemode and press the button";
	}
	
	private Board() {
		
		setLayout(null);
		
		defaultInsets = getInsets();
		
		loadMenus();
		
		new Timer(250,e-> repaint()).start();
		
		drawStart();
		
	}
	
	/**
	 * Create menus and add their respective listeners
	 */
	private void loadMenus() {
		
		JMenuBar menu = new JMenuBar(); // MENUBAR, Contains menus
		JMenu mode = new JMenu("Gamemode"); // menu mode,contains radio buttons
		
		JRadioButtonMenuItem choosePlayersVSMachine = new JRadioButtonMenuItem("Player vs a Dumb machine");
		gameModeGroup.add(choosePlayersVSMachine);
		
		choosePlayersVSMachine.addActionListener( e ->{
				gameMode = "IA";
				stateMessage ="Gamemode: player vs "+ gameMode;
		});
		
		JRadioButtonMenuItem choosePlayerVSPlayer = new JRadioButtonMenuItem("Player vs Player");
		gameModeGroup.add(choosePlayerVSPlayer);
		choosePlayerVSPlayer.addActionListener( e -> {
				gameMode = "player";
				stateMessage ="Gamemode: player vs "+ gameMode;
			});
		
		JMenu chooseToken = new JMenu("TokenType");
		
		JRadioButtonMenuItem chooseXToken = new JRadioButtonMenuItem("X");
		tokenFromTurnGroup.add(chooseXToken);
		chooseXToken.addActionListener(e ->tokenChooseForPlayer = "X");
		chooseToken.add(chooseXToken);

		JRadioButtonMenuItem chooseOToken = new JRadioButtonMenuItem("O");
		tokenFromTurnGroup.add(chooseOToken);
		chooseOToken.addActionListener(e ->tokenChooseForPlayer = "O");
		chooseToken.add(chooseOToken);
			
		randomlyTurn = new JCheckBox("first random turn?");
		randomlyTurn.setBackground(new Color(250,250,250));
		randomlyTurn.setBounds(defaultInsets.left+(Frame.resWidth()*610/1920),defaultInsets.top,Frame.resWidth()*180/1920,Frame.resHeight()*20/1080);;
		
		menu.add(mode);
		menu.add(chooseToken);
		mode.add(choosePlayerVSPlayer);
		mode.add(choosePlayersVSMachine);
		menu.setBounds(defaultInsets.left,defaultInsets.top,Frame.resWidth()*610/1920,Frame.resHeight()*20/1080);
		add(menu);
		add(randomlyTurn);
	}
	
	/**
	 * create the start button and a token list
	 */
	private void drawStart() {
		
		tokenColection = new ArrayList<>();
		
			for(int i = 0;i< 9;i++) {
			
				tokenColection.add(new Token());
				tokenColection.get(i).getLabel().addMouseListener(this);
			}
			
		start = new JLabel();
		Image startButton = Frame.getScaledImage(new ImageIcon("src/es/main/IMG/startButton.png").getImage(), 180, 180);
		backgroundForTiles = Frame.getScaledImage(new ImageIcon("src/es/main/IMG/board.png").getImage(), 600, 600);
		start.setIcon(new ImageIcon(startButton));
		start.setBounds(Frame.resWidth()*290/1920, Frame.resHeight()*240/1080, 200, 200);
		add(start);
		start.addMouseListener(this);
		
	}
	/**
	 * Draw and redraw the components from JPanel
	 */
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
	
		setBackground(new Color(100,150,255));
		Graphics2D g2D = (Graphics2D) g;
		// Draw a string if you have not clicked in the button
		if(!isStart) {
			
			g2D.setPaint(Color.BLACK);
			
			g2D.setFont(new Font("Arial",Font.PLAIN, Frame.resWidth()*25/1920));
			
			g2D.drawString(stateMessage, Frame.resWidth()*130/1920, Frame.resHeight()*110/1080);
		}else { // Draw the board
		
			for(Token token:tokenColection) {
			
			token.getLabel().setBounds(token.getDimension().width, token.getDimension().height, Frame.resWidth()*180/1920, Frame.resHeight()*180/1080);
			add(token.getLabel());
			
			}
		
		g2D.drawImage(new ImageIcon(backgroundForTiles).getImage(),defaultInsets.left+Frame.resWidth()*80/1920,defaultInsets.top+Frame.resHeight()*30/1080,null);
		
		}		
	}
	
	/**
	 * Listen clicks
	 */
	@Override
	public void mouseClicked(MouseEvent event) {
		
		
		if(event.getSource() == start) {
			
			countOfTokens = 0; //total tokens placed of the same type
			loadGame();
			
		} else {
			
			checkAndPutTokens(event);
		}
			
		
	}
	/**
	 * Load the game with the choosen options
	 */
	private void loadGame() {
		
		if(gameModeGroup.getSelection() == null || tokenFromTurnGroup.getSelection() == null) {
			
			stateMessage = "Please select a gamemode and token";
			
		}else {
			
			if(randomlyTurn.isSelected()) {
				
				isRandomStart = true;
			}
			
			if(isRandomStart) {
				
				randomStart();
				
			}else {
				
				tokenFromTurn = tokenChooseForPlayer;
				tokenColection.forEach(token-> token.setTokenType(tokenFromTurn));//set the token type in all tokens
			}
			
			isStart= true;
			removeAll();
		}

	}
	/**
	 * Search the place clicked and draw a token
	 */
	
	private void checkAndPutTokens(MouseEvent event) {
		
		for (Token token : tokenColection) {
			
			if (event.getSource() == token.getLabel()) {

				token.getLabel().setIcon(new ImageIcon(token.getImage())); // Set the icon that corresponds to this turn
				token.getLabel().removeMouseListener(this);
				ocupatedPositions[tokenColection.indexOf(token)] = token.getTokenType();
					
				// Compare if the token saved is the first token to have been placed
				if(ocupatedPositions[tokenColection.indexOf(token)] == tokenFromTurn) {
				
					countOfTokens++;
				}
							
				if(countOfTokens >= 3) {
					
					checkGameEnd(); 
				}
				
				if (gameMode.equals("IA") && countOfTokens < 5) {
					
					turnIA();	
				}
			}	
		}
		tokenColection.forEach(Token::nextTurn);// Change the turn of all tokens
		
		
	}
	
	/**
	 * Place a token randomly in free positions
	 */
	private static void turnIA() {
		
		tokenColection.forEach(Token::nextTurn);
		
		int indexRandom;
		while (true) {
			
			 indexRandom = new Random().nextInt(9);
			
			if (ocupatedPositions[indexRandom] == null) {
				
				tokenColection.get(indexRandom).getLabel().setIcon(new ImageIcon(tokenColection.get(indexRandom).getImage()));
				ocupatedPositions[indexRandom] = tokenColection.get(indexRandom).getTokenType();
				break;
			}
		}
		
		if(ocupatedPositions[indexRandom] == tokenFromTurn) {
			
			countOfTokens++;
		}
		if(countOfTokens >= 3) {
			
			checkGameEnd(); 
		}	
	}

	/**
	 * check if the game  is over
	 */
	private static void checkGameEnd() {
		
		String [] pos = ocupatedPositions; // Rename to make it easier to understand
		String tok = tokenColection.get(0).getTokenType(); // Token type for check this turn
		
		// HORIZONTAL 
		if(pos[0] == tok && pos[1] == tok && pos[2] == tok ||
				
		   pos[3] == tok && pos[4] == tok && pos[5] == tok ||
		   
		   pos[6] == tok && pos[7] == tok && pos[8] == tok ||
		   
		   // VERTICAL
		   pos[0] == tok && pos[3] == tok && pos[6] == tok ||
		   
		   pos[1] == tok && pos[4] == tok && pos[7] == tok ||
		   
		   pos[2] == tok && pos[5] == tok && pos[8] == tok ||
		   
		   // DIAGONAL
		   pos[0] == tok && pos[4] == tok && pos[8] == tok ||
		   
		   pos[6] == tok && pos[4] == tok && pos[2] == tok) {
			
			endGame("The winner is: " + tok);
		}
		// DRAW
		if(countOfTokens == 5) {
			
			endGame("Draw");
		}
		
	}
	/**
	 * Show a message and finish the game
	 */
	private static void endGame(String token) {
		
		JOptionPane.showMessageDialog(null, "End Game, " + token);
		System.exit(0);
	}
	
	/**
	 * Set a random token start
	 */
	private void randomStart() {
		
		if(new Random().nextInt(1000) > 499) {
			
			tokenFromTurn = "X";
			
		}else {
			
			tokenFromTurn = "O";
		}
		
		tokenColection.forEach(token-> token.setTokenType(tokenChooseForPlayer));//set the token type in all tokens
		
		if(tokenFromTurn != tokenChooseForPlayer) {
			
			if(gameMode == "IA") {
			
				turnIA();
			}
			
			JOptionPane.showMessageDialog(null, tokenFromTurn + " turn");
			tokenColection.forEach(Token::nextTurn);// Change the turn of all tokens
			return;
			
		}
		JOptionPane.showMessageDialog(null, tokenFromTurn + " turn");	
		
	}
	// Method to get board
		public static Board getInstance() {
			
			if(board == null) {
				
				board = new Board();
			}
			return board;
		}
	/**
	 *  Do nothing
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}

	
}
