package UI;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    private static final int FRAME_WIDTH  = 900;
    private static final int FRAME_HEIGHT = 750;

    public MainFrame(GameModel model) {
        Panel gamePanel = new Panel();
        InfoPanel infoPanel = new InfoPanel();
        GameController controller = new GameController(model, gamePanel, infoPanel);

        setLayout(new BorderLayout(5, 5));  // gap giữa các panel
        getContentPane().setBackground(new Color(250, 202, 222));

        setLayout(new BorderLayout());
        add(infoPanel,  BorderLayout.NORTH);
        add(gamePanel,  BorderLayout.CENTER);

        setSize(FRAME_WIDTH, FRAME_HEIGHT);

        setTitle("Memory Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);

        infoPanel.startTimer();
    }
}
