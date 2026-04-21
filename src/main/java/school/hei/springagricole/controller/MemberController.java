package school.hei.springagricole.controller;


import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public List<Member> createMembers(@RequestBody List<CreateMember> createMembers) {
        return memberService.createMembers(createMembers);
    }
}
