package volodov.cursework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    @Bean
    public static BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public WebSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/administration/**")
                        .hasAnyAuthority("АДМИНИСТРАТОР")
                    .antMatchers("/consumer/**")
                        .hasAnyAuthority("ПОТРЕБИТЕЛЬ")
                    .antMatchers("/driver/**")
                        .hasAnyAuthority("ВОДИТЕЛЬ")
                    .antMatchers("/sign_in", "/sign_up")
                        .anonymous()
                    .antMatchers("/profile/**")
                        .authenticated()
                    .antMatchers("/", "/webjars/**", "/css/**", "/home/**")
                        .permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/sign_in")
                    .defaultSuccessUrl("/profile")
                    .permitAll()
                .and()
                    .logout()
                    .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(encoder())
                .usersByUsernameQuery("select username, password, 'true' from \"user\" where username=?")
                .authoritiesByUsernameQuery("\n" +
                        "select u.username, r.name from \"user\" u" +
                        " inner join role r on u.role_id = r.id" +
                        " where u.username=?");
    }
}
