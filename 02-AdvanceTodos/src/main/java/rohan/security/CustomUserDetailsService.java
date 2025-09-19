package rohan.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rohan.exception.UserNotFound;
import rohan.model.Users;
import rohan.repo.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user =userRepo.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not fond ronniiiiie"));
        return CustomUserDetails.create(user);
    }
}
