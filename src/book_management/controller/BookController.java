package book_management.controller;

import book_management.domain.Book;
import book_management.repository.BookRepository;
import book_management.view.BookInsertPan;
import book_management.view.BookSearchPan;
import book_management.view.BookUpdatePan;
import book_management.view.center.SetFrame;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookController extends JFrame {

    JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

    BookRepository repository;

    ArrayList<Book> bookList;

    JComboBox<String> combo;

    BookSearchPan searchPan;
    BookInsertPan insertPan;
    BookUpdatePan updatePan;
    JTable updateTable;

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

        // 도서 수정 및 삭제 화면 생성
        updatePan = new BookUpdatePan();
        updatePan.setBookList(bookList);
        updatePan.initView();

        // 검색 버튼 이벤트 등록
        JButton btnSearch = searchPan.getBtnSearch();
        btnSearch.addActionListener(btnSearchListener);

        // 도서 추가 버튼 이벤트 등록
        JButton btnInsert = insertPan.getBtnInsert();
        btnInsert.addActionListener(btnInsertListener);

        // 도서 수정 버튼 이벤트 등록
        JButton btnUpdate = updatePan.getBtnUpdate();
        btnUpdate.addActionListener(btnUpdateListener);

        // 수정 테이블 행 클릭 이벤트 등록
        updateTable = updatePan.getTable();
        updateTable.addMouseListener(updateTableListener);

        // 탭 등록
        tabbedPane.add("도서 검색", searchPan);
        tabbedPane.add("도서 추가", insertPan);
        tabbedPane.add("수정 및 삭제", updatePan);

        // 탭 전환 시 최신 목록 동기화
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    refreshAllPanels();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        add(tabbedPane);

        // 프레임 설정
        SetFrame.setFrame(
                this,
                "도서관리 시스템",
                700,
                600
        );
    }

    // 전체 탭 테이블 최신화
    private void refreshAllPanels() throws SQLException {
        ArrayList<Book> allBookList = repository.select("", 0);

        insertPan.setBookList(allBookList);
        insertPan.putSearchResult();

        updatePan.setBookList(allBookList);
        updatePan.putSearchResult();

        bookList = repository.select(
                searchPan.getSearchWord(),
                combo.getSelectedIndex()
        );
        searchPan.setBookList(bookList);
        searchPan.putSearchResult();
    }

    // 도서 검색 이벤트
    ActionListener btnSearchListener = new ActionListener() {
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
    ActionListener btnInsertListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Book book = insertPan.getBook();
                repository.insert(book);

                refreshAllPanels();

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
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                        thisFrame(),
                        ex.getMessage()
                );
            }
        }
    };

    // 도서 수정 이벤트
    ActionListener btnUpdateListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Book book = updatePan.getBook();

                if (book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(
                            thisFrame(),
                            "수정할 도서를 테이블에서 먼저 선택하세요."
                    );
                    return;
                }

                repository.update(book);

                refreshAllPanels();

                JOptionPane.showMessageDialog(
                        thisFrame(),
                        "도서가 수정되었습니다."
                );

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(
                        thisFrame(),
                        "도서 수정 실패: " + ex.getMessage()
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

    // 수정 탭 테이블 행 클릭 이벤트
    MouseAdapter updateTableListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 1) {
                int rowIndex = updateTable.getSelectedRow();
                updatePan.setTextField(rowIndex);
            }
        }
    };

    public static void main(String[] args) throws SQLException {
        SwingUtilities.invokeLater(() -> {
            try {
                new BookController();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}