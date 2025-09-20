package rohan.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rohan.model.Todos;
import rohan.model.Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<Users,Long> {
    Optional<Users> findByUsername(String Username);
    Optional<Users> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
