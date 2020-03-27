package stalemate;

import java.util.Map;
import java.util.Set;

import util.Position;
import chess_pieces.AbstractChessPiece;
import control.GameControllerStateInfo;

public class ChessBoardMoment {

	private Map<Position, AbstractChessPiece> chessPieces;
	private CastlingOpportunities castlingOpportunities;
	private CastlingPiecesMovementTracker castlingPiecesMovementTracker;
	private GameControllerStateInfo gcState;

	public ChessBoardMoment(Map<Position, AbstractChessPiece> chessPieces,
			CastlingOpportunities castlingOpportunities,
			CastlingPiecesMovementTracker castlingPiecesMovementTracker,
			GameControllerStateInfo gcState) {
		super();
		this.chessPieces = chessPieces;
		this.castlingOpportunities = castlingOpportunities;
		this.castlingPiecesMovementTracker = castlingPiecesMovementTracker;
		this.gcState = gcState;
	}

	@Override
	public boolean equals(Object candidate) {
		if (this == candidate)
			return true;
		
		if (!(candidate instanceof ChessBoardMoment))
			return false;

		ChessBoardMoment confirmed = (ChessBoardMoment) candidate;
		boolean sameCastlingOpportunities = getCastlingOpportunities().equals(confirmed.getCastlingOpportunities());
		boolean sameCastlingPiecesMovementTracker = getCastlingPiecesMovementTracker().equals(confirmed.getCastlingPiecesMovementTracker());
		boolean sameEnPassantPosition = getEnPassantPosition() != null && getEnPassantPosition().equals(confirmed.getEnPassantPosition())
		|| getEnPassantPosition() == null && confirmed.getEnPassantPosition() == null;
		boolean samePiecePositions = getChessPieces().equals(confirmed.getChessPieces());
		return sameCastlingOpportunities && sameCastlingPiecesMovementTracker
		&& sameEnPassantPosition && samePiecePositions;
	}

	@Override
	public int hashCode() {
		return 1;
	}

	public Map<Position, AbstractChessPiece> getChessPieces() {
		return chessPieces;
	}

	public CastlingOpportunities getCastlingOpportunities() {
		return castlingOpportunities;
	}

	public CastlingPiecesMovementTracker getCastlingPiecesMovementTracker() {
		return castlingPiecesMovementTracker;
	}

	public Position getEnPassantPosition() {
		return gcState.getEnPassantPosition();
	}

	public GameControllerStateInfo getGCState() {
		return gcState;
	}
}