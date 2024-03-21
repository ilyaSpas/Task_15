package org.example.jdbc.dao;

import org.example.jdbc.entity.Book;

import java.util.List;

public interface BookDao {
    Book save(Book book);

    Book update(Long id, Book book);

    Book get(Long id);

    List<Book> getAll();

    void delete(Long id);
}
