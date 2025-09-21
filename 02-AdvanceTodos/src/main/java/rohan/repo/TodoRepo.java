package rohan.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rohan.model.Todos;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepo extends JpaRepository<Todos,Long> {
    List<Todos> findByUserId(Long userId);
    List<Todos> findByUserIdAndCompleted(Long userId, boolean completed);
    Optional<Todos> findByIdAndUserId(Long id, Long userId);

    @Query("SELECT t FROM Todos t WHERE t.user.id = :userId AND t.title LIKE %:title%")
    List<Todos> findByUserIdAndTitleContaining(@Param("userId") Long userId, @Param("title") String title);

    long countByUserIdAndCompleted(Long userId, boolean completed);
}
