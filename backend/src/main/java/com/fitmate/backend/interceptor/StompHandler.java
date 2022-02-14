package com.fitmate.backend.interceptor;

import com.fitmate.backend.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {
    private final TokenProvider tokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();
        System.out.println("preSend!!");
        System.out.println(accessor.getCommand());
        if (command.compareTo(StompCommand.SUBSCRIBE) == 0) {
            String destination = accessor.getDestination();
            System.out.println("구독 주소 : " + destination);
            System.out.println(message);
        } else if (command.compareTo(StompCommand.CONNECT) == 0) {
            System.out.println("사용자 연결");
        } else if (command.compareTo(StompCommand.DISCONNECT) == 0) {
            System.out.println("사용자 연결 해제");
        }
        return message;
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//        System.out.println("message:" + message);
//        System.out.println("헤더 : " + message.getHeaders());
//        System.out.println("토큰" + accessor.getNativeHeader("Authorization"));
//        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//            System.out.println("사용자 연결!");
//            tokenProvider.validateToken(Objects.requireNonNull(accessor.getFirstNativeHeader("Authorization")).substring(7));
//        }else if(StompCommand.DISCONNECT.equals(accessor.getCommand())) {
//            System.out.println("연결 해제!");
//        }else if(StompCommand.SUBSCRIBE.equals(accessor.getCommand())){
//            String destination = accessor.getDestination();
//            System.out.println("구독 주소: "+ destination);
//            System.out.println(message);
//        }
//        return message;
    }
}