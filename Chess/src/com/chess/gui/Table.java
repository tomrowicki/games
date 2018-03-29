package com.chess.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.MoveTransition;
import com.chess.engine.player.ai.MiniMax;
import com.chess.engine.player.ai.MoveStrategy;
import com.google.common.collect.Lists;

public class Table extends Observable {

	private final JFrame gameFrame;

	private final GameHistoryPanel gameHistoryPanel;

	private final TakenPiecesPanel takenPiecesPanel;

	private final BoardPanel boardPanel;

	private final MoveLog moveLog;

	private final GameSetup gameSetup;

	private Board chessBoard;

	private Tile sourceTile;

	private Tile destinationTile;

	private Piece humanMovedPiece;

	private BoardDirection boardDirection;

	private Move computerMove;

	private boolean highlightLegalMoves;

	private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);

	private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);

	private final static Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);

	private static String defaultPieceImagePath = "art/fancy/";

	private final Color lightTileColor = Color.decode("#FFFACD");

	private final Color darkTileColor = Color.decode("#593E1A");

	private static final Table INSTANCE = new Table();

	private Table() {
		this.gameFrame = new JFrame("JChess");
		this.gameFrame.setLayout(new BorderLayout());
		final JMenuBar tableMenuBar = createTableMenuBar();
		this.gameFrame.setJMenuBar(tableMenuBar);
		this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
		this.chessBoard = Board.createStandardBoard();
		this.gameHistoryPanel = new GameHistoryPanel();
		this.takenPiecesPanel = new TakenPiecesPanel();
		this.moveLog = new MoveLog();
		this.addObserver(new TableGameAIWatcher());
		this.gameSetup = new GameSetup(this.gameFrame, true);
		this.boardPanel = new BoardPanel();
		this.boardDirection = BoardDirection.NORMAL;
		this.highlightLegalMoves = false;
		this.gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
		this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
		this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);
		this.gameFrame.setVisible(true);
		this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static Table get() {
		return INSTANCE;
	}

	public void show() {
		Table.get().getMoveLog().clear();
		Table.get().getGameHistoryPanel().redo(chessBoard, Table.get().getMoveLog());
		Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
		Table.get().getBoardPanel().drawBoard(Table.get().getGameBoard());
	}

	private JMenuBar createTableMenuBar() {
		final JMenuBar tableMenuBar = new JMenuBar();
		tableMenuBar.add(createFileMenu());
		tableMenuBar.add(createPreferencesMenu());
		tableMenuBar.add(createOptionsMenu());
		return tableMenuBar;

	}

	private JMenu createFileMenu() {
		final JMenu fileMenu = new JMenu("File");
		// PGN files are replay files for games of chess available online
		final JMenuItem openPGN = new JMenuItem("Load PGN File");
		openPGN.addActionListener((ActionEvent e) -> {

			// TODO Auto-generated method stub
			System.out.println("opening up that pgn file!");
		});
		fileMenu.add(openPGN);

		final JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener((ActionEvent e) -> {
			System.exit(0);
		});
		fileMenu.add(exitMenuItem);
		return fileMenu;
	}

	private JMenu createPreferencesMenu() {
		final JMenu preferencesMenu = new JMenu("Preferences");
		final JMenuItem flipBoardMenuItem = new JMenuItem("Flip Board");
		flipBoardMenuItem.addActionListener((e) -> {
			boardDirection = boardDirection.opposite();
			boardPanel.drawBoard(chessBoard);
		});

		preferencesMenu.add(flipBoardMenuItem);
		preferencesMenu.addSeparator();
		final JCheckBoxMenuItem legalMoveHighlighterCheckBox = new JCheckBoxMenuItem("Highlight Legal Moves", false);
		legalMoveHighlighterCheckBox.addActionListener((l) -> {
			highlightLegalMoves = legalMoveHighlighterCheckBox.isSelected();
		});

		preferencesMenu.add(legalMoveHighlighterCheckBox);

		return preferencesMenu;
	}

	private JMenu createOptionsMenu() {
		final JMenu optionsMenu = new JMenu("Options");

		final JMenuItem setupGameMenuItem = new JMenuItem("Setup Game");
		setupGameMenuItem.addActionListener((e) -> {
			Table.get().getGameSetup().promptUser();
			Table.get().setupUpdate(Table.get().getGameSetup());
		});
		optionsMenu.add(setupGameMenuItem);

		return optionsMenu;
	}

	private void setupUpdate(final GameSetup gameSetup) {
		setChanged();
		notifyObservers(gameSetup);
	}

	private Board getGameBoard() {
		return this.chessBoard;
	}

	private static class TableGameAIWatcher implements Observer {

		@Override
		public void update(final Observable o, final Object arg) {

			if (Table.get().getGameSetup().isAIPlayer(Table.get().getGameBoard().currentPlayer())
					&& !Table.get().getGameBoard().currentPlayer().isInCheckMate()
					&& !Table.get().getGameBoard().currentPlayer().isInStaleMate()) {
				// create an AI thread
				// execute AI work
				final AIThinkTank thinkTank = new AIThinkTank();
				thinkTank.execute();
			}

			if (Table.get().getGameBoard().currentPlayer().isInCheckMate()) {
				// TODO look up using JOption instead of sysout from repo
				System.out.println("game over, " + Table.get().getGameBoard().currentPlayer() + " is in checkmate!");
			}

			if (Table.get().getGameBoard().currentPlayer().isInStaleMate()) {
				System.out.println("game over, " + Table.get().getGameBoard().currentPlayer() + " is in stalemate!");
			}
		}
	}

	public void updateGameBoard(final Board board) {
		this.chessBoard = board;
	}

	public void updateComputerMove(final Move move) {
		this.computerMove = move;
	}

	private MoveLog getMoveLog() {
		return this.moveLog;
	}

	private GameHistoryPanel getGameHistoryPanel() {
		return this.gameHistoryPanel;
	}

	private TakenPiecesPanel getTakenPiecesPanel() {
		return this.takenPiecesPanel;
	}

	private BoardPanel getBoardPanel() {
		return this.boardPanel;
	}

	private void moveMadeUpdate(final PlayerType playerType) {
		setChanged();
		notifyObservers(playerType);
	}

	private static class AIThinkTank extends SwingWorker<Move, String> {

		private AIThinkTank() {

		}

		@Override
		protected Move doInBackground() throws Exception {
			final MoveStrategy miniMax = new MiniMax(4);
			final Move bestMove = miniMax.execute(Table.get().getGameBoard());
			return bestMove;
		}

		@Override
		public void done() {
			try {
				final Move bestMove = get();
				Table.get().updateComputerMove(bestMove);
				Table.get().updateGameBoard(
						Table.get().getGameBoard().currentPlayer().makeMove(bestMove).getTransitionBoard());
				Table.get().getMoveLog().addMove(bestMove);
				Table.get().getGameHistoryPanel().redo(Table.get().getGameBoard(), Table.get().getMoveLog());
				Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
				Table.get().getBoardPanel().drawBoard(Table.get().getGameBoard());
				Table.get().moveMadeUpdate(PlayerType.COMPUTER);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}

	}

	private GameSetup getGameSetup() {
		return this.gameSetup;
	}

	@SuppressWarnings("serial")
	private class BoardPanel extends JPanel {

		final List<TilePanel> boardTiles;

		BoardPanel() {
			super(new GridLayout(8, 8));
			this.boardTiles = new ArrayList<>();
			for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
				final TilePanel tilePanel = new TilePanel(this, i);
				this.boardTiles.add(tilePanel);
				add(tilePanel);
			}
			setPreferredSize(BOARD_PANEL_DIMENSION);
			validate();
		}

		public void drawBoard(final Board board) {
			removeAll();
			for (final TilePanel tilePanel : boardDirection.traverse(boardTiles)) {
				tilePanel.drawTile(board);
				add(tilePanel);
			}
			validate();
			repaint();
		}
	}

	public static class MoveLog {

		private final List<Move> moves;

		MoveLog() {
			this.moves = new ArrayList<>();
		}

		public List<Move> getMoves() {
			return this.moves;
		}

		public void addMove(final Move move) {
			this.moves.add(move);
		}

		public int size() {
			return this.moves.size();
		}

		public void clear() {
			this.moves.clear();
		}

		public boolean remove(final Move move) {
			return this.moves.remove(move);
		}

		public Move removeMove(final int index) {
			return this.moves.remove(index);
		}
	}

	enum PlayerType {
		HUMAN, COMPUTER
	}

	@SuppressWarnings("serial")
	private class TilePanel extends JPanel {

		private final int tileId;

		TilePanel(final BoardPanel boardPanel, final int tileId) {
			super(new GridBagLayout());
			this.tileId = tileId;
			setPreferredSize(TILE_PANEL_DIMENSION);
			assignTileColor();
			assignTilePieceIcon(chessBoard);

			addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(final MouseEvent e) {
					if (SwingUtilities.isRightMouseButton(e)) {
						sourceTile = null;
						destinationTile = null;
						humanMovedPiece = null;
					} else if (SwingUtilities.isLeftMouseButton(e)) {
						if (sourceTile == null) {
							sourceTile = chessBoard.getTile(tileId);
							humanMovedPiece = sourceTile.getPiece();
							if (humanMovedPiece == null) {
								sourceTile = null;
							}
						} else {
							destinationTile = chessBoard.getTile(tileId);
							final Move move = Move.MoveFactory.createMove(chessBoard, sourceTile.getTileCoordinate(),
									destinationTile.getTileCoordinate());
							final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
							if (transition.getMoveStatus().isDone()) {
								chessBoard = transition.getTransitionBoard();
								moveLog.addMove(move);
							}
							sourceTile = null;
							destinationTile = null;
							humanMovedPiece = null;
						}
						SwingUtilities.invokeLater(() -> {
							gameHistoryPanel.redo(chessBoard, moveLog);
							takenPiecesPanel.redo(moveLog);
							if (gameSetup.isAIPlayer(chessBoard.currentPlayer())) {
								Table.get().moveMadeUpdate(PlayerType.HUMAN);
							}
							boardPanel.drawBoard(chessBoard);
						});
					}
				}

				@Override
				public void mousePressed(final MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseReleased(final MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseEntered(final MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseExited(final MouseEvent e) {
					// TODO Auto-generated method stub

				}
			});

			validate();
		}

		public void drawTile(final Board board) {
			assignTileColor();
			assignTilePieceIcon(board);
			highlightLegals(board);
			validate();
		}

		private void assignTilePieceIcon(final Board board) {
			this.removeAll();
			if (board.getTile(this.tileId).isTileOccupied()) {
				try {
					final BufferedImage image = ImageIO.read(new File(defaultPieceImagePath
							+ board.getTile(this.tileId).getPiece().getPieceAlliance().toString().substring(0, 1)
							+ board.getTile(this.tileId).getPiece().toString() + ".gif"));
					add(new JLabel(new ImageIcon(image)));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		private void assignTileColor() {
			boolean isLight = ((tileId + tileId / 8) % 2 == 0);
			setBackground(isLight ? lightTileColor : darkTileColor);
		}

		private void highlightLegals(final Board board) {
			if (highlightLegalMoves) {
				for (final Move move : pieceLegalMoves(board)) {
					if (move.getDesinationCoordinate() == this.tileId) {
						try {
							add(new JLabel(new ImageIcon(ImageIO.read(new File("art/misc/green_dot.png")))));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

		private Collection<Move> pieceLegalMoves(final Board board) {
			if (humanMovedPiece != null && humanMovedPiece.getPieceAlliance() == board.currentPlayer().getAlliance()) {
				return humanMovedPiece.calculateLegalMoves(board);
			}
			return Collections.emptyList();
		}
	}

	public enum BoardDirection {
		NORMAL {

			@Override
			List<TilePanel> traverse(List<TilePanel> boardTiles) {
				return boardTiles;
			}

			@Override
			BoardDirection opposite() {
				return FLIPPED;
			}
		},
		FLIPPED {

			@Override
			List<TilePanel> traverse(List<TilePanel> boardTiles) {
				return Lists.reverse(boardTiles);
			}

			@Override
			BoardDirection opposite() {
				return NORMAL;
			}

		};

		abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);

		abstract BoardDirection opposite();
	}
}
