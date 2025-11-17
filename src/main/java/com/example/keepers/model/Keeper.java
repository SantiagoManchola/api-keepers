package com.example.keepers.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

@Entity
@Table(name = "keepers")
public class Keeper {

    @Id
    @Column(unique = true)
    @NotNull(message = "Id cannot be null")
    private Long id;

    @NotBlank(message = "First name cannot be blank")
    @Size(max = 100, message = "First name cannot exceed 100 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 100, message = "Last name cannot exceed 100 characters")
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be valid")
    @Column(unique = true)
    private String email;

    @NotNull(message = "Hire date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate hireDate;

    @NotBlank(message = "Specialization cannot be blank")
    @Size(max = 100, message = "Specialization cannot exceed 100 characters")
    private String specialization; // Ej: "Mamíferos", "Aves", "Reptiles", etc.

    @NotNull(message = "isActive cannot be null")
    private Boolean isActive; // Estado del cuidador (activo/inactivo)

    @Min(value = 0, message = "Years of experience must be greater than or equal to 0")
    private Integer yearsOfExperience;

    // Constructores
    public Keeper() {}

    public Keeper(Long id, String firstName, String lastName, String email, 
                  LocalDate hireDate, String specialization, Boolean isActive, Integer yearsOfExperience) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hireDate = hireDate;
        this.specialization = specialization;
        this.isActive = isActive;
        this.yearsOfExperience = yearsOfExperience;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public Integer getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(Integer yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }

    // Método auxiliar para obtener nombre completo
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "Keeper{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", hireDate=" + hireDate +
                ", specialization='" + specialization + '\'' +
                ", isActive=" + isActive +
                ", yearsOfExperience=" + yearsOfExperience +
                '}';
    }
}
