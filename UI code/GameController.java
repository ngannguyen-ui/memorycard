import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.*;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import java.net.URL;

public class GameController {
    private GameModel model = new GameModel();
    private Panel view = new Panel();
    private GameController controller;
    private InfoPanel infoPanel;

    public GameController(GameModel model, Panel view, InfoPanel infoPanel) {
        this.model = model;
        this.view = view;
        this.infoPanel = infoPanel;
        view.setController(this);  
    }

    public GameController getController(){
        return controller;
    }

    public void onCellClicked(int row, int col) {
        if (!model.canFlip(row, col)) return;

        model.flipCard(row, col);
        view.renderCell(row, col, model.getCard(row, col));

        if (model.hasPendingPair()) {
            checkMatch();
        }
        infoPanel.addMove();
    }

    private void checkMatch() {
        if (model.isMatch()) {
            model.confirmMatch();
            flashMatch();
            infoPanel.addScore(10);
            checkWin(); 
        } else {
            flipBackWithDelay();
        }
    }

private void flipBackWithDelay() {
    view.setEnabled(false);

    Timer timer = new Timer(800, e -> {
        // Lật ngược lại
        for (int[] pos : model.getPendingPair()) {
            model.getCard(pos[0], pos[1]).hide();
            view.renderCell(pos[0], pos[1], model.getCard(pos[0], pos[1]));
        }
        model.clearPending();
        view.setEnabled(true);
    });

    timer.setRepeats(false);
    timer.start();
}

// Nhấp nháy khi match
public void flashMatch() {
    int[] a = model.getLastMatch()[0];
    int[] b = model.getLastMatch()[1];

    Timer flash = new Timer(150, null);
    int[] count = {0};

    flash.addActionListener(e -> {
        Color color = (count[0] % 2 == 0) ? Color.YELLOW : new Color(76, 175, 80);
        view.setCellColor(a[0], a[1], color);
        view.setCellColor(b[0], b[1], color);
        if (++count[0] >= 6) flash.stop();
    });
    flash.start();
}

private void checkWin() {
    if (model.isWin()) {
        infoPanel.stopTimer();
        showWinDialog();
    }
}

private void showWinDialog() {
    
    JPanel panel = new JPanel(new BorderLayout(0, 15));
    panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    panel.setBackground(Color.WHITE);

    
    JLabel lblTitle = new JLabel("You Win!", SwingConstants.CENTER);
    lblTitle.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
    lblTitle.setForeground(new Color(255, 220, 50));

   
    JPanel statsPanel = new JPanel(new GridLayout(2, 1, 0, 5));
    statsPanel.setBackground(new Color(244, 162, 89));

    JLabel lblScore = new JLabel("Điểm: " + infoPanel.getScore(), SwingConstants.CENTER);
    JLabel lblTime  = new JLabel("Thời gian: " + infoPanel.getSeconds() + "s", SwingConstants.CENTER);
    lblScore.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
    lblTime.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
    lblScore.setForeground(Color.WHITE);
    lblTime.setForeground(Color.WHITE);

    statsPanel.add(lblScore);
    statsPanel.add(lblTime);


    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
    btnPanel.setBackground(new Color(244, 162, 89));

    JButton btnReplay = new JButton("New Game");
    JButton btnExit   = new JButton("Quit");

    styleButton(btnReplay, new Color(222, 250, 202));   
    styleButton(btnExit,   new Color(222, 250, 202));   

    btnPanel.add(btnReplay);
    btnPanel.add(btnExit);

    panel.add(lblTitle,   BorderLayout.NORTH);
    panel.add(statsPanel, BorderLayout.CENTER);
    panel.add(btnPanel,   BorderLayout.SOUTH);

    
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


private void styleButton(JButton btn, Color color) {
    btn.setBackground(color);
    btn.setForeground(Color.WHITE);
    btn.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
    btn.setFocusPainted(false);
    btn.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(color.darker(), 2),
        BorderFactory.createEmptyBorder(8, 20, 8, 20)
    ));
    btn.getMargin().set(0,0,5,0);
    btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
}

private void resetGame() {
    model.reset();           
    infoPanel.reset();       
    view.renderAll(model);   
    infoPanel.startTimer();  
}
}


