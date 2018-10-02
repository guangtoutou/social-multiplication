package microservices.book.multiplicationservice.repository;

import microservices.book.multiplicationservice.domain.Multiplication;
import org.springframework.data.repository.CrudRepository;

public interface MultiplicationRepository extends CrudRepository<Multiplication, Long> {
}
