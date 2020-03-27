package chess_pieces;

import java.util.ArrayList;
import java.util.List;

import util.Position;


public class Bishop implements IChessPiece {
	
	private String pieceName;
	private Colour colour;
	private Position position;
	private boolean hasMoved = false;

	public Bishop(Colour colour, Position position) {
		this.colour = colour;
		this.position = position;
		this.pieceName = colour.getName() + "Bishop";
	}

	@Override
	public List<List<Position>> deriveAllMoves() {
		List<List<Position>> listHolder = new ArrayList<List<Position>>();
		IChessPiece.addDiagonalTranslations(listHolder, position);
		return listHolder;
	}

	@Override
	public IChessPiece createChessPiece(Colour colour, Position position) {
		return new Bishop(colour, position);
	}

	@Override
	public IChessPiece clone() {
    	IChessPiece newClass = new Bishop(getColour(), getPosition());
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
