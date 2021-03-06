package stalemate;

import gui.AbstractChessBoard;

import java.util.ArrayList;
import java.util.List;

import util.Position;
import chess_pieces.King;
import chess_pieces.Knight;
import chess_pieces.Pawn;
import chess_pieces.Queen;
import chess_pieces.Rook;
import chess_pieces.IChessPiece;
import chess_pieces.IChessPiece.Colour;

import control.IGameController;

public class StalemateChecker {

	private IGameController gc;
	private int previousNumberOfChessPieces = 32;
	private int remainingNumberOfMoves = 100;
	private List<ChessBoardMoment> previousMoments = new ArrayList<ChessBoardMoment>();

	public enum StalemateOption {MANDATORY_PLAYER_CANT_MOVE, MANDATORY_TOO_FEW_PIECES,
		OPTIONAL_THREE_FOLD, OPTIONAL_FIFTY_MOVE, NOT_STALEMATE};

	public StalemateChecker(IGameController gameController) {
		gc = gameController;
		CastlingOpportunities.resetStaticVariables();
	}

	public StalemateOption isStalemate() {
		if (!currentPlayerAbleToMove())
			return StalemateOption.MANDATORY_PLAYER_CANT_MOVE;
		if (tooFewPiecesForCheckmate())
			return StalemateOption.MANDATORY_TOO_FEW_PIECES;
		if (threeFoldRepetition())
			return StalemateOption.OPTIONAL_THREE_FOLD;
		if (fiftyMoveRepetition())
			return StalemateOption.OPTIONAL_FIFTY_MOVE;
		return StalemateOption.NOT_STALEMATE;
	}

	public void addChessBoardMoment(ChessBoardMoment chessBoardMoment) {
		previousMoments.add(chessBoardMoment);
	}

	private boolean currentPlayerAbleToMove() {
		chess_pieces.IChessPiece.Colour currentPlayerToMove = gc.getCurrentPlayerToMove();
		List<IChessPiece> playersPieces = gc.getChessBoard().getPlayersPieces(currentPlayerToMove);
		for (IChessPiece piece : playersPieces) {
			List<Position> allowedMoves = gc.getAllowedMovesForPiece(piece);
			if (allowedMoves.size() > 0)
				return true;
		}
		return false;
	}

	/*
	 * Returns false if either player has a pawn/queen/rook or 2 knights, knight & bishop,
	 * or at least one bishop on each colour square.
	 */
	private boolean tooFewPiecesForCheckmate() {
		Colour[] bothPlayers = {Colour.WHITE, Colour.BLACK};
		AbstractChessBoard chessBoard = gc.getChessBoard();
		for (Colour player : bothPlayers) {
			chess_pieces.IChessPiece.Colour existingBishopSquareColour = null;
			int knightCount = 0;

			List<IChessPiece> playersPieces = chessBoard.getPlayersPieces(player);
			for (IChessPiece piece : playersPieces) {
				if (piece instanceof King)
					continue;
				if (piece instanceof Queen || piece instanceof Rook || piece instanceof Pawn)
					return false;

				if (piece instanceof Knight) {
					knightCount++;
					if (knightCount > 2)
						return false;
				}
				else {// Must be a bishop
					IChessPiece.Colour bishopSquareColour = gui.AbstractChessBoard.getColourOfSquareAtPosition(piece.getPosition());
					if (existingBishopSquareColour != null &&
							!bishopSquareColour.equals(existingBishopSquareColour))
						return false;
					existingBishopSquareColour = bishopSquareColour;
				}
			}
		}
		return true;
	}

	private boolean threeFoldRepetition() {
		// Need to have been at least 8 moves in order for three-fold repetition to have taken place
		if (previousMoments.size() < 9) {
			return false;
		}

		int threeFoldCounter = 1;
		// Look back to see if there are two matches for the current moment (previousMoments.size() - 1)
		for (int i = previousMoments.size() - 1; i >= 4; i -= 4) {
			if (previousMoments.get(previousMoments.size() - 1).equals(previousMoments.get(i - 4))) {
				threeFoldCounter++;
			}
		}
		return threeFoldCounter >= 3;
	}

	private boolean fiftyMoveRepetition() {
		return remainingNumberOfMoves == 0;
	}

	public int getPreviousNumberOfChessPieces() {
		return previousNumberOfChessPieces;
	}

	public void setPreviousNumberOfChessPieces(int previousNumberOfChessPieces) {
		this.previousNumberOfChessPieces = previousNumberOfChessPieces;
	}

	public void resetToFiftyMoves() {
		remainingNumberOfMoves = 99;
	}

	public void decrementRemainingMoveNumber() {
		remainingNumberOfMoves--;
	}

	public List<ChessBoardMoment> getPreviousMoments() {
		return previousMoments;
	}

}