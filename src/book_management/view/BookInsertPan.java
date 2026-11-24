package book_management.view;

import book_management.domain.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;

public class BookInsertPan extends JPanel {

    JTable table;
    DefaultTableModel model;
    ArrayList<Book> bookList;

    String[] tableHeader = {
            "도서번호", "도서명", "저자명", "출판사",
            "가격", "재고량", "카테고리"
    };

    String[] header = {
            "도서번호", "도서명", "저자명", "가격",
            "재고량", "출판사", "카테고리"
    };

    String[] categoryNames = {
            "IT도서", "소설", "수필", "시"
    };

    String[] publishNames = {
            "한빛아카데미", "생능출판사", "민음사", "해냄출판사"
    };

    JLabel[] lbls = new JLabel[header.length];
    JTextField[] tfs = new JTextField[header.length - 2];
    JComboBox<String> comboCategory;
    JComboBox<String> comboPublish;
    JButton btnInsert = new JButton("도서추가");
    JPanel panS = new JPanel(new GridLayout(4, 4, 20, 2));

    public BookInsertPan() {
        setLayout(new BorderLayout());

        comboCategory = new JComboBox<>(categoryNames);
        comboPublish = new JComboBox<>(publishNames);

        for (int i = 0; i < header.length; i++) {
            lbls[i] = new JLabel(header[i], JLabel.RIGHT);
            panS.add(lbls[i]);
            if (i < header.length - 2) {
                tfs[i] = new JTextField();
                panS.add(tfs[i]);
            } else if (i == 5) {
                panS.add(comboPublish);
            } else {
                panS.add(comboCategory);
            }
        }

        JLabel emptyLbl = new JLabel(" ");
        panS.add(emptyLbl);
        panS.add(btnInsert);
    }

    public void initView() {
        removeAll();

        model = new DefaultTableModel(tableHeader, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        TableColumnModel tcm = table.getColumnModel();

        tcm.getColumn(0).setPreferredWidth(50);
        tcm.getColumn(1).setPreferredWidth(200);
        tcm.getColumn(2).setPreferredWidth(150);
        tcm.getColumn(3).setPreferredWidth(100);
        tcm.getColumn(4).setPreferredWidth(50);
        tcm.getColumn(5).setPreferredWidth(50);
        tcm.getColumn(6).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);
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
        book.setPrice(Integer.parseInt(tfs[3].getText()));
        book.setInventory(Integer.parseInt(tfs[4].getText()));
        book.setPublish_name((String) comboPublish.getSelectedItem());
        book.setCategory_name((String) comboCategory.getSelectedItem());

        return book;
    }

    public JButton getBtnInsert() {
        return btnInsert;
    }

    public JTable getTable() {
        return table;
    }
}