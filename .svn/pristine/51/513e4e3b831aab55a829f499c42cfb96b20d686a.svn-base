package undo_redo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import chess_pieces.AbstractChessPiece;
import gui.ChessBoard;
import control.GameController;
import control.GameControllerStateInfo;
import stalemate.ChessBoardMoment;
import util.Position;

/**
 * Template design pattern in use here.
 * 
 * @author rhys
 *
 */
public class UndoRedoMove {

	private GameController gameController;
	private ChessBoard chessBoard;
	private List<ChessBoardMoment> previousMoments;
	private int moveNumber;
	private int highestMoveNumber;
	enum Change {UNDO, REDO};

	public UndoRedoMove(GameController gameController, ChessBoard chessBoard,
			List<ChessBoardMoment> previousMoments) {
		super();
		this.gameController = gameController;
		this.chessBoard = chessBoard;
		this.previousMoments = previousMoments;
	}

	public void undo() {
		changeBoard(Change.UNDO);
	}

	public void redo() {
		changeBoard(Change.REDO);
	}

	private void changeBoard(Change change) {
		for (ChessBoardMoment c : previousMoments) {
			Set<Position> blah = c.getChessPieces().keySet();
			Set<Position> sortedSet = new TreeSet<Position>();
			for (Position p : blah)
				sortedSet.add(p);
		}
		ChessBoardMoment desiredChessBoardMoment = null;
		if (change == Change.UNDO) {
			desiredChessBoardMoment = getRequiredMomentForUndo();
		}
		else if (change == Change.REDO) {
			desiredChessBoardMoment = getRequiredMomentForRedo();
		}
		setGameControllerStateInfo(desiredChessBoardMoment);
		setChessPieces(desiredChessBoardMoment);
		updateVisualBoard(getCurrentMoment().getChessPieces(), desiredChessBoardMoment.getChessPieces());
		chessBoard.toggleCheckLabel(desiredChessBoardMoment.getGCState().isCurrentPlayerIsInCheck());
		// The following is for if the player clicks undo/redo whilst a piece is selected. The colouring
		// of the pieces available moves needed to be cleared.
		if (gameController.getPieceCurrentlyHeld() != null) {
			gameController.nullifyPieceAndPossibleMoves();
			chessBoard.resetAllBoardSquareColours();
		}
		if (change == Change.UNDO)
			moveNumber--;
		else if (change == Change.REDO)
			moveNumber++;
	}

	private ChessBoardMoment getCurrentMoment() {
		return previousMoments.get(moveNumber);
	}

	private ChessBoardMoment getRequiredMomentForUndo() {
		ChessBoardMoment retMoment = previousMoments.get(moveNumber - 1);
		return retMoment;
	}

	private ChessBoardMoment getRequiredMomentForRedo() {
		ChessBoardMoment retMoment =  previousMoments.get(moveNumber + 1);
		return retMoment;
	}

	private void setGameControllerStateInfo(ChessBoardMoment chessBoardMoment) {
		GameControllerStateInfo gcState = chessBoardMoment.getGCState();
		gameController.setGcState(gcState);
	}

	private void setChessPieces(ChessBoardMoment chessBoardMoment) {
		Map<Position, AbstractChessPiece> chessPieces = chessBoardMoment.getChessPieces();
		chessBoard.setChessPieces(chessPieces);
	}

	private void updateVisualBoard(
			Map<Position, AbstractChessPiece> currentPieces,
			Map<Position, AbstractChessPiece> intendedPieces) {
		AbstractChessPiece pieceToAdd = null;
		AbstractChessPiece pieceToDelete = null;

		Set<Position> currentPositions = currentPieces.keySet();
		Set<Position> intendedPositions = intendedPieces.keySet();

		List<AbstractChessPiece> piecesToDelete = new ArrayList<AbstractChessPiece>();
		List<AbstractChessPiece> piecesToAdd = new ArrayList<AbstractChessPiece>();
		
		for (Position intendedPosition : intendedPositions) {
			if (!currentPositions.contains(intendedPosition)
					|| !intendedPieces.get(intendedPosition).equals(currentPieces.get(intendedPosition))) {
				pieceToAdd = intendedPieces.get(intendedPosition);
				if (!pieceToAdd.getPosition().equals(intendedPosition)) {
					assert false;
				}
				piecesToAdd.add(pieceToAdd);
			}
		}
		for (Position currentPosition : currentPositions) {
			if (!intendedPositions.contains(currentPosition)
					|| !currentPieces.get(currentPosition).equals(intendedPieces.get(currentPosition))) {
				pieceToDelete = currentPieces.get(currentPosition);
				assert pieceToDelete.getPosition().equals(currentPosition);
				piecesToDelete.add(pieceToDelete);
			}
		}

		for (AbstractChessPiece piece : piecesToDelete)
		    chessBoard.removePiece(piece.getPosition());
		for (AbstractChessPiece piece : piecesToAdd)
		    chessBoard.addPiece(piece);
	}

	/**
	 * This is only called when the player makes a move rather than clicking undo/redo.
	 * 
	 * @param newMoveNumber
	 */
	public void setHighestMoveNumber(int newMoveNumber) {
		highestMoveNumber = newMoveNumber;
		moveNumber = newMoveNumber;
		trimPreviousMoments();
	}

	private void trimPreviousMoments() {
		while (previousMoments.size() > (highestMoveNumber)) {
			previousMoments.remove(previousMoments.size() - 1);
		}
	}

	public int getHighestMoveNumber() {
		return highestMoveNumber;
	}
}