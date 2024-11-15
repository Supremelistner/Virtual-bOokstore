package profile.profiler.profile.Controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import profile.profiler.profile.DTO.Authprincipal;
import profile.profiler.profile.DTO.LoginDTO;
import profile.profiler.profile.DTO.Signupinfo;
import profile.profiler.profile.Database.LocalUsers;
import profile.profiler.profile.Repos.LocalUsersRepository;
import profile.profiler.profile.Security.Jwtutils;

import profile.profiler.profile.Services.LocalUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class USerController {

    LocalUsersRepository repo;
    LocalUserService service;
    Jwtutils utils;

    public USerController(LocalUsersRepository repo, LocalUserService service, Jwtutils utils, AuthenticationManager authenticationManager) {
        this.repo = repo;
        this.service = service;
        this.utils = utils;
        this.authenticationManager = authenticationManager;
    }


    AuthenticationManager authenticationManager;


@PostMapping("/signup")
ResponseEntity<String> sigup(@RequestBody Signupinfo info){

        service.signup(info);
        return new ResponseEntity<>("true",HttpStatus.ACCEPTED);



}

@PostMapping("/verify")
String verification(@RequestParam Map jwt){
    return utils.user(jwt.get("jwt").toString());



}
    Logger logger = LoggerFactory.getLogger(USerController.class);

@PostMapping("/login")
    ResponseEntity<String> login(@RequestBody LoginDTO info){

    Authentication authentication;

        authentication =authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(info.getUsername(),info.getPassword()));
        String jwt=utils.generatejwt(info.getUsername());

    SecurityContextHolder.getContext().setAuthentication(authentication);
    Authprincipal authprincipal=new Authprincipal();
    authprincipal.setJwt(jwt);
    authprincipal.setRoles(authentication.getAuthorities().stream().map(item->item.getAuthority()).collect(Collectors.toList()));
    return new ResponseEntity<>(jwt,HttpStatus.ACCEPTED);
}

@GetMapping("/roleset")
ResponseEntity<Boolean> role(@AuthenticationPrincipal LocalUsers principal,@RequestParam String OTP,@RequestParam String role){
    try{
        service.setRole(principal.getUsername(), OTP,role);
    }
    catch(AuthenticationException ex){
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    return new ResponseEntity<>(true,HttpStatus.ACCEPTED);
}

@GetMapping("/otp")
    ResponseEntity generateotp(@AuthenticationPrincipal LocalUsers user){
    try{
        service.generateOTP(user.getUsername());
    }
    catch(AuthenticationException ex){
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    return new ResponseEntity<>(true,HttpStatus.ACCEPTED);
}
@PostMapping("/resetpswrd")
    ResponseEntity resetpass(@AuthenticationPrincipal LocalUsers user,@RequestParam String password,@RequestParam String OTP){
    try{
        service.forgotPassword(user.getUsername(),OTP,password);
    }
    catch(AuthenticationException ex){
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    return new ResponseEntity<>(true,HttpStatus.ACCEPTED);
}
@PostMapping("/ufp")
    ResponseEntity<Boolean> Ufp(@AuthenticationPrincipal LocalUsers users, @RequestParam("file") MultipartFile file){
    try{
       boolean d=service.uploadImg(users.getId(),file);
       if(d==false)
           return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
       else
           return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    catch(AuthenticationException ex){
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
   @GetMapping("/dwfp")
    ResponseEntity<Resource> dwfp(@AuthenticationPrincipal LocalUsers users){
       try{
           Resource d=service.DownloadImg(users.getId());
           if(d==null)
               return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
           else
               return new ResponseEntity<>(d,HttpStatus.ACCEPTED);
       }
       catch(AuthenticationException ex){
           return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
       } catch (MalformedURLException e) {
           return new ResponseEntity<>(HttpStatus.CONFLICT);
       }
   }
    @PostMapping("/dfp")
    ResponseEntity<Boolean> dfp(@AuthenticationPrincipal LocalUsers users){
        try{
            boolean d=service.DeleteImg(users.getId());
            if(d==false)
                return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
            else
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        catch(AuthenticationException ex){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

}}


