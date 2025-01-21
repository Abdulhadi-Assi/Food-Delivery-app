package food_delivery.repository;

import food_delivery.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole , Long> {
    List<UserRole> findByUser_userId(Long id);
}
