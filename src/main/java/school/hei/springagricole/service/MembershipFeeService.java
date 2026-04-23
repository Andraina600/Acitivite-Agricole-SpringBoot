package school.hei.springagricole.service;

import org.springframework.stereotype.Service;
import school.hei.springagricole.entity.CreateMembershipFee;
import school.hei.springagricole.entity.MembershipFee;
import school.hei.springagricole.entity.enums.ActivityStatus;
import school.hei.springagricole.exception.BadRequestException;
import school.hei.springagricole.exception.NotFoundException;
import school.hei.springagricole.repository.CollectivityRepository;
import school.hei.springagricole.repository.MembershipFeeRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MembershipFeeService {
    private final MembershipFeeRepository membershipFeeRepository;
    private final CollectivityRepository collectivityRepository;

    public MembershipFeeService(MembershipFeeRepository membershipFeeRepository,
                                CollectivityRepository collectivityRepository) {
        this.membershipFeeRepository = membershipFeeRepository;
        this.collectivityRepository = collectivityRepository;
    }

    public List<MembershipFee> getByCollectivityId(String collectivityId) {
        collectivityRepository.findById(collectivityId)
                .orElseThrow(() -> new NotFoundException(
                        "Collectivité non trouvée : " + collectivityId));
        return membershipFeeRepository.findByCollectivityId(collectivityId);
    }

    public List<MembershipFee> create(String collectivityId,
                                      List<CreateMembershipFee> requests) {
        collectivityRepository.findById(collectivityId)
                .orElseThrow(() -> new NotFoundException(
                        "Collectivité non trouvée : " + collectivityId));

        List<MembershipFee> toSave = new ArrayList<>();
        for (CreateMembershipFee request : requests) {
            if (request.getAmount() == null
                    || request.getAmount().compareTo(BigDecimal.ZERO) < 0) {
                throw new BadRequestException(
                        "Le montant d'une cotisation ne peut pas être négatif");
            }
            if (request.getFrequency() == null) {
                throw new BadRequestException("La fréquence est obligatoire");
            }
            if (request.getEligibleFrom() == null) {
                throw new BadRequestException("La date eligible_from est obligatoire");
            }

            MembershipFee fee = new MembershipFee(
                    UUID.randomUUID().toString(),
                    collectivityId,
                    request.getLabel(),
                    request.getAmount(),
                    request.getFrequency(),
                    request.getEligibleFrom(),
                    ActivityStatus.ACTIVE
            );
            toSave.add(fee);
        }

        return membershipFeeRepository.saveAll(toSave);
    }
}