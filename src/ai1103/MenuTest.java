
package ai1103;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class MenuTest extends JFrame {

    // ================= COLORS =================

    private final Color BG = new Color(10, 13, 26);
    private final Color SIDEBAR = new Color(15, 19, 36);
    private final Color PANEL = new Color(21, 27, 48);
    private final Color PANEL2 = new Color(27, 34, 59);

    private final Color WHITE = new Color(239, 243, 255);
    private final Color GRAY = new Color(137, 149, 179);
    private final Color BLUE = new Color(83, 133, 255);
    private final Color PURPLE = new Color(157, 105, 255);
    private final Color CYAN = new Color(65, 218, 231);

    private Color editorBackground = new Color(17, 23, 42);
    private Color editorForeground = WHITE;

    // ================= COMPONENTS =================

    private JTextArea textArea;
    private JLabel fileNameLabel;
    private JLabel statusLabel;
    private JLabel lineLabel;
    private JLabel wordLabel;

    private JFileChooser chooser;
    private File currentFile;

    private boolean modified = false;

    // ================= CONSTRUCTOR =================

    public MenuTest() {

        setTitle("NEXUS - Premium Text Studio");
        setSize(1150, 760);
        setMinimumSize(new Dimension(850, 600));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        chooser = new JFileChooser();
        chooser.setFileFilter(
                new FileNameExtensionFilter("Text Files (*.txt)", "txt")
        );

        createUI();
        createMenu();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeApplication();
            }
        });

        setVisible(true);
    }

    // ================= MAIN UI =================

    private void createUI() {

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG);

        // 왼쪽 사이드바
        root.add(createSidebar(), BorderLayout.WEST);

        // 오른쪽 메인 영역
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BG);

        main.add(createHeader(), BorderLayout.NORTH);
        main.add(createWorkspace(), BorderLayout.CENTER);
        main.add(createFooter(), BorderLayout.SOUTH);

        root.add(main, BorderLayout.CENTER);

        setContentPane(root);
    }

    // ================= SIDEBAR =================

    private JPanel createSidebar() {

        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(225, 0));
        sidebar.setBackground(SIDEBAR);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        sidebar.setBorder(
                new EmptyBorder(25, 17, 20, 17)
        );

        // 브랜드 로고
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        logoPanel.setOpaque(false);
        logoPanel.setMaximumSize(new Dimension(200, 55));

        JLabel logo = new JLabel("✦");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 35));
        logo.setForeground(CYAN);

        JLabel brand = new JLabel(" NEXUS");
        brand.setFont(new Font("Segoe UI", Font.BOLD, 23));
        brand.setForeground(WHITE);

        logoPanel.add(logo);
        logoPanel.add(brand);

        sidebar.add(logoPanel);

        JLabel subtitle = label(
                "CREATIVE TEXT STUDIO",
                9,
                Font.BOLD,
                GRAY
        );

        subtitle.setBorder(new EmptyBorder(0, 4, 35, 0));
        sidebar.add(subtitle);

        sidebar.add(section("WORKSPACE"));

        sidebar.add(sideButton("▣", "Editor", true));
        sidebar.add(sideButton("◈", "Documents", false));
        sidebar.add(sideButton("◷", "Recent Files", false));

        sidebar.add(Box.createVerticalStrut(35));

        sidebar.add(section("QUICK ACTIONS"));

        sidebar.add(sideButton("＋", "New Document", false));
        sidebar.add(sideButton("↗", "Open File", false));
        sidebar.add(sideButton("↓", "Save Document", false));

        sidebar.add(Box.createVerticalStrut(35));

        sidebar.add(section("COLOR THEMES"));

        sidebar.add(createThemeButton("MIDNIGHT BLUE",
                new Color(17, 23, 42)));

        sidebar.add(createThemeButton("DEEP PURPLE",
                new Color(35, 22, 57)));

        sidebar.add(createThemeButton("DARK GREEN",
                new Color(17, 42, 37)));

        sidebar.add(createThemeButton("PURE WHITE",
                Color.WHITE));

        sidebar.add(Box.createVerticalGlue());

        // 하단 프로필 카드
        RoundedPanel profile = new RoundedPanel(
                new Color(27, 34, 59), 17
        );

        profile.setLayout(new BorderLayout(10, 0));
        profile.setBorder(new EmptyBorder(12, 10, 12, 10));
        profile.setMaximumSize(new Dimension(200, 65));

        JLabel avatar = new JLabel("N");
        avatar.setHorizontalAlignment(SwingConstants.CENTER);
        avatar.setFont(new Font("Segoe UI", Font.BOLD, 19));
        avatar.setForeground(Color.WHITE);

        avatar.setOpaque(true);
        avatar.setBackground(new Color(100, 83, 220));
        avatar.setPreferredSize(new Dimension(38, 38));

        JPanel userInfo = new JPanel();
        userInfo.setOpaque(false);
        userInfo.setLayout(new BoxLayout(userInfo, BoxLayout.Y_AXIS));

        userInfo.add(label("NEXUS USER", 11, Font.BOLD, WHITE));
        userInfo.add(label("Premium Workspace", 9, Font.PLAIN, GRAY));

        profile.add(avatar, BorderLayout.WEST);
        profile.add(userInfo, BorderLayout.CENTER);

        sidebar.add(profile);

        return sidebar;
    }

    private JLabel section(String text) {

        JLabel label = label(text, 10, Font.BOLD, GRAY);
        label.setBorder(new EmptyBorder(0, 5, 12, 0));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        return label;
    }

    private JPanel sideButton(
            String icon,
            String name,
            boolean active
    ) {

        JPanel button = new JPanel(new BorderLayout(12, 0));

        button.setBackground(
                active ? new Color(39, 54, 91) : SIDEBAR
        );

        button.setBorder(new EmptyBorder(11, 12, 11, 12));
        button.setMaximumSize(new Dimension(200, 43));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel iconLabel = label(
                icon, 17, Font.BOLD,
                active ? CYAN : GRAY
        );

        JLabel nameLabel = label(
                name, 12, active ? Font.BOLD : Font.PLAIN,
                active ? WHITE : GRAY
        );

        button.add(iconLabel, BorderLayout.WEST);
        button.add(nameLabel, BorderLayout.CENTER);

        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(37, 45, 75));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(
                        active ? new Color(39, 54, 91) : SIDEBAR
                );
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                switch (name) {
                    case "New Document":
                        newFile();
                        break;
                    case "Open File":
                        openFile();
                        break;
                    case "Save Document":
                        saveFile();
                        break;
                }
            }
        });

        return button;
    }

    // ================= THEME BUTTONS =================

    private JPanel createThemeButton(String name, Color color) {

        JPanel item = new JPanel(new BorderLayout(10, 0));
        item.setOpaque(false);
        item.setMaximumSize(new Dimension(200, 37));
        item.setBorder(new EmptyBorder(5, 5, 5, 5));
        item.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel swatch = new JPanel();
        swatch.setBackground(color);
        swatch.setPreferredSize(new Dimension(19, 19));

        swatch.setBorder(
                BorderFactory.createLineBorder(
                        new Color(95, 105, 135)
                )
        );

        item.add(swatch, BorderLayout.WEST);

        item.add(
                label(name, 10, Font.BOLD, GRAY),
                BorderLayout.CENTER
        );

        item.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                item.setBackground(PANEL2);
                item.setOpaque(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                item.setOpaque(false);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                changeBackground(color);
            }
        });

        return item;
    }

    // ================= HEADER =================

    private JPanel createHeader() {

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG);
        header.setBorder(new EmptyBorder(23, 28, 18, 28));

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        JLabel heading = label(
                "Your Creative Space",
                24,
                Font.BOLD,
                WHITE
        );

        JLabel description = label(
                "Create something extraordinary.",
                12,
                Font.PLAIN,
                GRAY
        );

        left.add(heading);
        left.add(Box.createVerticalStrut(5));
        left.add(description);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        right.setOpaque(false);

        JLabel online = label(
                "● SYSTEM ONLINE",
                10,
                Font.BOLD,
                CYAN
        );

        JButton save = new ModernButton("SAVE", BLUE);
        save.addActionListener(e -> saveFile());

        right.add(online);
        right.add(save);

        header.add(left, BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);

        return header;
    }

    // ================= WORKSPACE =================

    private JPanel createWorkspace() {

        JPanel workspace = new JPanel(new BorderLayout(0, 17));
        workspace.setBackground(BG);

        workspace.setBorder(
                new EmptyBorder(5, 27, 20, 27)
        );

        // 툴바
        workspace.add(createToolbar(), BorderLayout.NORTH);

        // 에디터 카드
        RoundedPanel editorCard = new RoundedPanel(PANEL, 20);
        editorCard.setLayout(new BorderLayout());

        editorCard.setBorder(
                new EmptyBorder(19, 20, 17, 20)
        );

        // 문서 제목 영역
        JPanel documentHeader = new JPanel(new BorderLayout());
        documentHeader.setOpaque(false);
        documentHeader.setBorder(
                new EmptyBorder(0, 0, 16, 0)
        );

        JPanel documentInfo = new JPanel();
        documentInfo.setOpaque(false);
        documentInfo.setLayout(new BoxLayout(
                documentInfo, BoxLayout.Y_AXIS
        ));

        fileNameLabel = label(
                "Untitled Document",
                16,
                Font.BOLD,
                WHITE
        );

        JLabel documentDescription = label(
                "TEXT DOCUMENT  /  UTF-8",
                9,
                Font.BOLD,
                GRAY
        );

        documentInfo.add(fileNameLabel);
        documentInfo.add(Box.createVerticalStrut(5));
        documentInfo.add(documentDescription);

        JLabel live = label(
                "● LIVE EDITOR",
                10,
                Font.BOLD,
                CYAN
        );

        documentHeader.add(documentInfo, BorderLayout.WEST);
        documentHeader.add(live, BorderLayout.EAST);

        editorCard.add(documentHeader, BorderLayout.NORTH);

        // 편집창
        textArea = new JTextArea();

        textArea.setFont(
                new Font("Consolas", Font.PLAIN, 16)
        );

        textArea.setBackground(editorBackground);
        textArea.setForeground(editorForeground);
        textArea.setCaretColor(CYAN);

        textArea.setSelectionColor(
                new Color(72, 95, 155)
        );

        textArea.setSelectedTextColor(Color.WHITE);

        textArea.setMargin(new Insets(22, 22, 22, 22));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        textArea.setBorder(null);

        JScrollPane scroll = new JScrollPane(textArea);

        scroll.setBorder(
                BorderFactory.createLineBorder(
                        new Color(46, 57, 86)
                )
        );

        scroll.getViewport().setBackground(editorBackground);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        editorCard.add(scroll, BorderLayout.CENTER);

        // 편집창 아래 설명
        JLabel hint = label(
                "TIP  •  Start typing to create your document",
                10,
                Font.PLAIN,
                GRAY
        );

        hint.setBorder(new EmptyBorder(13, 3, 0, 0));

        editorCard.add(hint, BorderLayout.SOUTH);

        workspace.add(editorCard, BorderLayout.CENTER);

        textArea.getDocument().addDocumentListener(
                new DocumentListener() {

                    public void insertUpdate(DocumentEvent e) {
                        updateDocument();
                    }

                    public void removeUpdate(DocumentEvent e) {
                        updateDocument();
                    }

                    public void changedUpdate(DocumentEvent e) {
                        updateDocument();
                    }
                }
        );

        return workspace;
    }

    // ================= TOOLBAR =================

    private JPanel createToolbar() {

        RoundedPanel toolbar = new RoundedPanel(PANEL, 15);

        toolbar.setLayout(new BorderLayout());
        toolbar.setBorder(
                new EmptyBorder(9, 12, 9, 12)
        );

        JPanel left = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 7, 0)
        );

        left.setOpaque(false);

        addTool(left, "＋  NEW", "New Document", e -> newFile());
        addTool(left, "▣  OPEN", "Open File", e -> openFile());
        addTool(left, "↓  SAVE", "Save Document", e -> saveFile());

        JPanel right = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 8, 0)
        );

        right.setOpaque(false);

        JLabel themeLabel = label(
                "EDITOR TOOLS",
                9,
                Font.BOLD,
                GRAY
        );

        right.add(themeLabel);

        toolbar.add(left, BorderLayout.WEST);
        toolbar.add(right, BorderLayout.EAST);

        return toolbar;
    }

    private void addTool(
            JPanel parent,
            String text,
            String tooltip,
            ActionListener action
    ) {

        JButton button = new ModernButton(text, PANEL2);
        button.setToolTipText(tooltip);
        button.addActionListener(action);

        parent.add(button);
    }

    // ================= FOOTER =================

    private JPanel createFooter() {

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(SIDEBAR);

        footer.setBorder(
                new EmptyBorder(11, 25, 11, 25)
        );

        statusLabel = label(
                "● READY",
                10,
                Font.BOLD,
                new Color(74, 222, 128)
        );

        JPanel right = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 18, 0)
        );

        right.setOpaque(false);

        lineLabel = label(
                "LINES  1",
                10,
                Font.BOLD,
                GRAY
        );

        wordLabel = label(
                "CHARACTERS  0",
                10,
                Font.BOLD,
                GRAY
        );

        right.add(lineLabel);
        right.add(wordLabel);

        footer.add(statusLabel, BorderLayout.WEST);
        footer.add(right, BorderLayout.EAST);

        return footer;
    }

    // ================= DOCUMENT FUNCTIONS =================

    private void newFile() {

        if (!confirmUnsaved()) return;

        textArea.setText("");
        currentFile = null;
        modified = false;

        fileNameLabel.setText("Untitled Document");
        statusLabel.setText("● NEW DOCUMENT");

        textArea.requestFocusInWindow();
    }

    private void openFile() {

        if (!confirmUnsaved()) return;

        if (chooser.showOpenDialog(this) ==
                JFileChooser.APPROVE_OPTION) {

            File file = chooser.getSelectedFile();

            try {

                String content = new String(
                        Files.readAllBytes(file.toPath()),
                        StandardCharsets.UTF_8
                );

                textArea.setText(content);

                currentFile = file;
                modified = false;

                fileNameLabel.setText(file.getName());
                statusLabel.setText("● FILE OPENED");

            } catch (IOException ex) {

                showError("Unable to open file.", ex);
            }
        }
    }

    private void saveFile() {

        if (currentFile == null) {

            if (chooser.showSaveDialog(this) !=
                    JFileChooser.APPROVE_OPTION) {
                return;
            }

            currentFile = chooser.getSelectedFile();

            if (!currentFile.getName().toLowerCase().endsWith(".txt")) {
                currentFile = new File(
                        currentFile.getAbsolutePath() + ".txt"
                );
            }
        }

        try {

            Files.write(
                    currentFile.toPath(),
                    textArea.getText().getBytes(StandardCharsets.UTF_8)
            );

            modified = false;

            fileNameLabel.setText(currentFile.getName());
            statusLabel.setText("● SAVED SUCCESSFULLY");

        } catch (IOException ex) {

            showError("Unable to save file.", ex);
        }
    }

    private boolean confirmUnsaved() {

        if (!modified) return true;

        int result = JOptionPane.showConfirmDialog(
                this,
                "You have unsaved changes. Continue without saving?",
                "Unsaved Changes",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        return result == JOptionPane.YES_OPTION;
    }

    private void closeApplication() {

        if (confirmUnsaved()) {
            dispose();
        }
    }

    // ================= THEME =================

    private void changeBackground(Color color) {

        editorBackground = color;

        if (color.equals(Color.WHITE)) {

            editorForeground = new Color(25, 35, 55);

        } else {

            editorForeground = WHITE;
        }

        textArea.setBackground(editorBackground);
        textArea.setForeground(editorForeground);
        textArea.setCaretColor(
                color.equals(Color.WHITE) ? BLUE : CYAN
        );

        textArea.getParent().setBackground(editorBackground);

        statusLabel.setText("● THEME UPDATED");
    }

    // ================= DOCUMENT STATUS =================

    private void updateDocument() {

        modified = true;

        int characters = textArea.getText().length();

        int lines = textArea.getLineCount();

        lineLabel.setText("LINES  " + lines);
        wordLabel.setText("CHARACTERS  " + characters);

        if (currentFile != null) {
            fileNameLabel.setText(
                    currentFile.getName() + "  *"
            );
        } else {
            fileNameLabel.setText("Untitled Document  *");
        }

        statusLabel.setText("● UNSAVED CHANGES");
    }

    // ================= MENU BAR =================

    private void createMenu() {

        JMenuBar menuBar = new JMenuBar();

        menuBar.setBackground(SIDEBAR);
        menuBar.setBorder(
                new EmptyBorder(5, 10, 5, 10)
        );

        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");

        addMenuItem(file, "New", e -> newFile());
        addMenuItem(file, "Open", e -> openFile());
        addMenuItem(file, "Save", e -> saveFile());

        file.addSeparator();

        addMenuItem(file, "Close", e -> closeApplication());

        addMenuItem(edit, "Midnight Blue",
                e -> changeBackground(new Color(17, 23, 42)));

        addMenuItem(edit, "Deep Purple",
                e -> changeBackground(new Color(35, 22, 57)));

        addMenuItem(edit, "Dark Green",
                e -> changeBackground(new Color(17, 42, 37)));

        addMenuItem(edit, "White",
                e -> changeBackground(Color.WHITE));

        menuBar.add(file);
        menuBar.add(edit);

        setJMenuBar(menuBar);
    }

    private void addMenuItem(
            JMenu menu,
            String name,
            ActionListener action
    ) {

        JMenuItem item = new JMenuItem(name);
        item.addActionListener(action);

        menu.add(item);
    }

    // ================= CUSTOM COMPONENTS =================

    private JLabel label(
            String text,
            int size,
            int style,
            Color color
    ) {

        JLabel label = new JLabel(text);

        label.setFont(
                new Font("Segoe UI", style, size)
        );

        label.setForeground(color);

        return label;
    }

    // Rounded panel
    class RoundedPanel extends JPanel {

        private final Color color;
        private final int radius;

        RoundedPanel(Color color, int radius) {

            this.color = color;
            this.radius = radius;

            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(color);

            g2.fillRoundRect(
                    0, 0, getWidth(), getHeight(),
                    radius, radius
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    // Modern button
    class ModernButton extends JButton {

        private final Color baseColor;
        private boolean hovered = false;

        ModernButton(String text, Color color) {

            super(text);

            baseColor = color;

            setFont(new Font("Segoe UI", Font.BOLD, 11));
            setForeground(WHITE);

            setPreferredSize(new Dimension(95, 37));

            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);

            setCursor(new Cursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {

                @Override
                public void mouseEntered(MouseEvent e) {
                    hovered = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hovered = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            Color background = hovered
                    ? baseColor.brighter()
                    : baseColor;

            g2.setColor(background);

            g2.fillRoundRect(
                    0, 0, getWidth(), getHeight(), 11, 11
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    // ================= ERROR =================

    private void showError(String message, Exception ex) {

        JOptionPane.showMessageDialog(
                this,
                message + "\n" + ex.getMessage(),
                "NEXUS ERROR",
                JOptionPane.ERROR_MESSAGE
        );
    }

    // ================= MAIN =================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {

                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName()
                );

            } catch (Exception e) {
                e.printStackTrace();
            }

            new MenuTest();
        });
    }
}
