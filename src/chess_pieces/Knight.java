package chess_pieces;

import java.util.ArrayList;
import java.util.List;


import util.Position;


public class Knight implements IChessPiece {
	
	private String pieceName;
	private Colour colour;
	private Position position;
	private boolean hasMoved = false;

	public Knight(Colour colour, Position position) {
		this.colour = colour;
		this.position = position;
		pieceName = colour.getName() + "Knight";
	}

	@Override
	public List<List<Position>> deriveAllMoves() {
		List<List<Position>> listHolder = new ArrayList<List<Position>>();
		List<Position> knightPositions = Knight.getKnightAttackPositions(position);
		for (Position position : knightPositions) {
			List<Position> moveList = new ArrayList<Position>();
			moveList.add(position);
			listHolder.add(moveList);
		}
//		}
		return listHolder;
	}

	public static List<Position> getKnightAttackPositions(Position position) {
		List<Position> knightPositions = new ArrayList<Position>();
		int[] xCoords = new int[]{1, 2, 2, 1, -1, -2, -2, -1};
		int[] yCoords = new int[]{2, 1, -1, -2, -2, -1, 1, 2};
		for (int i = 0; i < xCoords.length; i++) {
			Position knightPosition = Position.createPosition(position.getXCoord() + xCoords[i],
					position.getYCoord() + yCoords[i]);
			if (knightPosition != null)
				knightPositions.add(knightPosition);
		}
		return knightPositions;
	}

	@Override
	public IChessPiece createChessPiece(Colour colour, Position position) {
		return new Knight(colour, position);
	}

	@Override
	public IChessPiece clone() {
		IChessPiece newClass = new Knight(getColour(), getPosition());
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
