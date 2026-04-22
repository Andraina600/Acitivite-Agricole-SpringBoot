package school.hei.springagricole.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.springagricole.entity.CreateMember;
import school.hei.springagricole.entity.Member;
import school.hei.springagricole.service.MemberService;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<List<Member>> createMembers(@RequestBody List<CreateMember> createMembers) {
        List<Member> members = memberService.createMembers(createMembers);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(members);
    }
}
