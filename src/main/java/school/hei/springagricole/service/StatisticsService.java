package school.hei.springagricole.service;


import org.springframework.stereotype.Service;
import school.hei.springagricole.dto.CollectivityNewMembers;
import school.hei.springagricole.dto.MemberAssiduity;
import school.hei.springagricole.dto.MemberEarnedAmount;
import school.hei.springagricole.dto.MemberUnpaidAmount;
import school.hei.springagricole.entity.*;
import school.hei.springagricole.exception.BadRequestException;
import school.hei.springagricole.exception.NotFoundException;
import school.hei.springagricole.repository.CollectivityRepository;
import school.hei.springagricole.repository.MemberRepository;
import school.hei.springagricole.repository.StatisticsRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

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

        List<MemberEarnedAmount> earnedList =
                statisticsRepository.findEarnedAmountByMember(collectivityId, from, to);
        List<MemberUnpaidAmount> unpaidList =
                statisticsRepository.findUnpaidAmountByMember(collectivityId, from, to);
        List<MemberAssiduity> assiduityList =
                statisticsRepository.findAssiduityByMember(collectivityId, from, to);
        List<String> memberIds =
                statisticsRepository.findMemberIdsByCollectivity(collectivityId);

        Map<String, BigDecimal> earnedIndex = new HashMap<>();
        for (MemberEarnedAmount e : earnedList) {
            earnedIndex.put(e.getMemberId(), e.getEarnedAmount());
        }

        Map<String, BigDecimal> unpaidIndex = new HashMap<>();
        for (MemberUnpaidAmount u : unpaidList) {
            unpaidIndex.put(u.getMemberId(), u.getUnpaidAmount());
        }

        Map<String, Double> assiduityIndex = new HashMap<>();
        for (MemberAssiduity a : assiduityList) {
            assiduityIndex.put(a.getMemberId(), a.getAssiduityPercentage());
        }

        List<CollectivityLocalStatistics> result = new ArrayList<>();
        for (String memberId : memberIds) {
            Member member = memberRepository.findById(memberId).orElse(null);
            if (member == null) continue;

            MemberDescription desc = new MemberDescription(
                    member.getId(),
                    member.getFirstName(),
                    member.getLastName(),
                    member.getEmail(),
                    member.getOccupation() != null
                            ? member.getOccupation().name() : null
            );

            result.add(new CollectivityLocalStatistics(
                    desc,
                    earnedIndex.getOrDefault(memberId, BigDecimal.ZERO),
                    unpaidIndex.getOrDefault(memberId, BigDecimal.ZERO),
                    assiduityIndex.getOrDefault(memberId, 100.0)
            ));
        }
        return result;
    }

    public List<CollectivityOverallStatistics> getOverallStatistics(
            LocalDate from, LocalDate to) {

        validatePeriod(from, to);

        List<String> collectivityIds = statisticsRepository.findAllCollectivityIds();

        List<CollectivityNewMembers> newMembersList =
                statisticsRepository.findNewMembersCountByCollectivity(from, to);
        Map<String, Integer> newMembersIndex = new HashMap<>();
        for (CollectivityNewMembers c : newMembersList) {
            newMembersIndex.put(c.getCollectivityId(), c.getNewMembersCount());
        }

        List<CollectivityOverallStatistics> result = new ArrayList<>();
        for (String collectivityId : collectivityIds) {
            Collectivity collectivity =
                    collectivityRepository.findById(collectivityId).orElse(null);
            if (collectivity == null) continue;

            CollectivityInformation info = new CollectivityInformation(
                    collectivity.getName(),
                    collectivity.getNumber()
            );

            int newMembers = newMembersIndex.getOrDefault(collectivityId, 0);

            double duePercentage =
                    statisticsRepository.computeCurrentDuePercentage(
                            collectivityId, from, to);

            double assiduityPercentage = statisticsRepository
                    .computeCollectivityAssiduityPercentage(collectivityId, from, to);

            result.add(new CollectivityOverallStatistics(
                    info, newMembers, duePercentage, assiduityPercentage));
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