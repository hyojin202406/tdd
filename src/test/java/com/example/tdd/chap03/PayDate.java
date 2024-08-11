package com.example.tdd.chap03;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayDate {
    private LocalDate firstBillingDate;
    private LocalDate billingDate;
    private int payAmount;
}
