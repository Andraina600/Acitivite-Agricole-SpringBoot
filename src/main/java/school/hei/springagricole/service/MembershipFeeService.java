package school.hei.springagricole.service;
import org.springframework.stereotype.Service;
import school.hei.springagricole.entity.MembershipFee;
import school.hei.springagricole.exception.BadRequestException;
import school.hei.springagricole.exception.NotFoundException;
import school.hei.springagricole.repository.CollectivityRepository;
import school.hei.springagricole.repository.MembershipFeeRepository;

import java.math.BigDecimal;
import java.util.List;

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

    public List<MembershipFee> create(String collectivityId, List<MembershipFee> fees) {
        collectivityRepository.findById(collectivityId)
                .orElseThrow(() -> new NotFoundException(
                        "Collectivité non trouvée : " + collectivityId));

        for (MembershipFee fee : fees) {
            if (fee.getAmount() == null || fee.getAmount().compareTo(BigDecimal.ZERO) < 0) {
                throw new BadRequestException(
                        "Le montant d'une cotisation ne peut pas être négatif");
            }
            if (fee.getFrequency() == null) {
                throw new BadRequestException("La fréquence est obligatoire");
            }
            if (fee.getEligibleFrom() == null) {
                throw new BadRequestException("La date eligible_from est obligatoire");
            }

            fee.setCollectivityId(collectivityId);
        }

        return membershipFeeRepository.saveAll(fees);
    }
}