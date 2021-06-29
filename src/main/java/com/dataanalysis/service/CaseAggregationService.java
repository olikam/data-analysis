package com.dataanalysis.service;

import com.dataanalysis.model.CaseAnalysis;
import com.dataanalysis.model.Variant;
import com.dataanalysis.util.EventlogRow;
import com.dataanalysis.util.JsonConverter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CaseAggregationService {

    private final List<EventlogRow> eventlogRows;

    public CaseAggregationService(List<EventlogRow> eventlogRows) {
        this.eventlogRows = eventlogRows;
    }

    /**
     * @return JSON String representing {@link CaseAnalysis} object.
     */
    public String getCaseAnalysis() {
        Map<String, List<EventlogRow>> eventlogRowsByCase = getLogsByCase();
        Map<List<String>, Long> traceCountMap = getTraceOccurrences(eventlogRowsByCase);
        int totalVariantCount = traceCountMap.size();
        int totalCaseCount = eventlogRowsByCase.size();
        List<Variant> mostOccurredVariants = getVariantsWithMostCases(totalCaseCount, traceCountMap);
        return JsonConverter.convert(new CaseAnalysis(mostOccurredVariants, totalVariantCount, totalCaseCount));
    }

    /**
     * Removes duplicates and returns logs by case id.
     */
    private Map<String, List<EventlogRow>> getLogsByCase() {
        return eventlogRows.stream()
                .distinct()
                .collect(Collectors.groupingBy(EventlogRow::getCaseId, Collectors.toList()));
    }

    /**
     * Calculates occurrences of each trace.
     */
    private Map<List<String>, Long> getTraceOccurrences(Map<String, List<EventlogRow>> eventlogRowsByCase) {
        return eventlogRowsByCase.values()
                .stream()
                .map(events -> events.stream()
                        .sorted(Comparator.comparing(EventlogRow::getTimestamp))
                        .map(EventlogRow::getEventName)
                        .collect(Collectors.toList()))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    /**
     * @return 10 most occurred variants.
     */
    private List<Variant> getVariantsWithMostCases(int totalCaseCount, Map<List<String>, Long> traceCountMap) {
        return traceCountMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .map(e -> new Variant(e.getKey(), e.getValue(), BigDecimal.valueOf((double) e.getValue() * 100 / totalCaseCount).setScale(1, RoundingMode.HALF_DOWN)))
                .collect(Collectors.toList());
    }

}
