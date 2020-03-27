package control;

import gui.ChessBoard;
import gui.EndGame;

import java.util.ArrayList;
import java.util.List;

import stalemate.StalemateChecker;
import util.Position;
import chess_pieces.AbstractChessPiece;
import chess_pieces.AbstractChessPiece.Colour;

/**
 * This class contains all of the fields that GameController requires at a chess position
 * (when a piece has not yet been clicked).
 * 
 * @author rhys
 *
 */
public class GameControllerStateInfo {

	Colour currentPlayerToMove;
	boolean currentPlayerIsInCheck;
	// Position of the Pawn at risk of being taken by en passant.
	Position enPassantPosition;
	int moveNumber;
	List<Position> checkBlockingMoves;

	public GameControllerStateInfo() {
		currentPlayerToMove = Colour.WHITE;
		moveNumber = 0;
	}

	public GameControllerStateInfo(Colour currentPlayerToMove, List<Position> checkBlockingMoves,
			boolean currentPlayerIsInCheck, Position enPassantPosition,
			int moveNumber) {
		super();
		this.currentPlayerToMove = currentPlayerToMove;
		this.checkBlockingMoves = checkBlockingMoves;
		this.currentPlayerIsInCheck = currentPlayerIsInCheck;
		this.enPassantPosition = enPassantPosition;
		this.moveNumber = moveNumber;
	}

	@Override
	public GameControllerStateInfo clone() {
		List<Position> newCheckBlockingMoves = null;
		if (checkBlockingMoves != null) {
			newCheckBlockingMoves = new ArrayList<Position>();
			for (Position p : checkBlockingMoves)
				newCheckBlockingMoves.add(p);
		}
		return new GameControllerStateInfo(currentPlayerToMove, newCheckBlockingMoves, currentPlayerIsInCheck, enPassantPosition, moveNumber);
	}

	public Colour getCurrentPlayerToMove() {
		return currentPlayerToMove;
	}

	public boolean isCurrentPlayerIsInCheck() {
		return currentPlayerIsInCheck;
	}

	public Position getEnPassantPosition() {
		return enPassantPosition;
	}

	public int getMoveNumber() {
		return moveNumber;
	}

	public List<Position> getCheckBlockingMoves() {
		return checkBlockingMoves;
	}

}
