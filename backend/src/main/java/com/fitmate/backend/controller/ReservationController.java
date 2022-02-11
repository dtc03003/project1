package com.fitmate.backend.controller;

import com.fitmate.backend.dto.ReservationDto;
import com.fitmate.backend.entity.Reservation;
import com.fitmate.backend.entity.State;
import com.fitmate.backend.service.ReservationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="ReservationController" , description = "예약 관리")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/portfolio/{nickname}/reservation")
    public ResponseEntity<Reservation> makeReservation(@PathVariable String nickname, @RequestBody ReservationDto reservationDto){
        return ResponseEntity.ok(reservationService.makeReservation(nickname, reservationDto));
    }
    @GetMapping("/portfolio/{nickname}/reservations")
    public ResponseEntity<?> findAllReservationsByNickname(@PathVariable String nickname, @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size= 100) Pageable pageable, @RequestParam(required = false) String state){
        return ResponseEntity.ok(reservationService.findAllReservationsByNicknameInState(nickname,pageable,state));
    }
    @GetMapping("/portfolio/{nickname}/reservations/schedule")
    public ResponseEntity<?> findAllTimeByNickname(@PathVariable String nickname){
        return ResponseEntity.ok(reservationService.findAllTimeByNickname(nickname));
    }
    @DeleteMapping("/reservation/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id){
        return ResponseEntity.ok(reservationService.deleteReservation(id));
    }
    @PutMapping("/reservation/cancel/{id}")
    public ResponseEntity<?> cancelReservation(@PathVariable Long id){
        return ResponseEntity.ok(reservationService.cancelReservation(id));
    }
    @PutMapping("/reservation/complete/{id}")
    public ResponseEntity<?> completeReservation(@PathVariable Long id){
        return ResponseEntity.ok(reservationService.completeReservation(id));
    }
}
