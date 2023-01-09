package org.example.dao;

import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> showAll(){
        return jdbcTemplate.query("SELECT * FROM person",
                new BeanPropertyRowMapper<>(Person.class));
    }

    public void save(Person person){
        jdbcTemplate.update("INSERT INTO Person(name, birthday) VALUES (?,?)",
                person.getName(),
                person.getBirthday());
              }

    public Person show(int id){
        return jdbcTemplate.query("SELECT * FROM person WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void update(int id, Person updatedPerson){
        jdbcTemplate.update("UPDATE Person SET name=?, birthday=? WHERE id=?",
                updatedPerson.getName(),
                updatedPerson.getBirthday(),
                id);
    }
    public void delete(int id){
        jdbcTemplate.update("DELETE FROM person where id = ?", id);
    }

    public Optional<Person> getPersonByName(String name){
       return jdbcTemplate.query("SELECT * from person where name = ?",
               new Object[]{name},
               new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
}
