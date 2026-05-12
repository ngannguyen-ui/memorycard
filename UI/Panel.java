package UI;
import Logic.Card;
import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel{
       JPanel panel = new JPanel(new GridBagLayout());
       GridBagConstraints grid = new GridBagConstraints();
        private CardButton[][] cells;
        private GameController controller;
        private static final int ROWS = 4;
        private static final int COLS = 4;
        

    public Panel() {
        setLayout(new GridLayout(ROWS, COLS, 5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cells = new CardButton[ROWS][COLS];
        initCells();
    }

   private void initCells() {
    for (int r = 0; r < ROWS; r++) {
        for (int c = 0; c < COLS; c++) {
            // Dùng CardButton thay cho JButton. Truyền luôn row, col vào!
            CardButton btn = new CardButton(r, c); 
            
            // Xóa các dòng setFont, setBackground, v.v... ở đây đi, 
            // vì CardButton đã tự làm việc đó rồi!

            int row = r, col = c;
            btn.addActionListener(e -> handleCellClick(row, col));
            
            cells[r][c] = btn;
            add(btn);
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
    cells[row][col].render(card); 
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
