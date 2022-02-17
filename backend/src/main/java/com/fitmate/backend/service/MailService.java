package com.fitmate.backend.service;

import com.fitmate.backend.dto.MailDto;
import com.fitmate.backend.entity.ChatRoom;
import com.fitmate.backend.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    private final ChatRoomService chatRoomService;
    private final MessageService messageService;
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
        ChatRoom chatRoom = chatRoomService.findByHostNickname(reservation.getPortfolio().getNickname());
        messageService.deleteMessagesInRoom(chatRoom);                                          // 메시지 초기화
        chatRoom = chatRoomService.updateAccessCode(chatRoom.getId());                          // 액세스코드 초기화
        System.out.println("sendMail!");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(reservation.getMember().getEmail());
        message.setSubject("화상통화 링크 및 입장코드 발송");
        StringBuilder sb = new StringBuilder();
        sb.append("http://i6D105.p.ssafy.io/room/"+reservation.getPortfolio().getNickname()+"\n");
        sb.append("Access code : "+ chatRoom.getAccessCode());
        System.out.println(sb);
        message.setText(sb.toString());
        mailSender.send(message);
    }
}
