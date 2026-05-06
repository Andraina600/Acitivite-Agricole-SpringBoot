package school.hei.springagricole.service;

import org.springframework.stereotype.Service;
import school.hei.springagricole.entity.*;
import school.hei.springagricole.exception.BadRequestException;
import school.hei.springagricole.exception.NotFoundException;
import school.hei.springagricole.repository.CollectivityRepository;
import school.hei.springagricole.repository.MemberRepository;
import school.hei.springagricole.repository.StatisticsRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;
    private final CollectivityRepository collectivityRepository;
    private final MemberRepository memberRepository;

    public StatisticsService(StatisticsRepository statisticsRepository,
                             CollectivityRepository collectivityRepository,
                             MemberRepository memberRepository) {
        this.statisticsRepository = statisticsRepository;
        this.collectivityRepository = collectivityRepository;
        this.memberRepository = memberRepository;
    }

    public List<CollectivityLocalStatistics> getLocalStatistics(
            String collectivityId, LocalDate from, LocalDate to) {

        validatePeriod(from, to);

        collectivityRepository.findById(collectivityId)
                .orElseThrow(() -> new NotFoundException(
                        "Collectivity not found: " + collectivityId));

        Map<String, BigDecimal> earnedByMember =
                statisticsRepository.findEarnedAmountByMember(collectivityId, from, to);

        Map<String, BigDecimal> unpaidByMember =
                statisticsRepository.findUnpaidAmountByMember(collectivityId, from, to);

        List<String> memberIds = statisticsRepository.findMemberIdsByCollectivity(collectivityId);

        List<CollectivityLocalStatistics> result = new ArrayList<>();
        for (String memberId : memberIds) {
            Member member = memberRepository.findById(memberId).orElse(null);
            if (member == null) continue;

            MemberDescription desc = new MemberDescription(
                    member.getId(),
                    member.getFirstName(),
                    member.getLastName(),
                    member.getEmail(),
                    member.getOccupation() != null ? member.getOccupation().name() : null
            );

            BigDecimal earned = earnedByMember.getOrDefault(memberId, BigDecimal.ZERO);
            BigDecimal unpaid = unpaidByMember.getOrDefault(memberId, BigDecimal.ZERO);

            result.add(new CollectivityLocalStatistics(desc, earned, unpaid));
        }

        return result;
    }

    public List<CollectivityOverallStatistics> getOverallStatistics(
            LocalDate from, LocalDate to) {

        validatePeriod(from, to);

        List<String> collectivityIds = statisticsRepository.findAllCollectivityIds();
        Map<String, Integer> newMembersMap =
                statisticsRepository.findNewMembersCountByCollectivity(from, to);

        List<CollectivityOverallStatistics> result = new ArrayList<>();

        for (String collectivityId : collectivityIds) {
            Collectivity collectivity = collectivityRepository.findById(collectivityId)
                    .orElse(null);
            if (collectivity == null) continue;

            CollectivityInformation info = new CollectivityInformation(
                    collectivity.getName(),
                    collectivity.getNumber()
            );

            int newMembers = newMembersMap.getOrDefault(collectivityId, 0);

            double duePercentage = statisticsRepository
                    .computeCurrentDuePercentage(collectivityId, from, to);

            result.add(new CollectivityOverallStatistics(info, newMembers, duePercentage));
        }

        return result;
    }

    private void validatePeriod(LocalDate from, LocalDate to) {
        if (from == null || to == null) {
            throw new BadRequestException("Parameters 'from' and 'to' are mandatory.");
        }
        if (from.isAfter(to)) {
            throw new BadRequestException("'from' date cannot be after 'to' date.");
        }
    }
}