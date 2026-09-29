package ai0929.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonTest extends JFrame {

    public ButtonTest() {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSizeDim = toolkit.getScreenSize();

        // 버튼을 창 정중앙에 배치
        setLayout(new GridBagLayout());

        setTitle("Button 컴포넌트");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton btn = new JButton("메시지 대화상자 보이기");
        add(btn);

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        null,
                        "버튼을 클릭했습니다."
                );
            }
        });

        int sw = screenSizeDim.width;
        int sh = screenSizeDim.height;

        int w = 500;
        int h = 200;

        int x = sw / 2 - w / 2;
        int y = sh / 2 - h / 2;

        setBounds(x, y, w, h);

        setVisible(true);
    }

    public static void main(String[] args) {
        new ButtonTest();
    }
}
