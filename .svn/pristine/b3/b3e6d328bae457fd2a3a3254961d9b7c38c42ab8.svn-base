package chess_pieces;

import java.util.ArrayList;
import java.util.List;

import util.Position;


public class Rook extends AbstractChessPiece {

	public Rook(Colour colour, Position position) {
		super(colour, position);
		pieceName = colour.getName() + "Rook";
	}

	@Override
	public List<List<Position>> deriveAllMoves() {
		List<List<Position>> listHolder = new ArrayList<List<Position>>();
		AbstractChessPiece.addStraightTranslations(listHolder, position);
		return listHolder;
	}

}
