package spring.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Created by Mordr on 13.03.2017.
 */
public class SecurityUser extends User {
    private final models.pojo.User user;
    public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities, models.pojo.User user) {
        super(username, password, authorities);
        this.user = user;
    }

    public models.pojo.User getUser() {
        return user;
    }
}
