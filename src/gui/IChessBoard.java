package gui;

import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JFrame;

import chess_pieces.IChessPiece;
import chess_pieces.King;
import chess_pieces.Pawn;
import chess_pieces.IChessPiece.Colour;
import util.Position;

public interface IChessBoard {
	public void reset();
	public void resetAllBoardSquareColours();
	public void initialiseBoard();
	public void clearJLabels();
	public IChessPiece getPieceAtPosition(Position position);
	public void setPieceAtPosition(Position position, IChessPiece iChessPiece);
	public JLabel pieceToChessArraySquare(Position position);
	public King getKing(chess_pieces.IChessPiece.Colour currentPlayerToMove);
	public void resetBoardSquareColour(Position position);
	public void movePiece(IChessPiece pieceCurrentlyHeld,Position clickedPosition);
	public void removePiece(Position piecePosition);
	public void addPiece(IChessPiece pieceCurrentlyHeld);
	public void toggleCheckLabel(boolean flag);
	public List<IChessPiece> getPlayersPieces(Colour currentPlayerToMove);
	public void replacePawnWithUserChoice(Pawn pieceCurrentlyHeld,Position clickedPosition);
	public int getNumberOfChessPieces();
	public Map<Position, IChessPiece> getChessPiecesClone();
	public Map<Position, IChessPiece> getChessPieces();
	public void setChessPieces(Map<Position, IChessPiece> chessPieces);
	public JCheckBox getAllowUndoCheckBox();
	public void setAllowUndoCheckBox(JCheckBox allowUndoCheckBox);
	public boolean isAllowUndoOverride();
	public void setAllowUndoOverride(boolean allowUndoOverride);
	
	public static Colour getColourOfSquareAtPosition(Position position) {
		int xCoord = position.getXCoord();
		int yCoord = position.getYCoord();
		if (Math.abs(xCoord - yCoord) % 2 == 0)
			return Colour.BLACK;
		else
			return Colour.WHITE;
	}
}
