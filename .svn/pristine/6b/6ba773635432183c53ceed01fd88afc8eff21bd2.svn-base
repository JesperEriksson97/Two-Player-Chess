package chess_pieces;

import java.util.ArrayList;
import java.util.List;

import util.Position;


public class Queen extends AbstractChessPiece {

	public Queen(Colour colour, Position position) {
		super(colour, position);
		pieceName = colour.getName() + "Queen";
	}

	@Override
	public List<List<Position>> deriveAllMoves() {
		List<List<Position>> listHolder = new ArrayList<List<Position>>();
		AbstractChessPiece.addStraightTranslations(listHolder, position);
		AbstractChessPiece.addDiagonalTranslations(listHolder, position);
		return listHolder;
	}

}
