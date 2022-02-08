package com.fitmate.backend.controller;

import com.fitmate.backend.dto.ReservationDto;
import com.fitmate.backend.entity.Reservation;
import com.fitmate.backend.service.ReservationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="ReservationController" , description = "예약 관리")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/portfolio")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/{nickname}/reservation")
    public ResponseEntity<Reservation> makeReservation(@PathVariable String nickname, @RequestBody ReservationDto reservationDto){
        return ResponseEntity.ok(reservationService.makeReservation(nickname, reservationDto));
    }
    @GetMapping("/{nickname}/reservations/all")
    public ResponseEntity<?> findAllReservationsByNickname(@PathVariable String nickname){
        return ResponseEntity.ok(reservationService.findAllReservationsByNickname(nickname));
    }
    @GetMapping("/{nickname}/reservations/timeList")
    public ResponseEntity<?> findAllTimeByNickname(@PathVariable String nickname){
        return ResponseEntity.ok(reservationService.findAllTimeByNickname(nickname));
    }
    @GetMapping("/{nickname}/reservations")
    public ResponseEntity<?> findAllReservationByNicknameInState(@PathVariable String nickname , @RequestParam String state){
        return ResponseEntity.ok(reservationService.findAllReservationByNicknameInState(nickname,state));
    }
    @DeleteMapping("/{nickname}/reservation/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable String nickname, @PathVariable Long id){
        return ResponseEntity.ok(reservationService.deleteReservation(nickname,id));
    }
    @PutMapping("/{nickname}/reservation/cancel/{id}")
    public ResponseEntity<?> cancelReservation(@PathVariable String nickname, @PathVariable Long id){
        return ResponseEntity.ok(reservationService.cancelReservation(nickname, id));
    }
    @PutMapping("/{nickname}/reservation/complete/{id}")
    public ResponseEntity<?> completeReservation(@PathVariable String nickname, @PathVariable Long id){
        return ResponseEntity.ok(reservationService.completeReservation(id));
    }
}
