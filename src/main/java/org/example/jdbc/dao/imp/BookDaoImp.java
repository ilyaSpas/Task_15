package org.example.jdbc.dao.imp;

import org.example.jdbc.dao.BookDao;
import org.example.jdbc.entity.Book;

import org.example.jdbc.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookDaoImp implements BookDao {

    private final ConnectionManager connectionManager;

    private final String SQL_INSERT = """
            INSERT INTO 
            book 
            (id, title, author, publicationYear) 
            VALUES 
            (?, ?, ?, ?)
            """;

    private final String SQL_SELECT = """
            SELECT 
            id, title, author, publicationYear
            FROM
            book 
            WHERE id = ?
            """;

    private final String SQL_SELECT_ALL = """
            SELECT 
            *
            FROM
            book
            """;

    private final String SQL_UPDATE = """
            UPDATE
            book 
            SET title = ?, author = ?, publicationYear = ?
            WHERE id = ?
            """;

    private final String SQL_DELETE = """
            DELETE FROM
            book 
            WHERE id = ?
            """;

    @Autowired
    public BookDaoImp(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public Book save(Book book) {
        try (Connection connection = connectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {
            preparedStatement.setLong(1, book.getId());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setLong(4, book.getPublicationYear());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return book;
    }

    public Book get(Long id) {
        Book bookFromDB = new Book();
        try (Connection connection = connectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bookFromDB.setId(resultSet.getLong("id"));
                bookFromDB.setTitle(resultSet.getString("title"));
                bookFromDB.setAuthor(resultSet.getString("author"));
                bookFromDB.setPublicationYear(resultSet.getLong("publicationYear"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookFromDB;
    }

    public List<Book> getAll() {
        List<Book> booksFromDB = new ArrayList<>();
        try (Connection connection = connectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book bookFromDB = new Book(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getLong("publicationYear")
                );
                booksFromDB.add(bookFromDB);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return booksFromDB;
    }

    public Book update(Long id, Book book) {
        try (Connection connection = connectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setLong(3, book.getPublicationYear());
            preparedStatement.setLong(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return book;
    }

    public void delete(Long id) {
        try (Connection connection = connectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
