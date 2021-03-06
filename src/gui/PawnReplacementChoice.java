package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import util.Position;
import chess_pieces.Bishop;
import chess_pieces.IChessPiece;
import chess_pieces.Knight;
import chess_pieces.Pawn;
import chess_pieces.Queen;
import chess_pieces.Rook;
import control.IGameController;

public class PawnReplacementChoice {

	AbstractChessBoard chessBoard;
	IGameController gameController;
	JOptionPane optionPane;
	JDialog dialog;
	IChessPiece pieceCurrentlyHeld;
	Position clickedPosition;

	public PawnReplacementChoice(AbstractChessBoard chessBoard2, IGameController gc,
			IChessPiece pieceCurrentlyHeld,Position clickedPosition) {
		this.chessBoard = chessBoard2;
		this.gameController = gc;
		this.pieceCurrentlyHeld = pieceCurrentlyHeld;
		this.clickedPosition = clickedPosition;
	}

	public void replace() {
		JButton[] optionButtons = new JButton[4];
		for (int i = 0; i < 4; i++) {
			String choice = null;
			switch(i) {
			    case 0: choice = "Bishop"; break;
			    case 1: choice = "Knight"; break;
			    case 2: choice = "Queen"; break;
			    case 3: choice = "Rook"; break;
			}
			JButton button = new JButton(choice);
			button.addActionListener(generateActionListener(choice));
			optionButtons[i] = button;
		}
		optionPane = new JOptionPane("Choose a piece to replace the pawn.", JOptionPane.QUESTION_MESSAGE,
				JOptionPane.DEFAULT_OPTION, null, optionButtons);

		dialog = new JDialog(chessBoard, true);
		dialog.setContentPane(optionPane);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		optionPane.addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent e) {
						if (dialog.isVisible() && (e.getSource() == optionPane)) {
							dialog.setVisible(false);
						}
					}
				});
		dialog.pack();
		dialog.setLocationRelativeTo(chessBoard);
		dialog.setVisible(true);
	}
	private ActionListener generateActionListener(final String choice) {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				implementPawnReplacementChoice(choice);
			}
		};
	}

	private void implementPawnReplacementChoice(String choice) {
		if (choice.equals("Queen"))
			pieceCurrentlyHeld = new Queen(pieceCurrentlyHeld.getColour(), clickedPosition);
		else if (choice.equals("Knight"))
			pieceCurrentlyHeld = new Knight(pieceCurrentlyHeld.getColour(), clickedPosition);
		else if (choice.equals("Rook"))
			pieceCurrentlyHeld = new Rook(pieceCurrentlyHeld.getColour(), clickedPosition);
		else if (choice.equals("Bishop"))
			pieceCurrentlyHeld = new Bishop(pieceCurrentlyHeld.getColour(), clickedPosition);
		else
			assert false;
		chessBoard.setPieceAtPosition(clickedPosition, pieceCurrentlyHeld);
		chessBoard.movePiece(pieceCurrentlyHeld, clickedPosition);
		// Call this again to see if the player is in check now that a replacement piece has been selected.
		gameController.determineIfCurrentPlayerIsInCheck();
		optionPane.firePropertyChange("a", false, true);
	}

}