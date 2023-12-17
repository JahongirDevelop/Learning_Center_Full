package uz.pdp.learning_center_full.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import lombok.*;

//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.learning_center_full.entity.enums.UserRole;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import uz.pdp.learning_center_full.entity.enums.UserRole;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity extends BaseEntity implements UserDetails {
   private String name;
   private String surname;
   @Email
   @Column(unique = true)
   private String email;
   private String password;
   private String phoneNumber;
   @Enumerated(EnumType.STRING)
   private UserRole role;

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      Set<SimpleGrantedAuthority> authorities =
              new HashSet<>(Set.of(new SimpleGrantedAuthority("ROLE_" + role.name())));
      return authorities;
   }

   @Override
   public String getUsername() {
      return email;
   }

   @Override
   public String getPassword() {
      return password;
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


//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Set.of(new SimpleGrantedAuthority(role.name()));
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }

}
