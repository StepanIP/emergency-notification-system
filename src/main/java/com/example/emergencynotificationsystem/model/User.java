package com.example.emergencynotificationsystem.model;

import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.validation.constraints.Pattern;

@Getter @Setter @NoArgsConstructor
@Entity
@ToString
@EqualsAndHashCode
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "users_id_seq"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "10"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    private long id;

    @Pattern(regexp = "[A-Z][a-z]+",
            message = "Must start with a capital letter followed by one or more lowercase letters")
    @Column(name = "name", nullable = false)
    private String name;

    @Pattern(regexp = "[A-Z][a-z]+",
            message = "Must start with a capital letter followed by one or more lowercase letters")
    @Column(name = "surname", nullable = false)
    private String surname;

    private String contact;

    public User(String name, String surname, String contact) {
        this.name = name;
        this.surname = surname;
        this.contact = contact;
    }
}
