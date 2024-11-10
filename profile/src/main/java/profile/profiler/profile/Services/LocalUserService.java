package profile.profiler.profile.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import profile.profiler.profile.DTO.Filedownload;
import profile.profiler.profile.DTO.LoginDTO;
import profile.profiler.profile.DTO.Signupinfo;
import profile.profiler.profile.Database.LocalUsers;
import profile.profiler.profile.Enumeratedtypes.Role;
import profile.profiler.profile.Repos.LocalUsersRepository;
import profile.profiler.profile.Security.Passwordpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.Random;

@Service
public class LocalUserService implements UserDetailsService {
 @Autowired
   Passwordpoint point;

 @Autowired MailServices mailServices;

@Autowired

LocalUsersRepository localUsersRepository;
@Autowired
Filedownload filedownload;


    public LocalUserService(Passwordpoint point, MailServices mailServices, LocalUsersRepository localUsersRepository) {
        this.point = point;
        this.mailServices = mailServices;
        this.localUsersRepository = localUsersRepository;


    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(localUsersRepository.findByUsernameIgnoreCase(username)==null)
            throw new RuntimeException();
        LocalUsers user=localUsersRepository.findByUsernameIgnoreCase(username);
        return user;

    }
    public void signup(Signupinfo signupinfo){
        if(localUsersRepository.findByUsernameIgnoreCase(signupinfo.getUsername())!=null||localUsersRepository.findByEmail(signupinfo.getEmail())!=null)
            throw new RuntimeException();
        LocalUsers user=new LocalUsers();
        user.setEmail(signupinfo.getEmail());
        user.setPassword(point.encode(signupinfo.getPassword()));
        user.setUsername(signupinfo.getUsername());
        user.setRole(Role.ROLE_New);
        localUsersRepository.save(user);
    }


    @Value("${spring.mail.username}")String from;


    public void generateOTP(String username){
        if(localUsersRepository.findByUsernameIgnoreCase(username)==null)
            throw new RuntimeException();
        LocalUsers user=localUsersRepository.findByUsernameIgnoreCase(username);
        Random rng=new Random();
        int random= rng.nextInt(100000,999999);
        String rnd=Integer.toString(random);
        user.setOTP(rnd);
        user.setOTPtime(new Date());
        localUsersRepository.save(user);
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom( from );
        message.setTo(user.getEmail());
        message.setSubject("Verify your OTP");
        message.setText("Dear user your one time password to activate your account is"+rnd+"   the given code is only viable for 10 minutes");
        mailServices.send(message);
    }

    public boolean confirmOTP(String Username,String OTP){
        if(localUsersRepository.findByUsernameIgnoreCase(Username)==null)
            throw new RuntimeException();
        LocalUsers user=localUsersRepository.findByUsernameIgnoreCase(Username);
        Date d=new Date();
        Date d1=user.getOTPtime();
        long difference=d.getTime()- d1.getTime();
        if(difference<=(1000*10*60)){
            if(OTP== user.getOTP());

            return true;}
            return false;
    }

    public void setRole(String Username,String OTP,String role){
        if(confirmOTP(Username,OTP)){
            if(localUsersRepository.findByUsernameIgnoreCase(Username)==null)
            throw new RuntimeException();
            LocalUsers user=localUsersRepository.findByUsernameIgnoreCase(Username);
            user.setRole(Role.valueOf(role));
            localUsersRepository.save(user);
        }

    }
    public void forgotPassword(String Username,String OTP,String Password){
        if(confirmOTP(Username,OTP)){
            if(localUsersRepository.findByUsernameIgnoreCase(Username)==null)
            throw new RuntimeException();
            LocalUsers user=localUsersRepository.findByUsernameIgnoreCase(Username);
            user.setPassword(point.encode(Password));
            localUsersRepository.save(user);
    }

    }
    public Boolean uploadImg(long id, MultipartFile file){
        return filedownload.uploadprofile(id,file);
    }
    public Resource DownloadImg(long id) throws MalformedURLException {
        return  filedownload.dwprofile(id).getBody();
    }
    public boolean DeleteImg(long id){
        return filedownload.dpfp(id);
    }



}



