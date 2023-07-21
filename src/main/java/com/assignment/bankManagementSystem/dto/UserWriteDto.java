package com.assignment.bankManagementSystem.dto;

import lombok.*;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserWriteDto {
    @Pattern(regexp = "^[A-Za-z]+$", message = "Field can not be empty and must contain alphabets only")
    private String firstName;
    @Pattern(regexp = "^[A-Za-z]+$", message = "Field can not be empty and must contain alphabets only")
    private String lastName;
    @Pattern(regexp = "^[2-9][0-9]{11}$", message = "Invalid Aadhar number. It should be a 12-digit number.")
    private String aadharNumber;
    @Past(message = "Date of birth must be in the past")
    private java.sql.Date dateOfBirth;
    @Min(18)
    @Max(100)
    private int age;
    @NotBlank(message="Address can not be blank")
    private String address;
    @Pattern(regexp = "^\\+91[6-9][0-9]{9}$", message = "Invalid mobile number. It should be in the format +91xxxxxxxxxx")
    private String mobileNo;
    @Email
    private String email;
}
