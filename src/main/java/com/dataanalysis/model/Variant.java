package com.dataanalysis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Variant {
    private List<String> trace;
    private long occurrences;
    private BigDecimal percentage;
}
