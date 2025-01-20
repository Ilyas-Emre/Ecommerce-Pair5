package org.turkcell.ecommercepair5.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.turkcell.ecommercepair5.entity.User;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
