package bookstore;

/**
 * Created by ulysse on 25/10/2016.
 */
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "book", path = "book")
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

    List<Book> findById(@Param("id") int id);

}
