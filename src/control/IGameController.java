package control;

import java.util.List;

import chess_pieces.IChessPiece;
import chess_pieces.IChessPiece.Colour;
import gui.AbstractChessBoard;
import stalemate.ChessBoardMoment;
import stalemate.StalemateChecker;
import util.Position;

public interface IGameController {
	public void reset();
	public void squareClicked(Position clickedPosition);
	public List<Position> getAllowedMovesForPiece(IChessPiece clickedPiece);
	public void determineIfCurrentPlayerIsInCheck();
	public AbstractChessBoard getChessBoard();
	public Colour getCurrentPlayerToMove();
	public ChessBoardMoment captureCurrentMoment();
	public void nullifyPieceAndPossibleMoves();
	public StalemateChecker getStalemateChecker();
	public GameControllerStateInfo getGcState();
	public void setGcState(GameControllerStateInfo gcState);
	public int getMoveNumber();
	public void undo();
	public void redo();
	public int getHighestRecordedMoveNumber();
	public IChessPiece getPieceCurrentlyHeld();
	public void setPieceCurrentlyHeld(IChessPiece pieceCurrentlyHeld);
	public List<Position> getPossibleMoves();
	public void setPossibleMoves(List<Position> possibleMoves);
}
