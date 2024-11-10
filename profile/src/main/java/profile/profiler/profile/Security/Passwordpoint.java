package profile.profiler.profile.Security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Passwordpoint implements PasswordEncoder {

    public Passwordpoint() {
        Bcrypt =new BCryptPasswordEncoder(5) ;
    }

    BCryptPasswordEncoder Bcrypt;

    @Override
    public String encode(CharSequence rawPassword) {
        return Bcrypt.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return Bcrypt.matches(rawPassword,encodedPassword);
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return PasswordEncoder.super.upgradeEncoding(encodedPassword);
    }
}
