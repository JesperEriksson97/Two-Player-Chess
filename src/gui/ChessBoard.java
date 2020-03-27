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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import util.Position;
import chess_pieces.Bishop;
import chess_pieces.King;
import chess_pieces.Knight;
import chess_pieces.Pawn;
import chess_pieces.Queen;
import chess_pieces.Rook;
import chess_pieces.IChessPiece.Colour;
import chess_pieces.IChessPiece;
import control.IGameController;


@SuppressWarnings("serial")
public class ChessBoard extends AbstractChessBoard {
	
	private static final int CHESSBOARD_WIDTH = 8;
	private static final int CHESSBOARD_LENGTH = 8;

	private IGameController gc;
	private AbstractChessBoard chessBoard;
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

	private Map<Position, IChessPiece> chessPieces;

	public ChessBoard(final IGameController gc) {
		super(gc);
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
				/*NewGameChoice sole = new NewGameChoice(chessBoard);
				sole.show(); */
				
				int n = JOptionPane.showConfirmDialog(
		        		chessBoard, "Really start a new game?",
		                "New game",
		                JOptionPane.YES_NO_OPTION);
		        if (n == JOptionPane.YES_OPTION) {
		        	reset();
		        }
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
		chessPieces = new HashMap<Position, IChessPiece>();
		addInitialSixteenPieces();
		Set<Position> positionSet = chessPieces.keySet();
		for (Position position : positionSet) {
			IChessPiece cp = chessPieces.get(position);
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
				
				IChessPiece pawn = new Pawn(colour, position);
				this.setPieceAtPosition(position, pawn);
			}
		}

		for (int i = 1; i <= 2; i++) {
			colour = (i == 1) ? Colour.WHITE : Colour.BLACK;
			yCoord = (i == 1) ? 1 : 8;
			for (xCoord = 1; xCoord <= CHESSBOARD_WIDTH; xCoord++) {
				Position position = Position.createPosition(xCoord, yCoord);
				switch (xCoord) {
				    case 1: case 8:
				    	IChessPiece rook = new Rook(colour, position);
				    	System.out.println("This is the position after the rook has been created: " + rook.getPosition());
				    	setPieceAtPosition(position, rook.createChessPiece(colour, position));
				    	break;
				    case 2: case 7:
				    	IChessPiece knight = new Knight(colour, position);
				    	setPieceAtPosition(position, knight);
				    	break;
				    case 3: case 6:
				    	IChessPiece bishop = new Bishop(colour, position);
				    	setPieceAtPosition(position, bishop);
				    	break;
				    case 4:
				    	IChessPiece queen = new Queen(colour, position);
				    	setPieceAtPosition(position, queen);
				    	break;
				    case 5:
				    	IChessPiece king = new King(colour, position);
				    	setPieceAtPosition(position, king);
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

	public IChessPiece getPieceAtPosition(Position position) {
		return chessPieces.get(position);
	}

	public void setPieceAtPosition(Position position, IChessPiece iChessPiece) {
		assert position != null;
		assert iChessPiece != null;
		assert position.equals(iChessPiece.getPosition()): "position = " + position
		+ ", and newPiece.getPosition() = " + iChessPiece.getPosition();
		chessPieces.put(position, iChessPiece);
	}

	/*public static Colour getColourOfSquareAtPosition(Position position) {
		int xCoord = position.getXCoord();
		int yCoord = position.getYCoord();
		if (Math.abs(xCoord - yCoord) % 2 == 0)
			return Colour.BLACK;
		else
			return Colour.WHITE;
	}*/

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

	public King getKing(chess_pieces.IChessPiece.Colour currentPlayerToMove) {
		Set<Position> chessPieceSet = chessPieces.keySet();
		for (Position position : chessPieceSet) {
			IChessPiece chessPiece = chessPieces.get(position);
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

	public void movePiece(IChessPiece pieceCurrentlyHeld,
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

	public void addPiece(IChessPiece pieceCurrentlyHeld) {
		setPieceAtPosition(pieceCurrentlyHeld.getPosition(), pieceCurrentlyHeld);
		paintBoardSquare(pieceCurrentlyHeld.getName(), pieceCurrentlyHeld.getPosition());
	}

	public void toggleCheckLabel(boolean flag) {
		checkNotifier.setVisible(flag);
	}

	public List<IChessPiece> getPlayersPieces(Colour currentPlayerToMove) {
        List<IChessPiece> currentPlayersPieces = new ArrayList<IChessPiece>();

		Set<Position> keySet = chessPieces.keySet();
		for (Position position : keySet) {
			assert position != null;
			IChessPiece fetchedPiece = chessPieces.get(position);
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

	public Map<Position, IChessPiece> getChessPiecesClone() {
		Map<Position, IChessPiece> chessPiecesClone =  new HashMap<Position, IChessPiece>();
		Set<Position> keySet = chessPieces.keySet();
		for (Position position : keySet) {
            chessPiecesClone.put(position, chessPieces.get(position).clone());
		}

		return chessPiecesClone;
	}

	public Map<Position, IChessPiece> getChessPieces() {
		return chessPieces;
	}

	public void setChessPieces(Map<Position, IChessPiece> chessPieces) {
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
