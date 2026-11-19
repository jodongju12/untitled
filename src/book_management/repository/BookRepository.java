
package book_management.repository;

import book_management.domain.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookRepository {

    ArrayList<Book> bookList;

    // 도서 검색
    public ArrayList<Book> select(
            String searchWord,
            int selectedIndex) throws SQLException {

        Connection con = JDBCConnector.getConnection();

        String[] columnName = {
                "b.name",
                "p.publish_name",
                "b.author"
        };

        if (selectedIndex < 0 || selectedIndex >= columnName.length) {
            selectedIndex = 0;
        }

        String sql =
                "SELECT b.isbn, b.name, b.author, b.price, " +
                        "b.inventory, c.category_name, p.publish_name " +
                        "FROM book b, category c, publish p " +
                        "WHERE b.category_id = c.category_id " +
                        "AND b.publish_id = p.publish_id " +
                        "AND " + columnName[selectedIndex] + " LIKE ?";

        bookList = new ArrayList<>();

        try (
                PreparedStatement pstmt = con.prepareStatement(sql)
        ) {

            pstmt.setString(
                    1,
                    "%" + (searchWord == null ? "" : searchWord) + "%"
            );

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {

                    Book book = new Book();

                    book.setIsbn(rs.getString("isbn"));
                    book.setName(rs.getString("name"));
                    book.setAuthor(rs.getString("author"));
                    book.setPrice(rs.getInt("price"));
                    book.setInventory(rs.getInt("inventory"));

                    book.setCategory_name(
                            rs.getString("category_name")
                    );

                    book.setPublish_name(
                            rs.getString("publish_name")
                    );

                    bookList.add(book);
                }
            }

        } finally {
            con.close();
        }

        return bookList;
    }

    // 도서 추가
    public void insert(Book book) {

        Connection con = JDBCConnector.getConnection();

        String sql =
                "INSERT INTO book " +
                        "(isbn, name, author, price, inventory, category_id, publish_id) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

        // 출판사명을 ID로 변환
        int publish_id = 0;

        String publishName = String.valueOf(book.getPublish_name());

        if (publishName.equals("한빛아카데미")) {
            publish_id = 1;
        } else if (publishName.equals("생능출판사")) {
            publish_id = 2;
        } else if (publishName.equals("민음사")) {
            publish_id = 3;
        } else if (publishName.equals("해냄출판사")) {
            publish_id = 4;
        }

        // 카테고리명을 ID로 변환
        int category_id = 0;

        String categoryName = String.valueOf(book.getCategory_name());

        if (categoryName.equals("IT도서")) {
            category_id = 10;
        } else if (categoryName.equals("소설")) {
            category_id = 20;
        } else if (categoryName.equals("수필")) {
            category_id = 30;
        } else if (categoryName.equals("시")) {
            category_id = 40;
        }

        if (publish_id == 0 || category_id == 0) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            throw new IllegalArgumentException(
                    "출판사 또는 카테고리를 확인하세요."
            );
        }

        try (
                PreparedStatement pstmt = con.prepareStatement(sql)
        ) {

            pstmt.setString(1, book.getIsbn());
            pstmt.setString(2, book.getName());
            pstmt.setString(3, book.getAuthor());

            pstmt.setInt(4, book.getPrice());
            pstmt.setInt(5, book.getInventory());

            pstmt.setInt(6, category_id);
            pstmt.setInt(7, publish_id);

            // 실제 DB 저장 실행
            pstmt.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(e);

        } finally {

            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
