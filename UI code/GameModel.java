import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.awt.Image;
import javax.swing.ImageIcon;

public class GameModel {
    private static final int ROWS = 4;
    private static final int COLS = 4;
    //private CardAnimation[][] gameBoard = new CardAnimation[ROWS][COLS];
    private List<int[]> flipped = new ArrayList<>();
    private int[][] lastMatch;
    private Board board;
    public GameModel() {
        board = new Board(new LocalImageProvider(), new RandomShuffle());
        //initBoard();
    }

    // private void initBoard() {
    //     String[][] cardData = {
    //         {"dragonfruit",  "/images/dragonfruit.png"},
    //         {"lemon",  "/images/lemon.png"},
    //         {"cherry",  "/images/cherry.png"},
    //         {"melon",  "/images/melon.png"},
    //         {"coconut",  "/images/coconut.png"},
    //         {"watermelon",  "/images/watermelon.png"},
    //         {"starfruit",  "/images/starfruit.png"},
    //         {"mangosteen",  "/images/mangosteen.png"},
    //         {"grapes",  "/images/grapes.png"},
    //     };

    //      List<CardAnimation> list = new ArrayList<>();
    //     for (String[] data : cardData) {
    //     list.add(new CardAnimation(data[0], data[1]));
    //     list.add(new CardAnimation(data[0], data[1]));
    //     }
    //     Collections.shuffle(list);

    //     int idx = 0;
    //     for (int r = 0; r < ROWS; r++)
    //         for (int c = 0; c < COLS; c++)
    //             gameBoard[r][c] = list.get(idx++);
    // }

    public boolean canFlip(int r, int c) {
        return !board.getCard(r, c).isMatch()
            && !board.getCard(r, c).isFlipped()
            && flipped.size() < 2;
    }

    public void flipCard(int r, int c) {
        board.getCard(r, c).flip();
        flipped.add(new int[]{r, c});
    }

    public boolean hasPendingPair() { return flipped.size() == 2; }

    public boolean isMatch() {
        int[] a = flipped.get(0), b = flipped.get(1);
        return board.getCard(a[0],a[1]).getPairID()
             == (board.getCard(b[0], b[1]).getPairID());
    }

    public void confirmMatch() {
        lastMatch = new int[][] {
            flipped.get(0),
            flipped.get(1)
        };

        for (int[] pos : flipped) {
            board.getCard(pos[0],pos[1]).setMatch(true);
        }
        flipped.clear();
    }

    public boolean isWin() {
    for (int r = 0; r < ROWS; r++)
        for (int c = 0; c < COLS; c++)
            if (!board.getCard(r, c).isMatch()) return false;
    return true;
    }

    public void reset() {
    flipped.clear();
    lastMatch = null;
    board.resetBoard();
    //initBoard();  
}
    public Card getCard(int r, int c){
        return board.getCard(r,c);
    }
    
    public int[][] getLastMatch() { return lastMatch; }
    
    public List<int[]> getPendingPair() { return flipped; }
    
    public void clearPending() { flipped.clear(); }
}

