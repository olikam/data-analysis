package com.dataanalysis.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class EventlogRow {
    private String caseId;
    private String eventName;
    private OffsetDateTime timestamp;
}
