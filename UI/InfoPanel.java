package UI;
import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel{
    private JLabel lblScore = new JLabel("0");
    private JLabel lblTime  = new JLabel("0:00");
    private JLabel lblMoves = new JLabel("0");
    private int score = 0, seconds = 0;
    private int moves = 0;
    private Timer countdownTimer;
     private JLabel lblTitle = new JLabel("★ Memory Card Game ★");

    public InfoPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(255, 248, 231));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        //setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
        //lblScore.setFont(new Font("Sans Serif", Font.BOLD, 18));
        //lblTime.setFont(new Font("Sans Serif", Font.BOLD, 18));
        //add(lblScore);
        //add(lblTime);

        JPanel card = new RoundedPanel(20, Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(card);

        lblTitle.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        lblTitle.setForeground(new Color(243, 58, 133));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

         JPanel statsRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 8));
        statsRow.setBackground(Color.WHITE);
        statsRow.setAlignmentX(Component.CENTER_ALIGNMENT);

        statsRow.add(makeStatBox("Scores",   lblScore));
        statsRow.add(makeStatBox("Time",    lblTime));
        statsRow.add(makeStatBox("Moves", lblMoves));

        card.add(lblTitle);
        card.add(Box.createVerticalStrut(5));
        card.add(statsRow);

        add(card);
        }

        private JPanel makeStatBox(String labelText, JLabel valueLabel){
        JPanel box = new RoundedPanel(12, new Color(250, 202,222));
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        box.setPreferredSize(new Dimension(80, 55));                       

        JLabel title = new JLabel(labelText);
        title.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);  

        valueLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        box.add(title);
        box.add(valueLabel);
        return box;
        }
      
    public int getScore(){
        return score;
    }

    public int getSeconds(){
        return seconds;
    }

    public int getMoves(){
        return moves;
    }

    public void startTimer() {
        countdownTimer = new Timer(1000, e -> {
            seconds++;
            int m = seconds / 60, s = seconds % 60;
            lblTime.setText(m + ":" + String.format("%02d", s));
        });
        countdownTimer.start();
    }

    public void stopTimer() { 
        if (countdownTimer != null) countdownTimer.stop(); 
    }

    public boolean isTimerRunning() { // ✅ method nằm ở đây mới đúng
        return countdownTimer != null && countdownTimer.isRunning();
    }

    public void addScore(int points) { 
        score += points; 
    }

    public void setScore(int points){
        score = points;
        lblScore.setText(String.valueOf(score));
    }

    public void addMove() {
        moves++;
        lblMoves.setText(String.valueOf(moves));
    }

    public void reset() {
        score = 0; seconds = 0; moves = 0;
        lblScore.setText("0");
        lblTime.setText("0:00");
        lblMoves.setText("0");
    }
}

