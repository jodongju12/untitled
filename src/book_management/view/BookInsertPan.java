
package book_management.view;

import book_management.domain.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class BookInsertPan extends JPanel {

    JTable table;
    DefaultTableModel model;
    ArrayList<Book> bookList;

    String[] header = {
            "도서번호", "도서명", "저자명", "출판사",
            "가격", "재고량", "카테고리"
    };

    String[] categoryNames = {
            "IT도서", "소설", "수필", "역사", "시"
    };

    String[] publishNames = {
            "한빛아카데미", "생능출판사", "민음사", "해냄출판사"
    };

    JLabel[] lbls = new JLabel[header.length];

    JTextField[] tfs = new JTextField[5];

    JComboBox<String> comboCategory;
    JComboBox<String> comboPublish;

    JButton btnInsert = new JButton("도서추가");

    JPanel panS = new JPanel(new GridLayout(3, 6));

    public BookInsertPan() {

        setLayout(new BorderLayout());

        comboCategory = new JComboBox<>(categoryNames);
        comboPublish = new JComboBox<>(publishNames);

        for (int i = 0; i < tfs.length; i++) {
            tfs[i] = new JTextField(10);
        }

        initView();
    }

    public void initView() {

        model = new DefaultTableModel(header, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        panS.removeAll();

        panS.setLayout(new GridLayout(3, 6, 5, 5));

        // 도서번호
        panS.add(new JLabel("도서번호"));
        panS.add(tfs[0]);

        // 도서명
        panS.add(new JLabel("도서명"));
        panS.add(tfs[1]);

        // 저자명
        panS.add(new JLabel("저자명"));
        panS.add(tfs[2]);

        // 가격
        panS.add(new JLabel("가격"));
        panS.add(tfs[3]);

        // 재고량
        panS.add(new JLabel("재고량"));
        panS.add(tfs[4]);

        // 출판사
        panS.add(new JLabel("출판사"));
        panS.add(comboPublish);

        // 카테고리
        panS.add(new JLabel("카테고리"));
        panS.add(comboCategory);

        // 도서 추가 버튼
        panS.add(new JLabel(""));
        panS.add(btnInsert);

        add(panS, BorderLayout.SOUTH);

        putSearchResult();
    }

    public void putSearchResult() {

        if (model == null) {
            return;
        }

        model.setRowCount(0);

        if (bookList == null) {
            return;
        }

        for (Book book : bookList) {

            Object[] row = {
                    book.getIsbn(),
                    book.getName(),
                    book.getAuthor(),
                    book.getPublish_name(),
                    book.getPrice(),
                    book.getInventory(),
                    book.getCategory_name()
            };

            model.addRow(row);
        }
    }

    public void setBookList(ArrayList<Book> bookList) {

        this.bookList = bookList;
    }

    public Book getBook() {

        Book book = new Book();

        book.setIsbn(tfs[0].getText());
        book.setName(tfs[1].getText());
        book.setAuthor(tfs[2].getText());

        book.setPrice(
                Integer.parseInt(tfs[3].getText())
        );

        book.setInventory(
                Integer.parseInt(tfs[4].getText())
        );

        book.setPublish_name(
                (String) comboPublish.getSelectedItem()
        );

        book.setCategory_name(
                (String) comboCategory.getSelectedItem()
        );

        return book;
    }

    public JButton getBtnInsert() {

        return btnInsert;
    }
}
