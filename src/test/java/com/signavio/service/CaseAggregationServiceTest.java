package com.signavio.service;

import com.dataanalysis.model.CaseAnalysis;
import com.dataanalysis.model.Variant;
import com.dataanalysis.service.CaseAggregationService;
import com.dataanalysis.util.EventlogRow;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class CaseAggregationServiceTest {

    private CaseAggregationService caseAggregationService;

    @Before
    public void setup() {
        caseAggregationService = new CaseAggregationService(createEventLogRows());
    }

    // Cases: 1,2,3,4,5
    // Variants:
    // [event1, event2, event3]: 3 occurrences, 60%
    // [event1, event2]: 2 occurrences, 40%
    private List<EventlogRow> createEventLogRows() {
        List<EventlogRow> list = new ArrayList<>();
        list.add(new EventlogRow("1", "event3",
                OffsetDateTime.of(2021, 3, 11, 0, 0, 0, 0, ZoneOffset.UTC)));
        list.add(new EventlogRow("1", "event1",
                OffsetDateTime.of(2021, 3, 10, 0, 0, 0, 0, ZoneOffset.UTC)));
        list.add(new EventlogRow("1", "event2",
                OffsetDateTime.of(2021, 3, 10, 0, 0, 0, 0, ZoneOffset.UTC)));
        list.add(new EventlogRow("1", "event1",
                OffsetDateTime.of(2021, 3, 10, 0, 0, 0, 0, ZoneOffset.UTC)));

        list.add(new EventlogRow("2", "event1",
                OffsetDateTime.of(2021, 3, 10, 3, 0, 0, 0, ZoneOffset.UTC)));
        list.add(new EventlogRow("2", "event2",
                OffsetDateTime.of(2021, 3, 11, 3, 0, 0, 0, ZoneOffset.UTC)));
        list.add(new EventlogRow("2", "event3",
                OffsetDateTime.of(2021, 3, 12, 3, 0, 0, 0, ZoneOffset.UTC)));

        list.add(new EventlogRow("3", "event1",
                OffsetDateTime.of(2021, 3, 10, 3, 0, 0, 0, ZoneOffset.UTC)));
        list.add(new EventlogRow("3", "event2",
                OffsetDateTime.of(2021, 3, 11, 3, 0, 0, 0, ZoneOffset.UTC)));

        list.add(new EventlogRow("4", "event1",
                OffsetDateTime.of(2021, 3, 10, 3, 0, 0, 0, ZoneOffset.UTC)));
        list.add(new EventlogRow("4", "event2",
                OffsetDateTime.of(2021, 3, 11, 3, 0, 0, 0, ZoneOffset.UTC)));

        list.add(new EventlogRow("5", "event1",
                OffsetDateTime.of(2021, 3, 10, 3, 0, 0, 0, ZoneOffset.UTC)));
        list.add(new EventlogRow("5", "event2",
                OffsetDateTime.of(2021, 3, 11, 3, 0, 0, 0, ZoneOffset.UTC)));
        return list;
    }

    @Test
    public void happy_path() throws IOException {
        //given
        List<String> givenTrace1 = new ArrayList<>();
        givenTrace1.add("event1");
        givenTrace1.add("event2");
        givenTrace1.add("event3");

        List<String> givenTrace2 = new ArrayList<>();
        givenTrace2.add("event1");
        givenTrace2.add("event2");

        //when
        String caseAnalysisJson = caseAggregationService.getCaseAnalysis();

        //then
        Assert.assertNotNull(caseAnalysisJson);
        CaseAnalysis caseAnalysis = convert(caseAnalysisJson);
        Assert.assertEquals(2, caseAnalysis.getTotalVariants());
        Assert.assertEquals(5, caseAnalysis.getTotalCases());
        for (Variant variant : caseAnalysis.getVariantList()) {
            List<String> actualTrace = variant.getTrace();
            Assert.assertTrue(actualTrace.equals(givenTrace1) || actualTrace.equals(givenTrace2));
            if (actualTrace.equals(givenTrace1)) {
                Assert.assertEquals(2, variant.getOccurrences());
                Assert.assertEquals(new BigDecimal("40.0"), variant.getPercentage());
            } else if (actualTrace.equals(givenTrace2)) {
                Assert.assertEquals(3, variant.getOccurrences());
                Assert.assertEquals(new BigDecimal("60.0"), variant.getPercentage());
            }
        }

    }

    private CaseAnalysis convert(String caseAnalysisJson) throws IOException {
        return new ObjectMapper().readValue(caseAnalysisJson, CaseAnalysis.class);
    }
}
