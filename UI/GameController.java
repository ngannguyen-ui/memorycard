package UI;
import Logic.Card;
import Logic.GameManager;
import Logic.GameStatus;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class GameController {
    private GameManager gameManager;
    private Panel view;
    private GameController controller;
    private InfoPanel infoPanel;

    public GameController(GameManager gameManager, Panel view, InfoPanel infoPanel) {
        this.gameManager = gameManager;
        this.view = view;
        this.infoPanel = infoPanel;
        view.setController(this);  

        gameManager.setObserver(() -> view.updateBoard(gameManager.getBoard()));

        gameManager.setStateObserver(result -> {
        infoPanel.stopTimer();
        if ("WINNING".equals(result)) {
            showWinDialog(); 
        } else if ("GAME OVER".equals(result)) {
            showLoseDialog();
            resetGame();
        } 
    });
    }

    public GameController getController(){
        return controller;
    }

    private ImageIcon loadAndScaleByWidth(String path, int targetWidth) {
        try {
            java.net.URL imgURL = getClass().getResource(path);
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                int originalWidth = icon.getIconWidth();
                int originalHeight = icon.getIconHeight();

                // Tính toán chiều cao để giữ nguyên tỷ lệ
                double ratio = (double)originalHeight / originalWidth;
                int targetHeight = (int)(targetWidth * ratio);

                Image scaledImage = icon.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            } else {
                System.err.println("❌ Không tìm thấy icon: " + path);
                return null;
            }
        } catch (Exception e) {
            System.err.println("❌ Lỗi load icon: " + e.getMessage());
            return null;
        }
    }
    // Khi người chơi bấm vào 1 lá bài
    public void onCellClicked(int row, int col) {
        //  GameManager sẽ tự lật, tự so sánh, tự úp bài nếu sai (bằng Timer của nó)
        int moveBefore = gameManager.getMoveCount();
        gameManager.SelectedCard(row, col);

        // 2. Cập nhật điểm và số lượt đi lên InfoPanel
       // Chỉ cộng move khi đã lật hai thẻ 
       if (gameManager.getMoveCount() > moveBefore){
        infoPanel.addMove();
       }

       // Lấy điểm thực tế từ Player
       infoPanel.setScore(gameManager.getPlayer().getScore());
    }

    // Bỏ 2 method checkMatch & flipBackWithDelay vì trong GameManager đã là full HD rồi
    // private void checkMatch() {
    //     if (model.isMatch()) {
    //         model.confirmMatch();
    //         flashMatch();
    //         infoPanel.addScore(10);
    //         checkWin(); 
    //     } else {
    //         flipBackWithDelay();
    //     }
    // }

    // private void flipBackWithDelay() {
    //     view.setEnabled(false);

    //     Timer timer = new Timer(800, e -> {
    //         // Lật ngược lại
    //         for (int[] pos : model.getPendingPair()) {
    //             model.getCard(pos[0], pos[1]).hide();
    //             view.renderCell(pos[0], pos[1], model.getCard(pos[0], pos[1]));
    //         }
    //         model.clearPending();
    //         view.setEnabled(true);
    // });

//     timer.setRepeats(false);
//     timer.start();
// }

// Nhấp nháy khi match
// tạm thời bỏ vì Manager cũng làm rồi
// public void flashMatch() {
//     int[] a = model.getLastMatch()[0];
//     int[] b = model.getLastMatch()[1];

//     Timer flash = new Timer(150, null);
//     int[] count = {0};

//     flash.addActionListener(e -> {
//         Color color = (count[0] % 2 == 0) ? Color.YELLOW : new Color(76, 175, 80);
//         view.setCellColor(a[0], a[1], color);
//         view.setCellColor(b[0], b[1], color);
//         if (++count[0] >= 6) flash.stop();
//     });
//     flash.start();
// }

// private void checkWin() {
//     if (gameManager.isWin()) {
//         infoPanel.stopTimer();
//         showWinDialog();
//     }
// }

