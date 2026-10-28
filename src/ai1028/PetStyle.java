
package ai1028;

import javax.swing.*;
import java.awt.*;

public class PetStyle {

    static Color[] colors = {
            new Color(220, 232, 217), // 세이지 그린
            new Color(241, 221, 210), // 피치
            new Color(232, 223, 240)  // 라벤더
    };

    public static void applyPanel(JPanel panel) {
        panel.setBackground(new Color(247, 245, 240));
        panel.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        );
    }

    public static JPanel createCard() {
        JPanel card = new JPanel(new BorderLayout(0, 10)) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0,
                        getWidth(), getHeight(), 25, 25);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        card.setOpaque(false);
        card.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 20, 15)
        );

        return card;
    }

    public static JButton createButton(String name, int i) {
        Color color = colors[i];

        JButton btn = new JButton(name) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                g2.setColor(getModel().isRollover()
                        ? color.darker() : color);

                g2.fillRoundRect(0, 0,
                        getWidth(), getHeight(), 18, 18);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        btn.setForeground(new Color(75, 70, 65));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(150, 55));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return btn;
    }
}
 