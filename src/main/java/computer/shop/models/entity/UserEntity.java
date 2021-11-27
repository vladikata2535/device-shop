package computer.shop.models.entity;

import computer.shop.models.entity.enumEntity.UserRoleEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity{
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Integer age;
    private LocalDate bornOn;
    private BigDecimal balance;
    private String country;

    private Set<CouponEntity> notActiveCoupons = new HashSet<>();
    private Set<CouponEntity> activeCoupons = new HashSet<>();
    private Set<UserRoleEntity> roles = new HashSet<>();

    public UserEntity() {
    }

    @Column(unique = true, nullable = false)
    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    @Column(nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Column(nullable = false)
    public String getLastName() {
        return lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    @Column(unique = true,nullable = false)
    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    @Column(nullable = false)
    public Integer getAge() {
        return age;
    }

    public UserEntity setAge(Integer age) {
        this.age = age;
        return this;
    }

    @Column(nullable = false)
    public LocalDate getBornOn() {
        return bornOn;
    }

    public UserEntity setBornOn(LocalDate bornOn) {
        this.bornOn = bornOn;
        return this;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public Set<UserRoleEntity> getRoles() {
        return roles;
    }

    public UserEntity setRoles(Set<UserRoleEntity> roles) {
        this.roles = roles;
        return this;
    }

    @Column(nullable = false)
    public String getCountry() {
        return country;
    }

    public UserEntity setCountry(String country) {
        this.country = country;
        return this;
    }

    @Column(nullable = false)
    public BigDecimal getBalance() {
        return balance;
    }

    public UserEntity setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public Set<CouponEntity> getNotActiveCoupons() {
        return notActiveCoupons;
    }

    public UserEntity setNotActiveCoupons(Set<CouponEntity> notActiveCoupons) {
        this.notActiveCoupons = notActiveCoupons;
        return this;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public Set<CouponEntity> getActiveCoupons() {
        return activeCoupons;
    }

    public UserEntity setActiveCoupons(Set<CouponEntity> activeCoupons) {
        this.activeCoupons = activeCoupons;
        return this;
    }
}
