package chess_pieces;

import java.util.ArrayList;
import java.util.List;

import util.Position;


public class Knight extends AbstractChessPiece {

	public Knight(Colour colour, Position position) {
		super(colour, position);
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

}
