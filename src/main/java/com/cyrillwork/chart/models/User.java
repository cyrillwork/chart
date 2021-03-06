package com.cyrillwork.chart.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@Entity
@Table(name = "usr")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min=3, max=64, message = "{validation.user}")
    private String username;

    @NotNull
    @Size(min=3, max=64, message = "{validation.password}")
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    private boolean active;

    @NotNull
    @Size(min=3, max=64, message = "{validation.email}")
    private String email;

    private String activationCode;

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
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

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public boolean getStateActivation(){
        boolean result = false;

        if((activationCode == null)||(activationCode.equals("")))
            result = true;
        else
            result = false;

        return result;
    }

    public void setRole(Role role)
    {
        if(this.roles == null) {
            this.roles = new HashSet<Role>();
        }
        else {
            this.roles.clear();
        }
        this.roles.add(role);
    }

    public void setRoles(Set<Role> roles)
    {
        this.roles = roles;
    }

    public Set<Role> getRoles()
    {
        if(this.roles == null) {
            this.roles = new HashSet<Role>();
            this.roles.add(Role.USER);
        }
        return this.roles;
    }

    public boolean hasRole(){
        return ((this.roles != null) && (!this.roles.isEmpty()));
    }

    public  boolean isAdmin() {
        boolean result = false;
        if(roles != null) {
            result = roles.stream().anyMatch(Role.ADMIN::equals);
        }
        return result;
    }

    public boolean getRoleString(String name)
    {
        if((roles == null) || (name == null))
        {
            return false;
        }

        return roles.toString().equals("[" + name + "]");
    }

}
