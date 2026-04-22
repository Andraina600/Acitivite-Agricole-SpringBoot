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
                        "Collectivity not found: " + collectivityId));
        return membershipFeeRepository.findByCollectivityId(collectivityId);
    }

    public List<MembershipFee> create(String collectivityId, List<MembershipFee> fees) {
        collectivityRepository.findById(collectivityId)
                .orElseThrow(() -> new NotFoundException(
                        "Collectivity not found: " + collectivityId));

        for (MembershipFee fee : fees) {
            if (fee.getAmount() == null || fee.getAmount().compareTo(BigDecimal.ZERO) < 0) {
                throw new BadRequestException(
                        "Membership fee amount cannot be negative");
            }
            if (fee.getFrequency() == null) {
                throw new BadRequestException("Frequency is mandatory");
            }
            if (fee.getEligibleFrom() == null) {
                throw new BadRequestException("The date eligible_from is mandatory");
            }

            fee.setCollectivityId(collectivityId);
        }

        return membershipFeeRepository.saveAll(fees);
    }
}