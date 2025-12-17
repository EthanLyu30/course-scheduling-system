package com.schedule.teacher.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DomainEventPublisher {

    private final ApplicationEventPublisher publisher;

    public void publishPreferenceChanged(PreferenceChangedEvent event) {
        log.info("[MQ] preference.changed teacherId={} courseId={} slotId={} version={}",
                event.getEntityId(), event.getCourseId(), event.getTimeSlotId(), event.getVersion());
        publisher.publishEvent(event);
    }

    public void publishScheduleConfirmed(ScheduleConfirmedEvent event) {
        log.info("[MQ] schedule.confirmed planId={} teacherId={}", event.getPlanId(), event.getTeacherId());
        publisher.publishEvent(event);
    }
}
