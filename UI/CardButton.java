package UI;
import Logic.Card;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.*;
import java.net.URL;

public class CardButton extends JButton {
    private int row, col;
    private static final ImageIcon CARD_BACK = loadIcon("UI/images/card-back.png");
    private Color currentBg = Color.WHITE; 
    static {
        System.out.println("CARD_BACK null? " + (CARD_BACK == null));  // kiểm tra
    }

    public CardButton(int row, int col) {
        this.row = row;
        this.col = col;
        setPreferredSize(new Dimension(80, 80));
        setContentAreaFilled(false);
        setBorderPainted(false);
        setIcon(CARD_BACK); 
        setOpaque(false);
        setText("");
        setHorizontalAlignment(CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

    // Nền trắng bo tròn
    g2.setColor(currentBg);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        // Vẽ viền
        g2.setColor(new Color(100, 210, 220));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 20, 20);

        g2.dispose();
        super.paintComponent(g);
}

    public void render(Card card) {
        if (card.isMatch()) {
            setIcon(loadIcon(card.getImagePath()));
            setBackground(new Color(76, 175, 80));
            setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3)); 
            BorderFactory.createMatteBorder(2, 0, 0, 0, Color.GRAY);
            setEnabled(false);
        } else if (card.isFlipped()) {
            setIcon(loadIcon(card.getImagePath()));
            setBackground(new Color(33, 150, 243));
            setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
            BorderFactory.createMatteBorder(2, 0, 0, 0, Color.GRAY);
        } else {
            setIcon(CARD_BACK);  
            setBorder(null);
            //setBackground(Color.DARK_GRAY);
        }
        repaint();
    }

    private static ImageIcon loadIcon(String path) {
    try {
        // Đảm bảo đường dẫn bắt đầu bằng "/" để trỏ từ thư mục gốc của src
        String resourcePath = path.startsWith("/") ? path : "/" + path;
        
        // Dùng getResource để lấy file từ classpath (chạy được cả trong file .jar)
        URL imgURL = CardButton.class.getResource(resourcePath);

        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            Image scaled = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } else {
            System.err.println("❌ Lỗi: Không tìm thấy file ảnh tại thư mục src" + resourcePath);
            return null;
        }
    } catch (Exception e) {
        System.err.println("❌ Lỗi ngoại lệ khi load ảnh: " + e.getMessage());
        return null;
    }
}

    public int getRow() { return row; }
    public int getCol() { return col; }
    public ImageIcon getCARD_BACK(){
        return CARD_BACK;
    }
}
