package com.fitmate.backend.service;

import com.fitmate.backend.dto.MailDto;
import com.fitmate.backend.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;

    public MailDto sendMail(MailDto mailDto){
        System.out.println("sendMail!");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());
        mailSender.send(message);
        System.out.println(message.getTo());
        System.out.println(message.getSubject());
        System.out.println(message.getText());
        return mailDto;
    }
    public void sendmailByReservation(Reservation reservation){
        System.out.println("sendMail!");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(reservation.getMember().getEmail());
        message.setSubject("화상통화 링크 발송");
        message.setText("https://edu.ssafy.com/");
        mailSender.send(message);
    }
}
