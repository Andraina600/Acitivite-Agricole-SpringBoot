package school.hei.springagricole.service;

import org.springframework.stereotype.Service;
import school.hei.springagricole.entity.*;
import school.hei.springagricole.exception.BadRequestException;
import school.hei.springagricole.exception.NotFoundException;
import school.hei.springagricole.repository.ActivityAttendanceRepository;
import school.hei.springagricole.repository.CollectivityActivityRepository;
import school.hei.springagricole.repository.CollectivityRepository;
import school.hei.springagricole.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CollectivityActivityService {
    private final CollectivityActivityRepository activityRepository;
    private final ActivityAttendanceRepository attendanceRepository;
    private final CollectivityRepository collectivityRepository;
    private final MemberRepository memberRepository;

    public CollectivityActivityService(CollectivityActivityRepository activityRepository,
                                       ActivityAttendanceRepository attendanceRepository,
                                       CollectivityRepository collectivityRepository,
                                       MemberRepository memberRepository) {
        this.activityRepository = activityRepository;
        this.attendanceRepository = attendanceRepository;
        this.collectivityRepository = collectivityRepository;
        this.memberRepository = memberRepository;
    }

    public List<CollectivityActivity> getActivities(String collectivityId) {
        collectivityRepository.findById(collectivityId)
                .orElseThrow(() -> new NotFoundException(
                        "Collectivity not found: " + collectivityId));
        return activityRepository.findByCollectivityId(collectivityId);
    }

    public List<CollectivityActivity> createActivities(String collectivityId,
                                                       List<CreateCollectivityActivity> requests) {
        collectivityRepository.findById(collectivityId)
                .orElseThrow(() -> new NotFoundException(
                        "Collectivity not found: " + collectivityId));

        List<CollectivityActivity> toSave = new ArrayList<>();
        for (CreateCollectivityActivity request : requests) {
            validateActivityRequest(request);

            CollectivityActivity activity = new CollectivityActivity(
                    UUID.randomUUID().toString(),
                    collectivityId,
                    request.getLabel(),
                    request.getActivityType(),
                    request.getMemberOccupationConcerned(),
                    request.getRecurrenceRule(),
                    request.getExecutiveDate()
            );
            toSave.add(activity);
        }

        return activityRepository.saveAll(toSave);
    }

    public List<ActivityMemberAttendance> saveAttendance(
            String collectivityId, String activityId,
            List<CreateActivityMemberAttendance> requests) {

        collectivityRepository.findById(collectivityId)
                .orElseThrow(() -> new NotFoundException(
                        "Collectivity not found: " + collectivityId));

        activityRepository.findById(activityId)
                .orElseThrow(() -> new NotFoundException(
                        "Activity not found: " + activityId));

        for (CreateActivityMemberAttendance request : requests) {
            memberRepository.findById(request.getMemberIdentifier())
                    .orElseThrow(() -> new NotFoundException(
                            "Member not found: " + request.getMemberIdentifier()));

            if (attendanceRepository.isAlreadyConfirmed(
                    activityId, request.getMemberIdentifier())) {
                throw new BadRequestException(
                        "Attendance already confirmed for member: "
                                + request.getMemberIdentifier()
                                + " — cannot be modified once set to ATTENDED or MISSING");
            }
        }

        List<ActivityMemberAttendance> result = new ArrayList<>();
        for (CreateActivityMemberAttendance request : requests) {
            ActivityMemberAttendance attendance = attendanceRepository.upsert(
                    activityId,
                    request.getMemberIdentifier(),
                    request.getAttendanceStatus()
            );
            result.add(attendance);
        }
        return result;
    }

    public List<ActivityMemberAttendance> getAttendance(
            String collectivityId, String activityId) {

        collectivityRepository.findById(collectivityId)
                .orElseThrow(() -> new NotFoundException(
                        "Collectivity not found: " + collectivityId));

        activityRepository.findById(activityId)
                .orElseThrow(() -> new NotFoundException(
                        "Activity not found: " + activityId));

        return attendanceRepository.findByActivityId(activityId);
    }

    private void validateActivityRequest(CreateCollectivityActivity request) {
        boolean hasRecurrence = request.getRecurrenceRule() != null;
        boolean hasDate = request.getExecutiveDate() != null;

        if (hasRecurrence && hasDate) {
            throw new BadRequestException(
                    "recurrenceRule and executiveDate cannot both be provided");
        }
        if (!hasRecurrence && !hasDate) {
            throw new BadRequestException(
                    "Either recurrenceRule or executiveDate must be provided");
        }
        if (request.getActivityType() == null) {
            throw new BadRequestException("activityType is mandatory");
        }
        if (request.getLabel() == null || request.getLabel().isBlank()) {
            throw new BadRequestException("label is mandatory");
        }
        if (hasRecurrence) {
            MonthlyRecurrenceRule rule = request.getRecurrenceRule();
            if (rule.getWeekOrdinal() == null
                    || rule.getWeekOrdinal() < 1 || rule.getWeekOrdinal() > 5) {
                throw new BadRequestException("weekOrdinal must be between 1 and 5");
            }
            if (rule.getDayOfWeek() == null) {
                throw new BadRequestException("dayOfWeek is mandatory in recurrenceRule");
            }
        }
    }
}
