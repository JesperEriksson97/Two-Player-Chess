package chess_pieces;

import java.util.ArrayList;
import java.util.List;

import util.Position;


public class King implements IChessPiece {
	
	private String pieceName;
	private Colour colour;
	private Position position;
	private boolean hasMoved = false;

	public static final int KING_POS = 5;

	public King(Colour colour, Position position) {
		this.colour = colour;
		this.position = position;
		pieceName = colour.getName() + "King";
	}

	@Override
	public List<List<Position>> deriveAllMoves() {
		List<List<Position>> listHolder = new ArrayList<List<Position>>();
		int[] xCoords = new int[]{0, 1, 1, 1, 0, -1, -1, -1};
		int[] yCoords = new int[]{1, 1, 0, -1, -1, -1, 0, 1};
		for (int i = 0; i < xCoords.length; i++) {
			List<Position> moveList = new ArrayList<Position>();
			IChessPiece.addMove(moveList, position, xCoords[i], yCoords[i]);
			if (moveList.size() > 0)
				listHolder.add(moveList);
		}
		return listHolder;
	}

	@Override
	public IChessPiece createChessPiece(Colour colour, Position position) {
		return new King(colour, position);
	}

	@Override
	public IChessPiece clone() {
		IChessPiece newClass = new King(getColour(), getPosition());
    	return newClass;
	}

	@Override
	public String getName() {
		return this.pieceName;
	}

	@Override
	public Position getPosition() {
		return this.position;
	}

	@Override
	public void setPosition(Position position) {
		this.position = position;
		
	}

	@Override
	public Colour getColour() {
		return this.colour;
	}

	@Override
	public void markAsHavingMoved() {
		this.hasMoved = true;
		
	}

	@Override
	public boolean hasMoved() {
		return this.hasMoved;
	}

	@Override
	public String getPieceName() {
		return this.pieceName;
	}
	
	public String toString() {
		return pieceName + ", " + position + ", " + hasMoved;
	}

}
