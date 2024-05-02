package com.test2.users.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Модель для валидации входных параметров при регистрации учетной записи.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "of")
@Entity
@Table(schema = "user_service", name = "platform")
public class Platform {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "platform_name", nullable = false, unique = true)
    private String platformName;
    @Column(name = "bank_id")
    private boolean bankId;
    @Column(name = "last_name")
    private boolean lastName;
    @Column(name = "first_name")
    private boolean firstName;
    @Column(name = "middle_name")
    private boolean middleName;
    @Column(name = "birth_date")
    private boolean birthDate;
    @Column(name = "passport")
    private boolean passport;
    @Column(name = "place_birth")
    private boolean placeBirth;
    @Column(name = "phone")
    private boolean phone;
    @Column(name = "email")
    private boolean email;
    @Column(name = "address_registered")
    private boolean addressRegistered;
    @Column(name = "address_life")
    private boolean addressLife;
}
