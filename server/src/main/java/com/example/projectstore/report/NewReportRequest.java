package com.example.projectstore.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
public class NewReportRequest {
    @Length(min = 1, max = 250)
    private String title;
    @Length(min = 1)
    private String description;
}
