package sfr.application.devicescontrol.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import sfr.application.devicescontrol.entities.telbook.devices_control.RoleEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.UserEntity;
import sfr.application.devicescontrol.repositories.telbook.device_control.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class SecurityService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByLoginAndIsDeletedNull(login);
        if (ObjectUtils.isEmpty(user)) {
            throw new UsernameNotFoundException(String.format(" User '%s' not found", login));
        }
        return new User(user.getLogin(), user.getPassword(), mapRoleToAuthorities(user.getRole()));
    }

    /**
     * Переобразуем роль в Authority.
     * Метод необходим для создания объекта User (Spring Security) в случае если в приложении архитектурно
     * не предусмотренно наличие прав доступа (Authority)
     * @param role - RoleEntity
     * @return Collection<? extends GrantedAuthority>
     */
    private Collection<? extends GrantedAuthority> mapRoleToAuthorities(RoleEntity role) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        return grantedAuthorities;
    }

    /**
     * Метод возвращает текущего пользователя сессии
     * @return UserEntity
     */
    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String login = authentication.getName();
            return userRepository.findByLoginAndIsDeletedNull(login);
        }
        return null;
    }
}
