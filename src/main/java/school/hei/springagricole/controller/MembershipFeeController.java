package school.hei.springagricole.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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


}
