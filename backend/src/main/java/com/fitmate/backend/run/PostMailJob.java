package com.fitmate.backend.run;

import com.fitmate.backend.entity.Reservation;
import com.fitmate.backend.service.MailService;
import com.fitmate.backend.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PostMailJob extends QuartzJobBean {
    private static final Logger log = LoggerFactory.getLogger(PostMailJob.class);
    private final ReservationService reservationService;
    private final MailService mailService;
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LocalDateTime now = LocalDateTime.now();
        log.info("예약 메일 발송!" + now);
        List<Reservation> reservations = reservationService.findAllAtCurrentTime(now);
        for(Reservation reservation : reservations){
            mailService.sendmailByReservation(reservation);
            reservationService.completeReservation(reservation.getId());
        }
    }
}
