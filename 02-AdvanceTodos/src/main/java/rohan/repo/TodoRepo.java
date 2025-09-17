package rohan.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import rohan.model.Todos;

public interface TodoRepo extends JpaRepository<Todos,Long> {

}