private void showWinDialog() {
    
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
    panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    panel.setBackground(Color.WHITE);

    JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER,8,0));   
    titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT); 
    JLabel lblTitle = new JLabel("You Win!", SwingConstants.CENTER);
    lblTitle.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
    lblTitle.setForeground(new Color(251, 84, 152));

    JLabel fireworkImage = new JLabel(loadDialogIcon("UI/images/firework-icon.png", 50, 50));

    titlePanel.add(fireworkImage);
    titlePanel.add(lblTitle);

    JPanel scoreBox = makeStatBox(
        loadDialogIcon("UI/images/star-icon.png", 32, 32),
        "Score: " + infoPanel.getScore(),
        new Color(244,159,70),    
        Color.WHITE      
    );

    JPanel timeBox = makeStatBox(
        loadDialogIcon("UI/images/clock-icon.png", 32, 32),
        "Time: " + infoPanel.getSeconds(),
        new Color(244,159,70),  
        Color.WHITE     
    );


    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
    //btnPanel.setBackground(Color.WHITE);

    JButton btnReplay = new JButton();
    JButton btnExit = new JButton();

    ImageIcon replayImage = loadAndScaleByWidth("UI/images/replay_button.png", 110);
    ImageIcon exitImage = loadAndScaleByWidth("UI/images/quit_button.png", 110);

    if (exitImage != null || replayImage != null) {
        btnReplay.setIcon(replayImage);
        btnExit.setIcon(exitImage);
    } else {
    btnReplay.setText("New Game");
    btnExit.setText("Quit");
    }

    btnReplay.setBorderPainted(false);      
    btnReplay.setContentAreaFilled(false);   
    btnReplay.setFocusPainted(false);        
    btnReplay.setMargin(new Insets(0, 0, 0, 0)); 
    btnReplay.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    btnExit.setBorderPainted(false);      // Xóa viền mặc định
    btnExit.setContentAreaFilled(false);   // Làm trong suốt vùng nền
    btnExit.setFocusPainted(false);        // Xóa viền khi được focus
    btnExit.setMargin(new Insets(0, 0, 0, 0)); // Xóa khoảng lề thừa
    btnExit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Hiện bàn tay khi di chuột vào
    styleButton(btnReplay, new Color(222, 250, 202));
    styleButton(btnExit,   new Color(222, 250, 202));

    btnPanel.add(btnReplay);
    btnPanel.add(btnExit);

    panel.add(lblTitle);
    panel.add(Box.createVerticalStrut(15));
    panel.add(scoreBox);
    //panel.add(Box.createVerticalStrut(10));
    panel.add(timeBox);
    panel.add(Box.createVerticalStrut(20));
    panel.add(btnPanel);

    
    JDialog dialog = new JDialog();
    dialog.setTitle("Kết quả");
    dialog.setModal(true);   
    dialog.setContentPane(panel);
    dialog.pack();
    dialog.setResizable(false);
    dialog.setLocationRelativeTo(view);  

    
    btnReplay.addActionListener(e -> {
        dialog.dispose();
        resetGame();
    });

    btnExit.addActionListener(e -> {
        int confirm = JOptionPane.showConfirmDialog(
            dialog,
            "Bạn có chắc muốn thoát không?",
            "Xác nhận",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) System.exit(0);
    });

    dialog.setVisible(true);
}

private void showLoseDialog() {
    
    
    
}

private void styleButton(JButton btn, Color color) {
    btn.setBackground(color);
    btn.setForeground(Color.DARK_GRAY);
    btn.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
    btn.setFocusPainted(false);
    btn.setContentAreaFilled(true);
    //btn.setBackground(new Color(222, 250, 202));
    btn.setContentAreaFilled(false);
    btn.setOpaque(false);
    btn.setFocusPainted(false);
    btn.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(200, 200, 200), 2), // Viền xám nhạt
        BorderFactory.createEmptyBorder(8, 20, 8, 20)
    ));
    btn.getMargin().set(0,0,5,0);
    btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
}

private JPanel makeStatBox(ImageIcon icon, String text, Color bgColor, Color textColor) {
    JPanel box = new RoundedPanel(15, bgColor);
    box.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 8));
    box.setAlignmentX(Component.CENTER_ALIGNMENT);
    box.setMaximumSize(new Dimension(260, 55));

    JLabel iconLabel = new JLabel(icon);
    JLabel textLabel = new JLabel(text);
    textLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
    textLabel.setForeground(textColor);

    box.add(iconLabel);
    box.add(textLabel);
    return box;
}

private ImageIcon loadDialogIcon(String path, int width, int height) {
    try {
        URL url = GameController.class.getResource(path);
        if (url == null) {
            System.err.println("❌ Không tìm thấy icon: " + path);
            return null;
        }
        Image img = new ImageIcon(url).getImage()
            .getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    } catch (Exception e) {
        System.err.println("❌ Lỗi load icon: " + e.getMessage());
        return null;
    }
}

private void resetGame() {
    gameManager.resetGame();           
    infoPanel.reset();       
    //view.renderAll(gameManager);
    }
}


