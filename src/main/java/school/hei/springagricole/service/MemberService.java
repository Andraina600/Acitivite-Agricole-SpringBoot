package school.hei.springagricole.service;


import org.springframework.stereotype.Service;
import school.hei.springagricole.entity.CreateMember;
import school.hei.springagricole.entity.Member;
import school.hei.springagricole.entity.enums.MemberOccupation;
import school.hei.springagricole.exception.BadRequestException;
import school.hei.springagricole.exception.NotFoundException;
import school.hei.springagricole.repository.CollectivityRepository;
import school.hei.springagricole.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final CollectivityRepository collectivityRepository;

    public MemberService(MemberRepository memberRepository,
                         CollectivityRepository collectivityRepository) {
        this.memberRepository = memberRepository;
        this.collectivityRepository = collectivityRepository;
    }

    public List<Member> createMembers(List<CreateMember> createMembers) {
        List<Member> result = new ArrayList<>();

        for (CreateMember req : createMembers) {

            collectivityRepository.findById(req.getCollectivityIdentifier())
                    .orElseThrow(() -> new NotFoundException(
                            "Collectivity not found: " + req.getCollectivityIdentifier()));

            if (!req.isRegistrationFeePaid()) {
                throw new BadRequestException("Registration fee not paid.");
            }
            if (!req.isMembershipDuesPaid()) {
                throw new BadRequestException("Membership dues not paid.");
            }

            List<String> refereeIds = req.getReferees();
            if (refereeIds == null || refereeIds.size() < 2) {
                throw new BadRequestException(
                        "Member must have at least 2 confirmed (SENIOR) referees.");
            }

            List<Member> resolvedReferees = new ArrayList<>();
            int sameCollectivityCount = 0;
            int otherCollectivityCount = 0;

            for (String refereeId : refereeIds) {
                Member referee = memberRepository.findById(refereeId)
                        .orElseThrow(() -> new NotFoundException(
                                "Referee not found with ID: " + refereeId));

                if (referee.getOccupation() != MemberOccupation.SENIOR) {
                    throw new BadRequestException(
                            "Referee " + refereeId + " is not a confirmed (SENIOR) member.");
                }

                if (req.getCollectivityIdentifier().equals(referee.getCollectivityId())) {
                    sameCollectivityCount++;
                } else {
                    otherCollectivityCount++;
                }

                resolvedReferees.add(referee);
            }

            if (sameCollectivityCount < otherCollectivityCount) {
                throw new BadRequestException(
                        "Not enough referees from the target collectivity. " +
                                "Same collectivity referees (" + sameCollectivityCount + ") must be >= " +
                                "other collectivities referees (" + otherCollectivityCount + ").");
            }

            Member newMember = new Member();
            newMember.setFirstName(req.getFirstName());
            newMember.setLastName(req.getLastName());
            newMember.setBirthDate(req.getBirthDate());
            newMember.setGender(req.getGender());
            newMember.setAddress(req.getAddress());
            newMember.setProfession(req.getProfession());
            newMember.setPhoneNumber(req.getPhoneNumber());
            newMember.setEmail(req.getEmail());
            newMember.setOccupation(req.getOccupation());
            newMember.setCollectivityId(req.getCollectivityIdentifier());
            newMember.setReferees(resolvedReferees);

            result.add(memberRepository.save(newMember));
        }

        return result;
    }
}