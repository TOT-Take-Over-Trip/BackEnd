package com.trip.auth.model;

import com.trip.member.model.MemberDto;
import java.util.Collection;
import java.util.Collections;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetailsImpl implements UserDetails {
    private int memberId;
    private String id;
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(MemberDto member) {
//        this.memberId = member.getMemberId();
//        this.id = member.getId();
//        this.password = member.getPassword();
        //TODO: 권한 설정\
//        if (member.isAdmin()) {
//            this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        } else {
//            this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
//        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return id;
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
}
