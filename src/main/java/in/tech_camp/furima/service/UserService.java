package in.tech_camp.furima.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.tech_camp.furima.entity.UserEntity;
import in.tech_camp.furima.form.RegisterForm;
import in.tech_camp.furima.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void userInsert(RegisterForm form) {
        UserEntity user = new UserEntity();

        user.setNickname(form.getNickname());
        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setLastName(form.getLastName());
        user.setFirstName(form.getFirstName());
        user.setLastNameKana(form.getLastNameKana());
        user.setFirstNameKana(form.getFirstNameKana());
        java.time.LocalDate birthday = java.time.LocalDate.of(form.getBirthYear(), form.getBirthMonth(),
                form.getBirthDay());
        user.setBirthday(birthday);

        userRepository.insert(user);
    }

    public boolean isEmailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }
}