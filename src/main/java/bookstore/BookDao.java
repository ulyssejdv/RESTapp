package bookstore;

import dao.DAOException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by ulysse on 06/11/2016.
 */
public interface BookDao {
    public Book insert(Book book) throws DAOException;
    public Book find(int id) throws DAOException;
    public Collection<Book> find() throws DAOException;
}
