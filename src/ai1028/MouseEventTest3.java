
package ai1028;

import center.SetFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MouseEventTest3 extends JFrame {

    JLabel lbl = new JLabel("", SwingConstants.CENTER);
    JLabel caption = new JLabel("", SwingConstants.CENTER);

    String[] names = {"고양이", "강아지", "토끼"};
    String[] files = {
            "imgs/cat.jpg",
            "imgs/dog.png",
            "imgs/rabbit.png"
    };

    public MouseEventTest3() {

        JPanel panel = new JPanel(new BorderLayout(0, 20));
        PetStyle.applyPanel(panel);
        setContentPane(panel);

        // 상단 제목
        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setOpaque(false);

        JLabel title = new JLabel("PAW DIARY.");
        title.setFont(new Font("맑은 고딕", Font.BOLD, 28));

        JLabel sub = new JLabel("오늘의 귀여운 친구를 만나보세요.");
        sub.setForeground(Color.GRAY);

        header.add(title);
        header.add(sub);
        panel.add(header, BorderLayout.NORTH);

        // 버튼 영역
        JPanel content = new JPanel(new BorderLayout(0, 15));
        content.setOpaque(false);

        JPanel buttons = new JPanel(new GridLayout(1, 3, 12, 0));
        buttons.setOpaque(false);

        for (int i = 0; i < names.length; i++) {
            JButton btn = PetStyle.createButton(names[i], i);
            final int index = i;

            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    showImage(index);
                }
            });

            buttons.add(btn);
        }

        content.add(buttons, BorderLayout.NORTH);

        // 이미지 표시 영역
        JPanel card = PetStyle.createCard();

        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        lbl.setVerticalAlignment(SwingConstants.CENTER);

        caption.setFont(new Font("맑은 고딕", Font.BOLD, 17));
        caption.setForeground(new Color(80, 76, 70));

        card.add(lbl, BorderLayout.CENTER);
        card.add(caption, BorderLayout.SOUTH);

        content.add(card, BorderLayout.CENTER);
        panel.add(content, BorderLayout.CENTER);

        // 처음 실행하면 고양이 사진 표시
        showImage(0);

        SetFrame.setFrame(this, "PAW DIARY", 650, 580);
    }

    // 이미지 변경
    void showImage(int i) {
        ImageIcon icon = new ImageIcon(files[i]);

        if (icon.getIconWidth() <= 0) {
            lbl.setIcon(null);
            lbl.setText("이미지를 찾을 수 없습니다.");
            caption.setText(files[i]);
            return;
        }

        double scale = Math.min(
                500.0 / icon.getIconWidth(),
                310.0 / icon.getIconHeight()
        );

        int w = (int) (icon.getIconWidth() * scale);
        int h = (int) (icon.getIconHeight() * scale);

        Image img = icon.getImage().getScaledInstance(
                w, h, Image.SCALE_SMOOTH
        );

        lbl.setText("");
        lbl.setIcon(new ImageIcon(img));
        caption.setText("오늘의 친구 · " + names[i]);
    }

    public static void main(String[] args) {
        new MouseEventTest3();
    }
}
 