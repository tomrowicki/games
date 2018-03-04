package com.chess.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.MoveTransition;

public class Table {

	private final JFrame gameFrame;

	private final BoardPanel boardPanel;

	private Board chessBoard;

	private Tile sourceTile;

	private Tile destinationTile;

	private Piece humanMovedPiece;

	private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);

	private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);

	private final static Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);

	private static String defaultPieceImagePath = "art/fancy/";

	private final Color lightTileColor = Color.decode("#FFFACD");

	private final Color darkTileColor = Color.decode("#593E1A");

	public Table() {
		this.gameFrame = new JFrame("JChess");
		this.gameFrame.setLayout(new BorderLayout());
		final JMenuBar tableMenuBar = createTableMenuBar();
		this.gameFrame.setJMenuBar(tableMenuBar);
		this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
		this.chessBoard = Board.createStandardBoard();
		this.boardPanel = new BoardPanel();
		this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
		this.gameFrame.setVisible(true);
		this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private JMenuBar createTableMenuBar() {
		final JMenuBar tableMenuBar = new JMenuBar();
		tableMenuBar.add(createFileMenu());
		return tableMenuBar;

	}

	private JMenu createFileMenu() {
		final JMenu fileMenu = new JMenu("File");
		// PGN files are replay files for games of chess available online
		final JMenuItem openPGN = new JMenuItem("Load PGN File");
		openPGN.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("opening up that pgn file!");
			}
		});
		fileMenu.add(openPGN);

		final JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(exitMenuItem);
		return fileMenu;
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
			for (TilePanel tilePanel : boardTiles) {
				tilePanel.drawTile(board);
				add(tilePanel);
			}
			validate();
			repaint();
		}
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
								// TODO add the move that was made to the move
								// log
							}
							sourceTile = null;
							destinationTile = null;
							humanMovedPiece = null;
						}
						SwingUtilities.invokeLater(() -> {
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
	}
}
