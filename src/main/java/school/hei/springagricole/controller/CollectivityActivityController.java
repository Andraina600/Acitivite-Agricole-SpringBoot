package school.hei.springagricole.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.springagricole.entity.ActivityMemberAttendance;
import school.hei.springagricole.entity.CollectivityActivity;
import school.hei.springagricole.entity.CreateActivityMemberAttendance;
import school.hei.springagricole.entity.CreateCollectivityActivity;
import school.hei.springagricole.service.CollectivityActivityService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/collectivities/{id}/activities")
public class CollectivityActivityController {
    private final CollectivityActivityService activityService;

    public CollectivityActivityController(CollectivityActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping
    public ResponseEntity<List<CollectivityActivity>> getActivities(@PathVariable String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(activityService.getActivities(id));
    }

    @PostMapping
    public ResponseEntity<List<CollectivityActivity> > createActivities(
            @PathVariable String id,
            @RequestBody List<CreateCollectivityActivity> requests) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(activityService.createActivities(id, requests));
    }

    @PostMapping("/{activityId}/attendance")
    public ResponseEntity<List<ActivityMemberAttendance>> saveAttendance(
            @PathVariable String id,
            @PathVariable String activityId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate activityDate,
            @RequestBody List<CreateActivityMemberAttendance> requests) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(activityService.saveAttendance(id, activityId, requests, activityDate));
    }

    @GetMapping("/{activityId}/attendance")

    public ResponseEntity<List<ActivityMemberAttendance>> getAttendance(
            @PathVariable String id,
            @PathVariable String activityId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(activityService.getAttendance(id, activityId));
    }
}