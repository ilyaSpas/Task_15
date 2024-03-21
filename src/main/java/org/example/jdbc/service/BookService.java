package org.example.jdbc.service;

import org.example.jdbc.dao.BookDao;
import org.example.jdbc.dao.imp.BookDaoImp;
import org.example.jdbc.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookDao bookDao;

    @Autowired
    public BookService(BookDaoImp bookDao) {
        this.bookDao = bookDao;
    }

    public Book save(Book book){
        return bookDao.save(book);
    }

    public Book update(Long id, Book book) {
        return bookDao.update(id, book);
    }

    public Book get(Long id) {
        return bookDao.get(id);
    }

    public List<Book> getAll() {
        return bookDao.getAll();
    }

    public void deleteById(Long id) {
        bookDao.delete(id);
    }
}
