package com.example.projectstore.report;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/report")
@AllArgsConstructor
@Slf4j
public class ReportController {

    private final ReportRepository reportRepository;

    @PostMapping
    public void NewReport(@Valid @RequestBody NewReportRequest request) {
        var report = new Report(
                null,
                request.getTitle(),
                request.getDescription()
        );
        reportRepository.save(report);
    }
}
