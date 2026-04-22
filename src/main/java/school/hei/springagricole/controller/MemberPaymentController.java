package school.hei.springagricole.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.springagricole.entity.MemberPayment;
import school.hei.springagricole.service.MemberPaymentService;

import java.util.List;

@RestController
@RequestMapping("/members")
public class    MemberPaymentController {

    private final MemberPaymentService memberPaymentService;

    public MemberPaymentController(MemberPaymentService memberPaymentService) {
        this.memberPaymentService = memberPaymentService;
    }

    @PostMapping("/{id}/payments")
    public ResponseEntity<List<MemberPayment>> createPayments(
            @PathVariable("id") String id,
            @RequestBody List<MemberPayment> payments) {

        List<MemberPayment> createdPayments = memberPaymentService.createPayments(id, payments);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdPayments);
    }
}