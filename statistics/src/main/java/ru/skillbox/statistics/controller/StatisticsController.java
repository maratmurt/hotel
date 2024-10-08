package ru.skillbox.statistics.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.statistics.service.StatisticsService;

import java.net.MalformedURLException;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Value("${app.statistics.download-folder}")
    private String downloadFolder;

    @GetMapping("/download")
    public ResponseEntity<Resource> download(@RequestParam String fileName) throws MalformedURLException {
        String filePath = downloadFolder + fileName;

        statisticsService.exportToCsv(filePath);

        Resource fileResource = new FileUrlResource(filePath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename=" + fileName);

        return ResponseEntity.ok().headers(headers).body(fileResource);
    }

}
