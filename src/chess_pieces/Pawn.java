package chess_pieces;

import java.util.ArrayList;
import java.util.List;

import util.Position;

public class Pawn implements IChessPiece {
	private String pieceName;
	private Colour colour;
	private Position position;
	private boolean hasMoved = false;
	
	public Pawn(Colour colour, Position position) {
		this.colour = colour;
		this.position = position;
		pieceName = colour.getName() + "Pawn";
	}

	@Override
	public List<List<Position>> deriveAllMoves() {
		List<List<Position>> listHolder = new ArrayList<List<Position>>();
		List<Position> moveList1 = new ArrayList<Position>();
		IChessPiece.addMove(moveList1, position, 0,
				(colour == Colour.WHITE) ? 1 : -1);

		if (position.getYCoord() == 2 && colour == Colour.WHITE) {
			IChessPiece.addMove(moveList1, position, 0, 2);
		}
		else if (position.getYCoord() == 7 && colour == Colour.BLACK) {
			IChessPiece.addMove(moveList1, position, 0, -2);
		}

		if (moveList1.size() > 0)
			listHolder.add(moveList1);

		// Add the positions the pawn can move into when capturing pieces
		List<Position> moveList2 = new ArrayList<Position>();
		IChessPiece.addMove(moveList2, position, 1,
				(colour == Colour.WHITE) ? 1 : -1);
		if (moveList2.size() > 0)
			listHolder.add(moveList2);

		List<Position> moveList3 = new ArrayList<Position>();
		IChessPiece.addMove(moveList3, position, -1,
				(colour == Colour.WHITE) ? 1 : -1);
		if (moveList3.size() > 0)
			listHolder.add(moveList3);

		return listHolder;
	}

	public boolean adjacentToEnPassantPosition(Position enPassantPosition) {
		// Needs to have the same y coordinate, and a difference of 1 between the x coordinates
		return (position.getYCoord() == enPassantPosition.getYCoord())
		         && (Math.abs(position.getXCoord() - enPassantPosition.getXCoord()) == 1);
	}

	public Position finalPositionAfterEnPassant(Position enPassantPosition) {
		int xCoord = enPassantPosition.getXCoord();
		int yCoord = enPassantPosition.getYCoord() + ((colour == Colour.WHITE) ? 1 : -1);
		return Position.createPosition(xCoord, yCoord);
	}

	
	public IChessPiece createChessPiece(Colour colour, Position position) {
		return new Pawn(colour, position);
	}

	@Override
	public IChessPiece clone() {
		IChessPiece newClass = new Pawn(getColour(), getPosition());
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
		return pieceName + ", " + this.position + ", " + hasMoved;
	}

}