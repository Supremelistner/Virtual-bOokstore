package profile.profiler.profile.Services;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailServices {
    @Autowired
    public MailServices(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    JavaMailSender javaMailSender;

    @Async
    public void send(SimpleMailMessage message){
        javaMailSender.send( message);
    }


}
