package com.chess.engine.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chess.engine.Alliance;
import com.chess.engine.board.Move.MoveFactory;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Knight;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Queen;
import com.chess.engine.pieces.Rook;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

public final class Board {

	private final List<Tile> gameBoard;

	private final Collection<Piece> whitePieces;

	private final Collection<Piece> blackPieces;

	private final WhitePlayer whitePlayer;

	private final BlackPlayer blackPlayer;

	private final Player currentPlayer;

	private final Pawn enPassantPawn;

	private final Move transitionMove;

	private static final Board STANDARD_BOARD = createStandardBoardImpl();

	private Board(final Builder builder) {
		this.gameBoard = createGameBoard(builder);
		this.whitePieces = calculateActivePieces(builder, Alliance.WHITE);
		this.blackPieces = calculateActivePieces(builder, Alliance.BLACK);
		this.enPassantPawn = builder.enPassantPawn;
		final Collection<Move> whiteStandardMoves = calculateLegalMoves(this.whitePieces);
		final Collection<Move> blackStandardMoves = calculateLegalMoves(this.blackPieces);
		this.whitePlayer = new WhitePlayer(this, whiteStandardMoves, blackStandardMoves);
		this.blackPlayer = new BlackPlayer(this, whiteStandardMoves, blackStandardMoves);
		this.currentPlayer = builder.nextMoveMaker.choosePlayerByAlliance(this.whitePlayer, this.blackPlayer);
		this.transitionMove = builder.transitionMove != null ? builder.transitionMove : MoveFactory.getNullMove();
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
			final String tileText = prettyPrint(this.gameBoard.get(i));
			builder.append(String.format("%3s", tileText));
			if ((i + 1) % 8 == 0) {
				builder.append("\n");
			}
		}
		return builder.toString();
	}

	private static String prettyPrint(final Tile tile) {
		if (tile.isTileOccupied()) {
			return tile.getPiece().getPieceAlliance().isBlack() ? tile.toString().toLowerCase() : tile.toString();
		}
		return tile.toString();
	}

	public Collection<Piece> getBlackPieces() {
		return this.blackPieces;
	}

	public Collection<Piece> getWhitePieces() {
		return this.whitePieces;
	}

	public Iterable<Piece> getAllPieces() {
		return Iterables.unmodifiableIterable(Iterables.concat(this.whitePieces, this.blackPieces));
	}

	public Iterable<Move> getAllLegalMoves() {
		return Iterables.unmodifiableIterable(
				Iterables.concat(this.whitePlayer.getLegalMoves(), this.blackPlayer.getLegalMoves()));
	}

	public WhitePlayer whitePlayer() {
		return this.whitePlayer;
	}

	public BlackPlayer blackPlayer() {
		return this.blackPlayer;
	}

	public Player currentPlayer() {
		return this.currentPlayer;
	}

	public Tile getTile(final int coordinate) {
		return this.gameBoard.get(coordinate);
	}

	public List<Tile> getGameBoard() {
		return this.gameBoard;
	}

	public Pawn getEnPassantPawn() {
		return this.enPassantPawn;
	}

	public Move getTransitionMove() {
		return this.transitionMove;
	}

	public static Board createStandardBoard() {
		return STANDARD_BOARD;
	}

	private static Board createStandardBoardImpl() {
		final Builder builder = new Builder();
		// Black Layout
		builder.setPiece(new Rook(0, Alliance.BLACK));
		builder.setPiece(new Knight(1, Alliance.BLACK));
		builder.setPiece(new Bishop(2, Alliance.BLACK));
		builder.setPiece(new Queen(3, Alliance.BLACK));
		builder.setPiece(new King(4, Alliance.BLACK, true, true));
		builder.setPiece(new Bishop(5, Alliance.BLACK));
		builder.setPiece(new Knight(6, Alliance.BLACK));
		builder.setPiece(new Rook(7, Alliance.BLACK));
		builder.setPiece(new Pawn(8, Alliance.BLACK));
		builder.setPiece(new Pawn(9, Alliance.BLACK));
		builder.setPiece(new Pawn(10, Alliance.BLACK));
		builder.setPiece(new Pawn(11, Alliance.BLACK));
		builder.setPiece(new Pawn(12, Alliance.BLACK));
		builder.setPiece(new Pawn(13, Alliance.BLACK));
		builder.setPiece(new Pawn(14, Alliance.BLACK));
		builder.setPiece(new Pawn(15, Alliance.BLACK));
		// White Layout
		builder.setPiece(new Pawn(48, Alliance.WHITE));
		builder.setPiece(new Pawn(49, Alliance.WHITE));
		builder.setPiece(new Pawn(50, Alliance.WHITE));
		builder.setPiece(new Pawn(51, Alliance.WHITE));
		builder.setPiece(new Pawn(52, Alliance.WHITE));
		builder.setPiece(new Pawn(53, Alliance.WHITE));
		builder.setPiece(new Pawn(54, Alliance.WHITE));
		builder.setPiece(new Pawn(55, Alliance.WHITE));
		builder.setPiece(new Rook(56, Alliance.WHITE));
		builder.setPiece(new Knight(57, Alliance.WHITE));
		builder.setPiece(new Bishop(58, Alliance.WHITE));
		builder.setPiece(new Queen(59, Alliance.WHITE));
		builder.setPiece(new King(60, Alliance.WHITE, true, true));
		builder.setPiece(new Bishop(61, Alliance.WHITE));
		builder.setPiece(new Knight(62, Alliance.WHITE));
		builder.setPiece(new Rook(63, Alliance.WHITE));
		// white to move
		builder.setMoveMaker(Alliance.WHITE);
		// build the board
		return builder.build();
	}

	private static List<Tile> createGameBoard(final Builder boardBuilder) {
		final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
		for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
			tiles[i] = Tile.createTile(i, boardBuilder.boardConfig.get(i));
		}
		return ImmutableList.copyOf(tiles);
	}

	private Collection<Move> calculateLegalMoves(final Collection<Piece> pieces) {
		final List<Move> legalMoves = new ArrayList<>(35);
		for (final Piece piece : pieces) {
			legalMoves.addAll(piece.calculateLegalMoves(this));
		}
		return legalMoves;
	}

	private static Collection<Piece> calculateActivePieces(final Builder builder, final Alliance alliance) {
		final List<Piece> activePieces = new ArrayList<>(16);
		for (final Piece piece : builder.boardConfig.values()) {
			if (piece.getPieceAlliance() == alliance) {
				activePieces.add(piece);
			}
		}
		return ImmutableList.copyOf(activePieces);
	}

	public static class Builder {

		Map<Integer, Piece> boardConfig;

		Alliance nextMoveMaker;

		Pawn enPassantPawn;

		Move transitionMove;

		public Builder() {
			this.boardConfig = new HashMap<>(33, 1.0f);
		}

		public Builder setPiece(final Piece piece) {
			this.boardConfig.put(piece.getPiecePosition(), piece);
			return this;
		}

		public Builder setMoveMaker(final Alliance nextMoveMaker) {
			this.nextMoveMaker = nextMoveMaker;
			return this;
		}

		public Builder setEnPassantPawn(final Pawn enPassantPawn) {
			this.enPassantPawn = enPassantPawn;
			return this;
		}

		public Builder setMoveTransition(final Move transitionMove) {
			this.transitionMove = transitionMove;
			return this;
		}

		public Board build() {
			return new Board(this);
		}

	}

}