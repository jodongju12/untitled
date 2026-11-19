
package book_management.controller;

import book_management.domain.Book;
import book_management.repository.BookRepository;
import book_management.view.BookInsertPan;
import book_management.view.BookSearchPan;
import book_management.view.center.SetFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookController extends JFrame {

    JTabbedPane tabbedPane =
            new JTabbedPane(JTabbedPane.TOP);

    BookRepository repository;

    ArrayList<Book> bookList;

    JComboBox<String> combo;

    BookSearchPan searchPan;
    BookInsertPan insertPan;

    public BookController() throws SQLException {

        // 검색 화면 생성
        searchPan = new BookSearchPan();

        combo = searchPan.getCombo();

        // 데이터베이스 연결
        repository = new BookRepository();

        // 도서 목록 조회
        bookList = repository.select(
                searchPan.getSearchWord(),
                combo.getSelectedIndex()
        );

        searchPan.setBookList(bookList);
        searchPan.initView();

        // 도서 추가 화면 생성
        insertPan = new BookInsertPan();

        insertPan.setBookList(bookList);
        insertPan.initView();

        // 검색 버튼 이벤트 등록
        JButton btnSearch = searchPan.getBtnSearch();

        btnSearch.addActionListener(btnSearchListener);

        // 도서 추가 버튼 이벤트 등록
        JButton btnInsert = insertPan.getBtnInsert();

        btnInsert.addActionListener(btnInsertListener);

        // 탭 등록
        tabbedPane.add("도서 검색", searchPan);
        tabbedPane.add("도서 추가", insertPan);

        add(tabbedPane);

        // 프레임 설정
        SetFrame.setFrame(
                this,
                "도서관리 시스템",
                700,
                600
        );
    }

    // 도서 검색 이벤트
    ActionListener btnSearchListener =
            new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    try {

                        bookList = repository.select(
                                searchPan.getSearchWord(),
                                combo.getSelectedIndex()
                        );

                        searchPan.setBookList(bookList);
                        searchPan.putSearchResult();

                    } catch (SQLException ex) {

                        throw new RuntimeException(ex);
                    }
                }
            };

    // 도서 추가 이벤트
    ActionListener btnInsertListener =
            new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    try {

                        Book book = insertPan.getBook();

                        repository.insert(book);

                        // DB에 저장된 전체 도서 목록 다시 조회
                        ArrayList<Book> insertBookList =
                                repository.select("", 0);

                        // 도서 추가 화면 갱신
                        insertPan.setBookList(insertBookList);
                        insertPan.putSearchResult();

                        // 검색 화면도 갱신
                        bookList = repository.select(
                                searchPan.getSearchWord(),
                                combo.getSelectedIndex()
                        );

                        searchPan.setBookList(bookList);
                        searchPan.putSearchResult();

                        JOptionPane.showMessageDialog(
                                thisFrame(),
                                "도서가 추가되었습니다."
                        );

                    } catch (SQLException ex) {

                        JOptionPane.showMessageDialog(
                                thisFrame(),
                                "도서 추가 실패: " + ex.getMessage()
                        );

                    } catch (NumberFormatException ex) {

                        JOptionPane.showMessageDialog(
                                thisFrame(),
                                "가격과 재고량은 숫자로 입력하세요."
                        );

                    }
                }
            };

    private JFrame thisFrame() {
        return this;
    }

    public static void main(String[] args)
            throws SQLException {

        SwingUtilities.invokeLater(() -> {
            try {
                new BookController();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
