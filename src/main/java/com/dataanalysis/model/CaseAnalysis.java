package com.dataanalysis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaseAnalysis {
    private List<Variant> variantList;
    private long totalVariants;
    private long totalCases;
}
