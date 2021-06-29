package com.dataanalysis;

import com.dataanalysis.service.CaseAggregationService;
import com.dataanalysis.util.CSVReader;
import com.dataanalysis.util.EventlogRow;

import java.util.List;

public class App {

    public static void main(String[] args) {
        List<EventlogRow> eventlogRows = CSVReader.readFile("Activity_Log_2004_to_2014.csv");

        long begin = System.currentTimeMillis();

        String result = new CaseAggregationService(eventlogRows).getCaseAnalysis();

        long end = System.currentTimeMillis();

        System.out.println(result);
        System.out.println(String.format("Duration: %s milliseconds", end - begin));
    }
}
