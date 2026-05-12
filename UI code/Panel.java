import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel{
       JPanel panel = new JPanel(new GridBagLayout());
       GridBagConstraints grid = new GridBagConstraints();
        private JButton[][] cells;
        private GameController controller;
        private static final int ROWS = 4;
        private static final int COLS = 4;

    public Panel() {
        setLayout(new GridLayout(ROWS, COLS, 5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cells = new JButton[ROWS][COLS];
        initCells();
    }

    private void initCells(){
        for (int r = 0; r<ROWS; r++){
            for (int c =0; c<COLS; c++){
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(80, 80));
                button.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
                button.setBackground(Color.PINK);
                button.setForeground(Color.WHITE);

                int row = r, col = c;
                button.addActionListener(e -> handleCellClick(row,col));
                cells[r][c] = button;
                add(button);
            }
        }
    }

    private void handleCellClick(int row, int col){
        if (controller != null) controller.onCellClicked(row, col);
        System.out.println("Clicked: " + row + ", " + col);
    }       
    
    public void setController(GameController controller) {
    this.controller = controller;
    }

public void renderCell(int row, int col, Card card) {
    JButton btn = cells[row][col];

    if (card.isMatch()) {
        btn.setText(card.getValue());
        btn.setBackground(new Color(76, 175, 80));  // xanh lá
        btn.setEnabled(false);
    } else if (card.isFaceUp()) {
        btn.setText(card.getValue());
        btn.setBackground(new Color(33, 150, 243)); // xanh dương
    } else {
        btn.setText("");
        btn.setBackground(Color.DARK_GRAY);         // úp xuống
    }
}

// Render toàn bộ bảng (dùng khi init / reset)
public void renderAll(GameModel model) {
    for (int r = 0; r < ROWS; r++)
        for (int c = 0; c < COLS; c++)
            renderCell(r, c, model.getCard(r, c));
}

public void setCellColor(int row, int col, Color color) {
    cells[row][col].setBackground(color);
}
    
}
