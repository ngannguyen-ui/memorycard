package Main;
import Logic.GameManager;
import UI.MainFrame;
import javax.swing.SwingUtilities;
import Logic.Board;
import Logic.Player;
import Logic.RandomShuffle;
import Logic.LocalImageProvider;

public class Khanhdepgai {
        public static void main(String[]args){
        SwingUtilities.invokeLater(() -> {
            Board board = new Board(new LocalImageProvider(), new RandomShuffle());
            Player player = new Player(" Khanh' ");
            GameManager gameManager   = new GameManager(board, player);
            MainFrame frame   = new MainFrame(gameManager);
            frame.setVisible(true);
            gameManager.startPreview(2000);
        });
    } 
}
