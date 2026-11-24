package book_management.view;

import book_management.domain.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;

public class BookSearchPan extends JPanel {

    JTextField fieldSearch = new JTextField(20);

    JButton btnSearch = new JButton("검색");

    String[] comboStr = {
            "도서명", "출판사명", "저자명"
    };

    JComboBox<String> combo =
            new JComboBox<>(comboStr);

    JTable table;

    DefaultTableModel model;

    String[] tableHeader = {
            "도서번호", "도서명", "저자명",
            "출판사", "가격", "재고량", "카테고리"
    };

    ArrayList<Book> bookList;

    String searchWord;

    public BookSearchPan() {

        setLayout(new BorderLayout());

        JPanel panN = new JPanel();

        JLabel lbl = new JLabel("검색어: ");

        panN.add(lbl);
        panN.add(fieldSearch);
        panN.add(combo);
        panN.add(btnSearch);

        add("North", panN);
    }

    public void initView() {

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

        JScrollPane scroll = new JScrollPane(table);

        add("Center", scroll);

        putSearchResult();
    }

    // 검색 결과 테이블 출력
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

    // 도서 목록 설정
    public void setBookList(ArrayList<Book> bookList) {

        this.bookList = bookList;
    }

    // 콤보박스 반환
    public JComboBox<String> getCombo() {

        return combo;
    }

    // 검색어 반환
    public String getSearchWord() {

        return fieldSearch.getText();
    }

    // 검색 버튼 반환
    public JButton getBtnSearch() {

        return btnSearch;
    }
}