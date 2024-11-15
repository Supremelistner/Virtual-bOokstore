package profile.profiler.profile.Security;

import profile.profiler.profile.Services.LocalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class Securityconfigs {

    @Autowired
    LocalUserService localUserService;
    @Bean
    public JwtAuthPoint authenticationJwtTokenFilter() {
        return new JwtAuthPoint();
    }

    @Bean
    protected SecurityFilterChain webFilterChain(HttpSecurity http)throws Exception{
        http.authorizeHttpRequests(request-> request.anyRequest().permitAll());
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.csrf(csrf->csrf.disable());
        http.cors(cors->cors.disable());
        http.authenticationProvider(config());


        return   http.build();

    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new Passwordpoint();
    }

    @Bean
    AuthenticationProvider config(){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(localUserService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
