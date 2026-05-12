package UI;

import javax.swing.*;
import java.awt.*;
import Logic.GameManager;
import Logic.GameObserver;
import Logic.GameStatus;

public class MainFrame extends JFrame implements GameObserver{
    private static final int FRAME_WIDTH  = 900;
    private static final int FRAME_HEIGHT = 750;

    private Panel gamePanel;
    private InfoPanel infoPanel;
    private GameManager gameManager;
    public MainFrame(GameManager gameManager) {
        this.gamePanel = new Panel();
        this.infoPanel = new InfoPanel();
        this.gameManager = gameManager;
        GameController controller = new GameController(this.gameManager, this.gamePanel, this.infoPanel);

        getContentPane().setBackground(new Color(250, 202, 222));
        setLayout(new BorderLayout(5, 5));  // gap giữa các panel

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

    @Override
    public void onGameUpdate() {
        System.out.println("MainFrame nhận được lệnh cập nhật từ GameManager!"); // Thêm dòng này để test
        
        // BẮT BUỘC PHẢI CÓ DÒNG NÀY ĐỂ BẢNG GAME VẼ LẠI:
        if (gamePanel != null && gameManager != null) {
            gamePanel.updateBoard(gameManager.getBoard());
        }

        if (gameManager.gameStatus() == GameStatus.PLAYING && !infoPanel.isTimerRunning()){
            infoPanel.startTimer();
        }
        throw new UnsupportedOperationException("Unimplemented method 'onGameUpdate'");
    }
}
