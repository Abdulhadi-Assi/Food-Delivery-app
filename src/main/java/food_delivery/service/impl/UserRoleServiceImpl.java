package food_delivery.service.impl;

import food_delivery.enumeration.RoleEnum;
import food_delivery.exception.ApplicationErrorEnum;
import food_delivery.exception.BusinessException;
import food_delivery.model.Role;
import food_delivery.model.User;
import food_delivery.model.UserRole;
import food_delivery.model.UserRoleId;
import food_delivery.repository.RoleRepository;
import food_delivery.repository.UserRoleRepository;
import food_delivery.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;


    @Override
    public void addRoleToUser(RoleEnum roleEnum, User user) {
        Role roleCustomer = roleRepository.findById(roleEnum.getCode())
                .orElseThrow(() -> new BusinessException(ApplicationErrorEnum.ROLE_NOT_FOUND));
        UserRole userRoleRole = new UserRole(new UserRoleId(user.getId(),roleCustomer.getId()),user,roleCustomer);
        userRoleRepository.save(userRoleRole);
    }



    @Override
    public Set<GrantedAuthority> getUserRoles(Long userId){
        return userRoleRepository.findByUser_id(userId).stream().map(userRole -> new SimpleGrantedAuthority(userRole.getRole().getRoleName())).collect(Collectors.toSet());
    }
}
