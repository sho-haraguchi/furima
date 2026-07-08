package in.tech_camp.furima.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        // ログアウト状態でも実行できるGETリクエスト（トップページ、新規登録、ログイン、静的ファイル）
                        .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/users/sign_up", "/users/sign_in",
                                "/error")
                        .permitAll()
                        // ログアウト状態でも実行できるPOSTリクエスト（新規登録の送信処理）
                        .requestMatchers(HttpMethod.POST, "/users/sign_up").permitAll()
                        // 上記以外のリクエストは認証されたユーザーのみ許可される(要ログイン)
                        .anyRequest().authenticated())

                .formLogin(login -> login
                        // ログインフォームのボタンをクリックした時に送信するパス
                        .loginProcessingUrl("/login")
                        // ログインページのパスを設定
                        .loginPage("/login")
                        // ログイン成功後のリダイレクト先（トップページへ）
                        .defaultSuccessUrl("/", true)
                        // ログイン失敗後のリダイレクト先（ログイン画面にエラーパラメータ付きで戻す）
                        .failureUrl("/login?error")
                        // ログイン画面のemail入力欄のname属性
                        .usernameParameter("email")
                        .permitAll())

                .logout(logout -> logout
                        // ログアウト処理のパス
                        .logoutUrl("/logout")
                        // ログアウト成功時のリダイレクト先（トップページへ）
                        .logoutSuccessUrl("/")
                        .permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}