package com.example.projectstore.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class NewReportRequest {
    @Size(min = 1, max = 60)
    private String title;
    @Size(min = 1, max = 2500)
    private String description;
}
