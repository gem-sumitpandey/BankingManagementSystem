package com.assignment.bankManagementSystem.dto;

import lombok.*;

import javax.validation.constraints.DecimalMin;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountWriteDto {
    @NotNull(message ="User id can not be blank")
    private int userId;
    @Pattern(regexp = "(?i)^Savings|Current|Salary|Fixed Deposit|Recurring Deposit", message = "Invalid account type. Allowed values: Savings, Current, Salary, Fixed Depost, Recurring Deposit")
    private String accountType;
    @NotNull
    @DecimalMin(value = "499.0", inclusive = false, message = "Balance must be greater or equal to 500")
    private Double balance;
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Field must contain alphabets only")
    private String branch;
}
