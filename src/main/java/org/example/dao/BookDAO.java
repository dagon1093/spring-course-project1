package org.example.dao;

import org.example.models.Book;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> showAll(){
        BeanPropertyRowMapper beanPropertyRowMapper = new BeanPropertyRowMapper<>(Book.class);
        beanPropertyRowMapper.setPrimitivesDefaultedForNullValue(true);
        return jdbcTemplate.query("SELECT * FROM book", beanPropertyRowMapper);
    }

    public void save(Book book){
        jdbcTemplate.update("INSERT INTO book(title, author, year) VALUES(?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getYear());
    }

    public Book show(int id){
        return jdbcTemplate.query("SELECT * FROM book where id = ? ", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void update(int id, Book updatedBook){
        jdbcTemplate.update("UPDATE Book SET person_id=?, title=?, author=?, year=? where id = ?",
                updatedBook.getPerson_id(), updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getYear(),
                id);
    }

    public Optional<Person> getBookOwner(int id){
        return jdbcTemplate.query("SELECT Person.* from person " +
                "join book ON book.person_id = person.id " +
                "where book.id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Book Where id = ?", id);
    }

    public void releaseBook(int id){
        jdbcTemplate.update("UPDATE book SET person_id = NULL WHERE id = ?", id);
    }

}
