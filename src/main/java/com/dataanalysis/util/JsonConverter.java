package com.dataanalysis.util;

import com.dataanalysis.model.CaseAnalysis;
import com.dataanalysis.model.Variant;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

@UtilityClass
public class JsonConverter {

    /**
     * Converts {@link CaseAnalysis} object to JsonString by using StringBuilder.
     *
     * @implNote Generating JSON manually has better performance than Jackson's ObjectMapper.
     */
    public String convert(CaseAnalysis caseAnalysis) {
        StringBuilder caseAnalysisJson = new StringBuilder("{\n  \"variantList\" : [");
        for (Variant variant : caseAnalysis.getVariantList()) {
            caseAnalysisJson.append(" {\n")
                    .append("    \"trace\" : ")
                    .append(variant.getTrace()
                            .stream()
                            .map(event -> "\"" + event + "\"")
                            .collect(Collectors.joining(", ", "[ ", " ],")))
                    .append("\n\t\"occurrences\" : ")
                    .append(variant.getOccurrences())
                    .append(",\n")
                    .append("\t\"percentage\" : ")
                    .append(variant.getPercentage())
                    .append("\n  },");
        }
        // Remove last comma
        caseAnalysisJson.setLength(caseAnalysisJson.length() - 1);
        caseAnalysisJson.append(" ],\n")
                .append("  \"totalVariants\" : ")
                .append(caseAnalysis.getTotalVariants())
                .append(",\n")
                .append("  \"totalCases\" : ")
                .append(caseAnalysis.getTotalCases())
                .append("\n}");

        return caseAnalysisJson.toString();
    }
}
