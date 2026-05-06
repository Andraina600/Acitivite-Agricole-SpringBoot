package school.hei.springagricole.controller;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.hei.springagricole.entity.CollectivityLocalStatistics;
import school.hei.springagricole.entity.CollectivityOverallStatistics;
import school.hei.springagricole.service.StatisticsService;

import java.time.LocalDate;
import java.util.List;

@RestController
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }


    @GetMapping("/collectivities/{id}/statistics")
    public ResponseEntity<List<CollectivityLocalStatistics>> getLocalStatistics(
            @PathVariable("id") String id,
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

        List<CollectivityLocalStatistics> stats =
                statisticsService.getLocalStatistics(id, from, to);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/collectivities/statistics")
    public ResponseEntity<List<CollectivityOverallStatistics>> getOverallStatistics(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

        List<CollectivityOverallStatistics> stats =
                statisticsService.getOverallStatistics(from, to);
        return ResponseEntity.ok(stats);
    }
}
