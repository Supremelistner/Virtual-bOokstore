package profile.profiler.profile.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import profile.profiler.profile.Services.LocalUserService;

import java.security.Key;
import java.util.Date;

@Service
public class Jwtutils {


    @Autowired
    LocalUserService service;




    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiry}")
    private int timing;

    public String getHeader(HttpServletRequest req){
        String Request=req.getHeader("Authorization");
        if(Request!=null&&Request.startsWith("Bearer")){
            return Request.substring(7);
        }
        return null;
    }

    public String generatejwt(String token){
        String jwts= Jwts.builder()
                .setSubject(token)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+timing))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
        return jwts;
    }

    Key key(){
       return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }


    public String user(String token){
        Jws<Claims> username= Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
        return username.getBody().getSubject();

    }
    public boolean validate(String token){
        try{
        String user=Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();

        if(user!=null)
            if(service.loadUserByUsername(user)!=null)


        return true;}
        catch (Exception exception){

        }
            return false;



    }

}
