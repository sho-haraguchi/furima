package in.tech_camp.furima.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.tech_camp.furima.entity.UserEntity;
import in.tech_camp.furima.repository.UserRepository;

@Service
public class UserAuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserAuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException("ユーザーが見つかりません: " + email);
        }

        return new CustomUserDetails(userEntity);
    }
}