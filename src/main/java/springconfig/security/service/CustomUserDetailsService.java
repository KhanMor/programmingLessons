package springconfig.security.service;

import common.exceptions.DAOException;
import models.entity.Role;
import models.entity.User;
import models.entity.UserRole;
import models.springdata.UserRepository;
import models.springdata.UserRoleRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springconfig.security.SecurityUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mordr on 13.03.2017.
 * Spring Security вытягивает пользователя из базы данных
 * устонавливает права
 */
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger logger = Logger.getLogger(CustomUserDetailsService.class);
    /*private UserAuthorizationDAO userAuthorizationDAO;

    @Autowired
    public void setUserAuthorizationDAO(UserAuthorizationDAO userAuthorizationDAO) {
        this.userAuthorizationDAO = userAuthorizationDAO;
    }*/
    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         /*try {
           User user = userAuthorizationDAO.findUserByEmail(username);
            List<UserRole> userRoles = userAuthorizationDAO.getUserAllRoles(user.getId());*/
            User user = userRepository.findByEmail(username);
            List<UserRole> userRoles = userRoleRepository.getUserAllRoles(user.getId());
            String password = user.getPassword();
            user.setUserRoles(userRoles);
            return new SecurityUser(username, password, getGrantedAuthorities(userRoles), user);
        /*} catch (DAOException e) {
            logger.error(e);
            return null;
        }*/
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<UserRole> userRoles) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (userRoles != null) {
            logger.trace("begin grant authorities process with roles count " + userRoles.size());
            for (UserRole userRole : userRoles) {
                Role role = userRole.getRole();
                String roleName = "ROLE_" + role.getRole();
                SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(roleName);
                logger.trace("granted authority " + roleName + " to user");
                grantedAuthorities.add(grantedAuthority);
            }
            return grantedAuthorities;
        } else {
            logger.trace("no authorities found for user");
            return null;
        }
    }
}
