package ui;

import domain.Location;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class BoardView extends JPanel {
    private static final int BOARD_SIZE = 8;
    private static final int PREFERRED_TILE_SIZE = 50; // initial window size
    private static final Color LIGHT_SQUARE_COLOR = new Color(240, 217, 181);
    private static final Color DARK_SQUARE_COLOR = new Color(181, 136, 99);
    private static final Color SELECTED_SQUARE_COLOR = new Color(164, 149, 195); // NU Purple 40

    private int selectedRow = -1;
    private int selectedCol = -1;

    private Map<PieceType, Image> whitePieceSprites;
    private Map<PieceType, Image> blackPieceSprites;

    private BoardController boardController;

    public BoardView(BoardController boardController) {
        setPreferredSize(new Dimension(BOARD_SIZE * PREFERRED_TILE_SIZE, BOARD_SIZE * PREFERRED_TILE_SIZE));
        loadPieceSprites();
        this.boardController = boardController;
        boardController.setBoardView(this);
        addMouseListener(new BoardMouseListener());
    }

    private int tileSize() {
        return Math.min(getWidth(), getHeight()) / BOARD_SIZE;
    }

    // Left edge of the board
    private int originX(int tile) {
        return 0;
    }

    // Top edge of the board.
    private int originY(int tile) {
        return (getHeight() - tile * BOARD_SIZE) / 2;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int tile = tileSize();
        if (tile <= 0) return; // panel not laid out yet

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int ox = originX(tile);
        int oy = originY(tile);

        drawBoard(g2, tile, ox, oy);
        drawSelectedSquare(g2, tile, ox, oy);
        drawPieces(g2, tile, ox, oy); // after selected square so piece image is on top

        g2.dispose();

    }

    private void drawBoard(Graphics2D g, int tile, int ox, int oy) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                g.setColor((row + col) % 2 == 0 ? LIGHT_SQUARE_COLOR : DARK_SQUARE_COLOR);
                g.fillRect(ox + col * tile, oy + row * tile, tile, tile);
            }
        }
    }

    private void loadPieceSprites(){

        whitePieceSprites = new HashMap<>();
        blackPieceSprites = new HashMap<>();

        loadOnePieceImage(PieceType.PAWN, PieceColor.WHITE, "sprites/white_pawn.png");
        loadOnePieceImage(PieceType.ROOK, PieceColor.WHITE, "sprites/white_rook.png");
        loadOnePieceImage(PieceType.KNIGHT, PieceColor.WHITE, "sprites/white_knight.png");
        loadOnePieceImage(PieceType.BISHOP, PieceColor.WHITE, "sprites/white_bishop.png");
        loadOnePieceImage(PieceType.QUEEN, PieceColor.WHITE, "sprites/white_queen.png");
        loadOnePieceImage(PieceType.KING, PieceColor.WHITE, "sprites/white_king.png");

        loadOnePieceImage(PieceType.PAWN, PieceColor.BLACK, "sprites/black_pawn.png");
        loadOnePieceImage(PieceType.ROOK, PieceColor.BLACK, "sprites/black_rook.png");
        loadOnePieceImage(PieceType.KNIGHT, PieceColor.BLACK, "sprites/black_knight.png");
        loadOnePieceImage(PieceType.BISHOP, PieceColor.BLACK, "sprites/black_bishop.png");
        loadOnePieceImage(PieceType.QUEEN, PieceColor.BLACK, "sprites/black_queen.png");
        loadOnePieceImage(PieceType.KING, PieceColor.BLACK, "sprites/black_king.png");
    }

    private void loadOnePieceImage(PieceType type, PieceColor color, String imagePath){
        InputStream imageInputStream = getClass().getClassLoader().getResourceAsStream(imagePath);
        BufferedImage image = null;

        try {
            image = ImageIO.read(imageInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (color.equals(PieceColor.BLACK)) {
            blackPieceSprites.put(type, image);
        } else {
            this.whitePieceSprites.put(type, image);
        }
    }

    private void drawSelectedSquare(Graphics2D g, int tile, int ox, int oy) {
        if (selectedRow < 0 || selectedCol < 0) return;
        g.setColor(SELECTED_SQUARE_COLOR);
        g.fillRect(ox + selectedCol * tile, oy + selectedRow * tile, tile, tile);
    }

    private void drawPieces(Graphics2D g, int tile, int ox, int oy){

        Piece[][] boardSnapshot = boardController.getBoardSnapshot();

        for(int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                Piece piece = boardSnapshot[r][c];
                if (piece != null) {
                    Image pieceImage = piece.getColor() == PieceColor.WHITE ?
                        whitePieceSprites.get(piece.getType()) :
                        blackPieceSprites.get(piece.getType());
                    g.drawImage(pieceImage, ox + c * tile, oy + r * tile, tile, tile, this);
                }
            }
        }
    }

    private class BoardMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {

            int tile = tileSize();
            if (tile <= 0) return;

            int x = e.getX() - originX(tile);
            int y = e.getY() - originY(tile);

            // ignore clicks in the margin outside the board
            if (x < 0 || y < 0 || x >= tile * BOARD_SIZE || y >= tile * BOARD_SIZE) {
                return;
            }

            selectedCol = x / tile;
            selectedRow = y / tile;

            // FIXME: perform input validation

            repaint();

            boardController.handleSquareClick(new Location(selectedCol, selectedRow));
        }
    }
}
