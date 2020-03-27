package chess_pieces;

import java.util.ArrayList;
import java.util.List;

import util.Position;


public class Bishop extends AbstractChessPiece {

	public Bishop(Colour colour, Position position) {
		super(colour, position);
		pieceName = colour.getName() + "Bishop";
	}

	@Override
	public List<List<Position>> deriveAllMoves() {
		List<List<Position>> listHolder = new ArrayList<List<Position>>();
		AbstractChessPiece.addDiagonalTranslations(listHolder, position);
		return listHolder;
	}

}
