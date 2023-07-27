package com.assignment.bankManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {
    @NotBlank(message="Address can not be blank")
    private String address;
    @Pattern(regexp = "^\\+91[6-9][0-9]{9}$", message = "Invalid mobile number. It should be in the format +91xxxxxxxxxx")
    private String mobileNo;
    @Email
    private String email;
}
