package rohan.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rohan.model.Users;

@Repository
public interface UserRepo extends JpaRepository<Users,Long> {
}
