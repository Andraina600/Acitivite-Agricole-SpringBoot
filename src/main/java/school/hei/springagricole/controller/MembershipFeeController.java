package school.hei.springagricole.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.springagricole.entity.CreateMembershipFee;
import school.hei.springagricole.entity.MembershipFee;
import school.hei.springagricole.service.MembershipFeeService;

import java.util.List;

@RestController
@RequestMapping("/collectivities/{id}/membershipFees")
public class MembershipFeeController {

    private final MembershipFeeService membershipFeeService;

    public MembershipFeeController(MembershipFeeService membershipFeeService) {
        this.membershipFeeService = membershipFeeService;
    }

    @GetMapping
    public ResponseEntity<List<MembershipFee>> getByCollectivtyId(@PathVariable("id") String id){
        List<MembershipFee> membershipFees = membershipFeeService.getByCollectivityId(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(membershipFees);
    }

    @PostMapping
    public ResponseEntity<List<MembershipFee>> create(
            @PathVariable("id") String id,
            @RequestBody List<CreateMembershipFee> membershipFee
    ){
        List<MembershipFee> fees = membershipFeeService.create(id,  membershipFee);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(fees);
    }
}
