package school.hei.springagricole.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.springagricole.entity.FinancialAccount;
import school.hei.springagricole.service.FinancialAccountService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/collectivities")
public class FinancialAccountController {

    private final FinancialAccountService financialAccountService;

    public FinancialAccountController(FinancialAccountService financialAccountService) {
        this.financialAccountService = financialAccountService;
    }

    @GetMapping("/{id}/financialAccounts")
    public ResponseEntity<List<FinancialAccount>> getFinancialAccounts(
            @PathVariable("id") String id,
            @RequestParam(value = "at", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate at) {

        List<FinancialAccount> accounts = financialAccountService.getByCollectivityId(id, at);
        return ResponseEntity.ok(accounts);
    }
}