package rohan.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import rohan.model.Users;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private Long id;
    private String username;
    private String email;
    private String password;
    private Users.UserRole userRole;
    private Collection<? extends GrantedAuthority> authorities;


    //So it is static method belongs to class ,so to call it you don;t need to create object of that class

    public static CustomUserDetails create(Users user){
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_"+user.getUserRole().name()));
        return new CustomUserDetails(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getUserRole(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    public boolean hasRole(Users.UserRole role) {
        return this.userRole.equals(role);
    }

    public boolean isAdmin() {
        return this.userRole.equals(Users.UserRole.ADMIN);
    }

    public boolean isUser() {
        return this.userRole.equals(Users.UserRole.USER);
    }
}
