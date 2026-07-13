package in.tech_camp.furima.service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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

    public Map<String, String> validateRegistration(RegisterForm form) {
        Map<String, String> errors = new HashMap<>();

        // メールアドレスの重複チェック
        if (isEmailExist(form.getEmail())) {
            errors.put("email", "このメールアドレスは既に登録されています");
        }


        return errors;
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
        
        LocalDate birthday = LocalDate.of(form.getBirthYear(), form.getBirthMonth(), form.getBirthDay());
        user.setBirthday(birthday);

        userRepository.insert(user);
    }

    public boolean isEmailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }
}