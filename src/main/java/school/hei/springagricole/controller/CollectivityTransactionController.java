package school.hei.springagricole.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import school.hei.springagricole.entity.CollectivityTransaction;
import school.hei.springagricole.service.CollectivityTransactionService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/collectivities")
public class CollectivityTransactionController {

    private final CollectivityTransactionService transactionService;

    public CollectivityTransactionController(CollectivityTransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{id}/transactions")
    public List<CollectivityTransaction> getTransactions(
            @PathVariable("id") String id,
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return transactionService.getTransactions(id, from, to);
    }
}