package profile.profiler.profile.Database;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import profile.profiler.profile.Enumeratedtypes.Role;

import java.util.Collection;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "local_users")
public class LocalUsers implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email",unique = true,nullable = false)
    private String email;

    @Column(name = "username",unique = true,nullable = false)
    private String username;

    public void setRole(Role role) {
        this.role = role;
    }

    @Column(name="role")
    @Enumerated(EnumType.STRING)
    Role role;



    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "sellerinventory_id", unique = true)
    private Sellerinventory sellerinventory;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_inventory_id", unique = true)
    private UserInventory userInventory;

    @OneToOne(mappedBy = "localUsers",orphanRemoval = true)
    private ImageDB imageDB;

    @OneToOne(mappedBy = "localUsers", orphanRemoval = true)
    private Credentials credentials;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.toString()));
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Column(name="otp")
    private String OTP;

    @Column(name="otpexpiry")
    private Date OTPtime;
}