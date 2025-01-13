package food_delivery.service;

import food_delivery.enumeration.RoleEnum;
import food_delivery.model.User;
import org.springframework.security.core.GrantedAuthority;
import java.util.Set;

public interface UserRoleService {
    void addRoleToUser(RoleEnum roleEnum, User user) ;
    Set<GrantedAuthority> getUserRoles(Long id);
}
