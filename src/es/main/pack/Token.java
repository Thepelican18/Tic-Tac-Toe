package es.main.pack;

import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Token {

	private Dimension tokDimension;
	
	private JLabel tokLabel;
	
	private Image tokImage;
	private Image XToken = Frame.getScaledImage(new ImageIcon("src/es/main/IMG/XToken.png").getImage(), 140, 140);
	private Image OToken = Frame.getScaledImage(new ImageIcon("src/es/main/IMG/OToken.png").getImage(), 140, 140);

	private String tokenType;
	
	private static int nextDimensionX = Frame.resWidth() * 105 / 1920;
	private static int nextDimensionY = Frame.resHeight() * 40 / 1080;

	public Token() {

		tokLabel = new JLabel();

		tokDimension = new Dimension(nextDimensionX, nextDimensionY);

		buildPosTokens();

	}
	/**
	 * Place tokens without images adapted to the board
	 */
	private void buildPosTokens() {

		nextDimensionX += Frame.resWidth() * 205 / 1920;

		if (nextDimensionX > 3 * (Frame.resWidth() * 205 / 1920)) {

			nextDimensionX = Frame.resWidth() * 105 / 1920;
			nextDimensionY += Frame.resHeight() * 200 / 1080;
		}
	}

	/**
	 * Change the turn
	 */
	public void nextTurn() {
		
		if (tokenType == "X") {
			tokenType = "O";
			tokImage = OToken;

		} else {
			tokenType = "X";
			tokImage = XToken;
		}
	}

	//SETTERS
	public void setDimension(Dimension sqDimension) {
		this.tokDimension = sqDimension;
	}

	//Set the first image in the token
	public void setTokenType(String team) {
		
		tokenType = team;
		
		if (team == "X") {
			
			tokImage = XToken;
			
		} else {
			
			tokImage = OToken;
		}
	}
	//GETTERS
	public Image getImage() {
		return tokImage;
	}

	public JLabel getLabel() {
		return tokLabel;
	}

	public Dimension getDimension() {
		return tokDimension;
	}

	public String getTokenType() {
		return tokenType;
	}

}
