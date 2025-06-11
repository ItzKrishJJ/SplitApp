package com.Jayesh.SplitApp.dto;

import com.Jayesh.SplitApp.model.ShareType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseRequest {
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", inclusive = true, message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotBlank(message = "PaidBy person name is required")
    private String paidBy;

    @NotEmpty(message = "Shares must not be empty")
    private List<@Valid ShareDetail> shares;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ShareDetail {
        private String name;
        private ShareType shareType;
        private BigDecimal value;
    }
}
