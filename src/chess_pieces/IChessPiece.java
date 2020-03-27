package chess_pieces;

import java.util.ArrayList;
import java.util.List;

import util.Position;

public interface IChessPiece {

	public enum Colour {
		WHITE("White"), BLACK("Black");
				
		private String name;

		Colour(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
	}
	
	public boolean equals(Object candidate);
	public int hashCode();
	public IChessPiece createChessPiece(Colour colour, Position position);
	public abstract List<List<Position>> deriveAllMoves();
	public IChessPiece clone();
	
	// --- Getters and Setters ---
	public String toString();
	public String getName();
	public Position getPosition();
	public void setPosition(Position position);
	public Colour getColour();
	public void markAsHavingMoved();
	public boolean hasMoved();
	public String getPieceName();
	
	
	// --- Static functions --- 
	
	static boolean addMove(List<Position> moveList, Position currentPosition, int xTrans, int yTrans) {
		Position subsequentPosition =
			Position.createPosition(xTrans + currentPosition.getXCoord(),
					yTrans + currentPosition.getYCoord());
		if (subsequentPosition != null) {
			moveList.add(subsequentPosition);
			return true;
		}
		return false;
	}
	
	static void addStraightTranslations(List<List<Position>> listHolder, Position currentPosition) {
    	List<Position> moveListUp = new ArrayList<Position>();
    	for (int yTrans = 1; ; yTrans++) {
    		boolean result = addMove(moveListUp, currentPosition, 0, yTrans);
    		if (result == false)
    			break;
    	}
    	if (moveListUp.size() > 0)
    		listHolder.add(moveListUp);

    	List<Position> moveListDown = new ArrayList<Position>();
    	for (int yTrans = -1; ; yTrans--) {
    		boolean result = addMove(moveListDown, currentPosition, 0, yTrans);
    		if (result == false)
    			break;
    	}
    	if (moveListDown.size() > 0)
    		listHolder.add(moveListDown);

    	List<Position> moveListRight = new ArrayList<Position>();
    	for (int xTrans = 1; ; xTrans++) {
    		boolean result = addMove(moveListRight, currentPosition, xTrans, 0);
    		if (result == false)
    			break;
    	}
    	if (moveListRight.size() > 0)
    		listHolder.add(moveListRight);

    	List<Position> moveListLeft = new ArrayList<Position>();
    	for (int xTrans = -1; ; xTrans--) {
    		boolean result = addMove(moveListLeft, currentPosition, xTrans, 0);
    		if (result == false)
    			break;
    	}
    	if (moveListLeft.size() > 0)
    		listHolder.add(moveListLeft);
    }
	
	static void addDiagonalTranslations(List<List<Position>> listHolder, Position currentPosition) {
    	List<Position> moveListNE = new ArrayList<Position>();
    	for (int xTrans = 1, yTrans = 1; ; xTrans++, yTrans++) {
    		boolean result = addMove(moveListNE, currentPosition, xTrans, yTrans);
    		if (result == false)
    			break;
    	}
    	if (moveListNE.size() > 0)
    		listHolder.add(moveListNE);

    	List<Position> moveListNW = new ArrayList<Position>();
    	for (int xTrans = -1, yTrans = 1; ; xTrans--, yTrans++) {
    		boolean result = addMove(moveListNW, currentPosition, xTrans, yTrans);
    		if (result == false)
    			break;
    	}
    	if (moveListNW.size() > 0)
    		listHolder.add(moveListNW);

    	List<Position> moveListSE = new ArrayList<Position>();
    	for (int xTrans = 1, yTrans = -1; ; xTrans++, yTrans--) {
    		boolean result = addMove(moveListSE, currentPosition, xTrans, yTrans);
    		if (result == false)
    			break;
    	}
    	if (moveListSE.size() > 0)
    		listHolder.add(moveListSE);

    	List<Position> moveListSW = new ArrayList<Position>();
    	for (int xTrans = -1, yTrans = -1; ; xTrans--, yTrans--) {
    		boolean result = addMove(moveListSW, currentPosition, xTrans, yTrans);
    		if (result == false)
    			break;
    	}
    	if (moveListSW.size() > 0)
    		listHolder.add(moveListSW);
    }
}
