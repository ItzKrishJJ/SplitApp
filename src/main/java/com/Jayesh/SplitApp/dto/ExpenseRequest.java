package com.Jayesh.SplitApp.dto;

import com.Jayesh.SplitApp.model.ShareType;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseRequest {
    private BigDecimal amount;
    private String description;
    private String paidBy; // Name of person
    private List<ShareDetail> shares;

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
