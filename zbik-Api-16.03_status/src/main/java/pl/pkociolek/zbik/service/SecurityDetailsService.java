package pl.pkociolek.zbik.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.pkociolek.zbik.repository.UserRepository;
import pl.pkociolek.zbik.repository.entity.UserEntity;

import java.util.Collections;
@Service
@RequiredArgsConstructor
public class SecurityDetailsService implements UserDetailsService {
    private final UserRepository repo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final UserEntity byUsername = repo.findByEmailAddress(email).get();

        return new User(byUsername.getEmailAddress(), byUsername.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_" + byUsername.getRole())));
    }
}
