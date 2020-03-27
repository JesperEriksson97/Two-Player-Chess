package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import util.Position;
import chess_pieces.AbstractChessPiece;
import chess_pieces.King;
import chess_pieces.Pawn;
import chess_pieces.AbstractChessPiece.Colour;
import control.GameController;


@SuppressWarnings("serial")
public class ChessBoard extends JFrame {
	
	private static final int CHESSBOARD_WIDTH = 8;
	private static final int CHESSBOARD_LENGTH = 8;

	private GameController gc;
	private ChessBoard chessBoard;
	private JPanel contentPanel = new JPanel();
	private JPanel gridJPanel = new JPanel();
	private JToolBar soleJToolBar = new JToolBar();
	private JButton newGameButton = new JButton("New game");
	private JButton undoButton = new JButton("Undo");
	private JButton redoButton = new JButton("Redo");
	private JCheckBox allowUndoCheckBox = new JCheckBox("Allow undo");
	private JLabel checkNotifier = new JLabel("Check");
	private JLabel[][] chessSquareArray = new JLabel[CHESSBOARD_LENGTH][CHESSBOARD_WIDTH];
	private boolean allowUndoOverride;

	private Map<Position, AbstractChessPiece> chessPieces;

	public ChessBoard(final GameController gc) {
		this.gc = gc;
		chessBoard = this;
		setSize(600, 600);
		setContentPane(contentPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Two player chess");

		contentPanel.setLayout(new BorderLayout());
		initializeGridJPanel();
		contentPanel.add(gridJPanel, BorderLayout.CENTER);
		initializeSoleJToolBar();
		contentPanel.add(soleJToolBar, BorderLayout.NORTH);

		newGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				NewGameChoice sole = new NewGameChoice(chessBoard);
				sole.show();
			}
		});

		undoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				gc.undo();
				redoButton.setEnabled(true);
				if (gc.getMoveNumber() == 0) {
					undoButton.setEnabled(false);
				}
			}
		});

		redoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				gc.redo();
				undoButton.setEnabled(true);
				if (gc.getMoveNumber() == gc.getHighestRecordedMoveNumber()) {
					redoButton.setEnabled(false);
				}
			}
		});
		
		initializeChessSquareArray();

		setVisible(true);
	}

	public void reset() {
		gc.reset();
		clearJLabels();
		resetAllBoardSquareColours();
		initialiseBoard();
		toggleCheckLabel(false);
		undoButton.setEnabled(false);
		redoButton.setEnabled(false);
		allowUndoCheckBox.setEnabled(true);
		allowUndoOverride = false;
	}

	public void resetAllBoardSquareColours() {
		for (int i = 0; i < CHESSBOARD_LENGTH; i++) {
			for (int j = 0; j < CHESSBOARD_WIDTH; j++) {
				JLabel square = chessSquareArray[i][j];
				if (square.getName().charAt(0) == 'g')
					square.setBackground(Color.GRAY);
			    else
			    	square.setBackground(Color.WHITE);
			}
		}
	}

	private void initializeGridJPanel() {
        GridLayout gridLayout = new GridLayout();
        gridLayout.setRows(CHESSBOARD_LENGTH);
        gridLayout.setColumns(CHESSBOARD_WIDTH);
        gridJPanel.setLayout(gridLayout);
	}

	private void initializeSoleJToolBar() {
        soleJToolBar.setOrientation(JToolBar.HORIZONTAL);
        soleJToolBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        soleJToolBar.setFloatable(false);
        soleJToolBar.add(newGameButton);
        soleJToolBar.add(new JToolBar.Separator());
        soleJToolBar.add(undoButton);
        undoButton.setEnabled(false);
        soleJToolBar.add(new JToolBar.Separator());
        soleJToolBar.add(redoButton);
        redoButton.setEnabled(false);
        soleJToolBar.add(new JToolBar.Separator());
        soleJToolBar.add(allowUndoCheckBox);
        allowUndoCheckBox.setSelected(true);
        allowUndoCheckBox.setEnabled(true);
        soleJToolBar.add(new JToolBar.Separator());
        checkNotifier.setForeground(Color.RED);
        checkNotifier.setVisible(false);
        soleJToolBar.add(checkNotifier);
	}

	private void initializeChessSquareArray() {
		boolean bool1 = false, bool2 = false;
		for (int i = 0; i < CHESSBOARD_LENGTH; i++) {
			for (int j = 0; j < CHESSBOARD_WIDTH; j++) {
				chessSquareArray[i][j] = new JLabel((Icon)null, JLabel.CENTER);
				chessSquareArray[i][j].setOpaque(true);
				//chessSquareArray[i][j] = new JLabel("[" + i + "][" + j + "]");
				if (bool1 ^ bool2) {
					chessSquareArray[i][j].setBackground(Color.GRAY);
					chessSquareArray[i][j].setName("gray" + i + j);
				}
				else {
					chessSquareArray[i][j].setBackground(Color.WHITE);
					chessSquareArray[i][j].setName("white" + i + j);
				}
				gridJPanel.add(chessSquareArray[i][j]);
				bool2 = (bool2 == true) ? false : true;

				chessSquareArray[i][j].addMouseListener(new MouseAdapter() {
//					@Override
//					public void mouseEntered(MouseEvent mouseEvent) {
//						mouseEvent.getComponent().setBackground(Color.GREEN);
//					}

//					@Override
//					public void mouseExited(MouseEvent mouseEvent) {
//						Component currentJLabel = (JLabel) mouseEvent.getComponent();
//						if (currentJLabel.getName().charAt(0) == 'g')
//							currentJLabel.setBackground(Color.GRAY);
//						else
//							currentJLabel.setBackground(Color.WHITE);
//					}

					@Override
					public void mouseReleased(MouseEvent mouseEvent) {
						Component currentJLabel = (JLabel) mouseEvent.getComponent();
						String labelName = currentJLabel.getName();
						Position clickedPosition = arrayToBoard(labelName.charAt(labelName.length() - 2) - '0',
								labelName.charAt(labelName.length() - 1) - '0');
						gc.squareClicked(clickedPosition);
					}
                });
			}
			bool1 = (bool1 == true) ? false : true;
		}
	}

	public void initialiseBoard() {
		chessPieces = new HashMap<Position, AbstractChessPiece>();
		addInitialSixteenPieces();
		Set<Position> positionSet = chessPieces.keySet();
		for (Position position : positionSet) {
			AbstractChessPiece cp = chessPieces.get(position);
			paintBoardSquare(cp.getName(), position);
		}
	}

	/*
	 * Called from GameController when the new game button is clicked.
	 */
	public void clearJLabels() {
		Set<Position> piecePositionSet = chessPieces.keySet();
		for (Position piecePosition : piecePositionSet)
		    pieceToChessArraySquare(piecePosition).setIcon(null);
	}

	private void addInitialSixteenPieces() {
		Colour colour = null;
		int xCoord = 0, yCoord = 0;

		for (int i = 1; i <= 2; i++) {
			colour = (i == 1) ? Colour.WHITE : Colour.BLACK;
			yCoord = (i == 1) ? 2 : 7;
			for (xCoord = 1; xCoord <= CHESSBOARD_WIDTH; xCoord++) {
				Position position = Position.createPosition(xCoord, yCoord);
				setPieceAtPosition(position, AbstractChessPiece.createChessPiece("Pawn", colour, position));
			}
		}

		for (int i = 1; i <= 2; i++) {
			colour = (i == 1) ? Colour.WHITE : Colour.BLACK;
			yCoord = (i == 1) ? 1 : 8;
			for (xCoord = 1; xCoord <= CHESSBOARD_WIDTH; xCoord++) {
				Position position = Position.createPosition(xCoord, yCoord);
				switch (xCoord) {
				    case 1: case 8:
				    	setPieceAtPosition(position, AbstractChessPiece.createChessPiece("Rook", colour, position));
				    	break;
				    case 2: case 7:
				    	setPieceAtPosition(position, AbstractChessPiece.createChessPiece("Knight", colour, position));
				    	break;
				    case 3: case 6:
				    	setPieceAtPosition(position, AbstractChessPiece.createChessPiece("Bishop", colour, position));
				    	break;
				    case 4:
				    	setPieceAtPosition(position, AbstractChessPiece.createChessPiece("Queen", colour, position));
				    	break;
				    case 5:
				    	setPieceAtPosition(position, AbstractChessPiece.createChessPiece("King", colour, position));
				    	break;
				}
			}
		}
	}

	private void paintBoardSquare(String pieceName, Position position) {
        InputStream inIcon = ClassLoader.getSystemResourceAsStream(pieceName + ".gif");
        assert inIcon != null : "inIcon should not be null.";
        BufferedImage imgIcon = null;

		try {
			imgIcon = ImageIO.read(inIcon);
		} catch (Exception e) {
			System.out.println("Error: Could not locate \"" + pieceName + ".gif\" in the current folder.");
			assert false;
		}


		pieceToChessArraySquare(position).setIcon(new ImageIcon(imgIcon));
	}

	public AbstractChessPiece getPieceAtPosition(Position position) {
		return chessPieces.get(position);
	}

	public void setPieceAtPosition(Position position, AbstractChessPiece newPiece) {
		assert position != null;
		assert newPiece != null;
		assert position.equals(newPiece.getPosition()): "position = " + position
		+ ", and newPiece.getPosition() = " + newPiece.getPosition();
		chessPieces.put(position, newPiece);
	}

	public static Colour getColourOfSquareAtPosition(Position position) {
		int xCoord = position.getXCoord();
		int yCoord = position.getYCoord();
		if (Math.abs(xCoord - yCoord) % 2 == 0)
			return Colour.BLACK;
		else
			return Colour.WHITE;
	}

	private Position arrayToBoard(int xCoord, int yCoord) {
		xCoord = 7 - xCoord;
		xCoord++;
		yCoord++;
		int z = xCoord;
		xCoord = yCoord;
		yCoord = z;
		return Position.createPosition(xCoord, yCoord);
	}

	public JLabel pieceToChessArraySquare(Position position) {
		int z = position.getXCoord();
		int xCoord = position.getYCoord();
		int yCoord = z;
		xCoord--;
		yCoord--;
		xCoord = 7 - xCoord;
		return chessSquareArray[xCoord][yCoord];
	}

	public King getKing(Colour currentPlayerToMove) {
		Set<Position> chessPieceSet = chessPieces.keySet();
		for (Position position : chessPieceSet) {
			AbstractChessPiece chessPiece = chessPieces.get(position);
			if (chessPiece instanceof King && chessPiece.getColour() == currentPlayerToMove)
				return (King)chessPiece;
		}
		assert false : "There should always be a king of either colour";
		return null;
	}

	public void resetBoardSquareColour(Position position) {
		JLabel square = pieceToChessArraySquare(position);
		if (square.getName().charAt(0) == 'g')
			square.setBackground(Color.GRAY);
	    else
	    	square.setBackground(Color.WHITE);
	}

	public void movePiece(AbstractChessPiece pieceCurrentlyHeld,
			Position clickedPosition) {
		removePiece(pieceCurrentlyHeld.getPosition());
		pieceCurrentlyHeld.setPosition(clickedPosition);
		addPiece(pieceCurrentlyHeld);
		if (allowUndoCheckBox.isSelected() || allowUndoOverride) {
		    undoButton.setEnabled(true);
		    redoButton.setEnabled(false);
		}
	}

	public void removePiece(Position piecePosition) {
		pieceToChessArraySquare(piecePosition).setIcon(null);
		chessPieces.remove(piecePosition);
	}

	public void addPiece(AbstractChessPiece pieceToAdd) {
		setPieceAtPosition(pieceToAdd.getPosition(), pieceToAdd);
		paintBoardSquare(pieceToAdd.getName(), pieceToAdd.getPosition());
	}

	public void toggleCheckLabel(boolean flag) {
		checkNotifier.setVisible(flag);
	}

	public List<AbstractChessPiece> getPlayersPieces(Colour currentPlayerToMove) {
        List<AbstractChessPiece> currentPlayersPieces = new ArrayList<AbstractChessPiece>();

		Set<Position> keySet = chessPieces.keySet();
		for (Position position : keySet) {
			assert position != null;
			AbstractChessPiece fetchedPiece = chessPieces.get(position);
			assert fetchedPiece != null;
			if (fetchedPiece.getColour() == currentPlayerToMove)
				currentPlayersPieces.add(fetchedPiece);
		}
		return currentPlayersPieces;
	}

	public void replacePawnWithUserChoice(Pawn pieceCurrentlyHeld,
			Position clickedPosition) {
		PawnReplacementChoice sole = new PawnReplacementChoice(chessBoard, gc, pieceCurrentlyHeld, clickedPosition);
		sole.replace();
	}

	public int getNumberOfChessPieces() {
		return chessPieces.size();
	}

	public Map<Position, AbstractChessPiece> getChessPiecesClone() {
		Map<Position, AbstractChessPiece> chessPiecesClone =  new HashMap<Position, AbstractChessPiece>();
		Set<Position> keySet = chessPieces.keySet();
		for (Position position : keySet) {
            chessPiecesClone.put(position, chessPieces.get(position).clone());
		}

		return chessPiecesClone;
	}

	public Map<Position, AbstractChessPiece> getChessPieces() {
		return chessPieces;
	}

	public void setChessPieces(Map<Position, AbstractChessPiece> chessPieces) {
		this.chessPieces = chessPieces;
	}

	public JCheckBox getAllowUndoCheckBox() {
		return allowUndoCheckBox;
	}

	public void setAllowUndoCheckBox(JCheckBox allowUndoCheckBox) {
		this.allowUndoCheckBox = allowUndoCheckBox;
	}

	public boolean isAllowUndoOverride() {
		return allowUndoOverride;
	}

	public void setAllowUndoOverride(boolean allowUndoOverride) {
		this.allowUndoOverride = allowUndoOverride;
		if (allowUndoOverride)
			undoButton.setEnabled(true);
	}

}
