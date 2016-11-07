package bookstore;

import dao.DAOFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by ulysse on 24/10/2016.
 */

@RestController
@RequestMapping("/books")
public class BookController {

    @GetMapping
    public Collection<Book> list() {
        BookDaoImpl bookDaoImpl = new BookDaoImpl(DAOFactory.getInstance());
        return bookDaoImpl.find();
    }

    @GetMapping("/{id}")
    public Book retrieve(@PathVariable int id) {
        BookDaoImpl bookDaoImpl = new BookDaoImpl(DAOFactory.getInstance());
        return bookDaoImpl.find(id);
    }

    @PostMapping
    public Book create(@RequestBody Book input) {
        BookDaoImpl bookDaoImpl = new BookDaoImpl(DAOFactory.getInstance());
        return bookDaoImpl.insert(input);
    }

    @PutMapping("/{id}")
    public void update (@PathVariable int id) {
        System.out.println("update ");
    }

    @PatchMapping("/{id}")
    public void partialUpdate() {
        System.out.println("partial update");
    }

    @DeleteMapping("/{id}")
    public void destroy() {
        System.out.println("delete");
    }


}
