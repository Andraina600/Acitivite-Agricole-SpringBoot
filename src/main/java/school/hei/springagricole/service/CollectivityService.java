package school.hei.springagricole.service;


import org.springframework.stereotype.Service;
import school.hei.springagricole.entity.Collectivity;
import school.hei.springagricole.entity.CollectivityStructure;
import school.hei.springagricole.entity.CreateCollectivity;
import school.hei.springagricole.entity.Member;
import school.hei.springagricole.exception.BadRequestException;
import school.hei.springagricole.exception.NotFoundException;
import school.hei.springagricole.repository.CollectivityRepository;
import school.hei.springagricole.repository.MemberRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CollectivityService {

    private final MemberRepository memberRepository;
    private final CollectivityRepository collectivityRepository;

    public CollectivityService(MemberRepository memberRepository,
                               CollectivityRepository collectivityRepository) {
        this.memberRepository = memberRepository;
        this.collectivityRepository = collectivityRepository;
    }

    public List<Collectivity> createCollectivities(List<CreateCollectivity> requests) {
        List<Collectivity> result = new ArrayList<>();

        for (CreateCollectivity req : requests) {

            if (!req.isFederationApproval()) {
                throw new BadRequestException(
                        "Collectivity cannot be created without federation approval.");
            }

            if (req.getStructure() == null
                    || req.getStructure().getPresident() == null
                    || req.getStructure().getVicePresident() == null
                    || req.getStructure().getTreasurer() == null
                    || req.getStructure().getSecretary() == null) {
                throw new BadRequestException(
                        "Collectivity structure is missing or incomplete " +
                                "(President, VP, Treasurer, Secretary are all required).");
            }

            if (req.getMembers() == null || req.getMembers().size() < 10) {
                throw new BadRequestException(
                        "A new collectivity must have at least 10 registered members.");
            }

            LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);
            List<Member> collectivityMembers = new ArrayList<>();
            int seniorEnoughCount = 0;

            for (String memberId : req.getMembers()) {
                Member member = memberRepository.findById(memberId)
                        .orElseThrow(() -> new NotFoundException(
                                "Member not found with ID: " + memberId));

                if (member.getAdmissionDate() != null
                        && member.getAdmissionDate().isBefore(sixMonthsAgo)) {
                    seniorEnoughCount++;
                }
                collectivityMembers.add(member);
            }

            if (seniorEnoughCount < 5) {
                throw new BadRequestException(
                        "A new collectivity must have at least 5 members " +
                                "with more than 6 months of seniority in the federation.");
            }

            CollectivityStructure structure = new CollectivityStructure();
            structure.setPresident(resolveMember(req.getStructure().getPresident()));
            structure.setVicePresident(resolveMember(req.getStructure().getVicePresident()));
            structure.setTreasurer(resolveMember(req.getStructure().getTreasurer()));
            structure.setSecretary(resolveMember(req.getStructure().getSecretary()));

            Collectivity collectivity = new Collectivity();
            collectivity.setId(UUID.randomUUID().toString());
            collectivity.setLocation(req.getLocation());
            collectivity.setStructure(structure);
            collectivity.setMembers(collectivityMembers);

            result.add(collectivityRepository.save(collectivity));
        }

        return result;
    }

    private Member resolveMember(String memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(
                        "Member in structure not found with ID: " + memberId));
    }
}