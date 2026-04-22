package school.hei.springagricole.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.springagricole.entity.Collectivity;
import school.hei.springagricole.entity.CreateCollectivity;
import school.hei.springagricole.service.CollectivityService;

import java.util.List;

@RestController
@RequestMapping("/collectivities")
public class CollectivityController {
    private final CollectivityService collectivityService;

    public CollectivityController(CollectivityService collectivityService) {
        this.collectivityService = collectivityService;
    }

    @PostMapping
    public ResponseEntity<List<Collectivity>> createCollectivities(@RequestBody List<CreateCollectivity> requests) {
        List<Collectivity> collectivities = collectivityService.createCollectivities(requests);
        return ResponseEntity.status(HttpStatus.CREATED).body(collectivities);

    }
}
