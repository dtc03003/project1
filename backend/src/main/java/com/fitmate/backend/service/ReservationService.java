//package com.fitmate.backend.service;
//
//import com.fitmate.backend.dto.getDateDto;
//import net.minidev.json.JSONObject;
//
//import java.security.Timestamp;
//import java.time.DayOfWeek;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.YearMonth;
//
//public class ReservationService {
//    public JSONObject getDateBySeat(getDateDto getDateDto){
//        int month = getDateDto.getMonth();
//        LocalDate selectDate = LocalDate.of(getDateDto.getYear(), month, 1);
//        YearMonth yearMonth = YearMonth.from(selectDate);
//        int lastDay = yearMonth.lengthOfMonth();
//        int start = 0;
//        DayOfWeek dayOfWeek = selectDate.getDayOfWeek();
//        int temp = 1;
//        start=dayOfWeek.getValue();
//        int endDayIdOfMonth= lastDay+start;
//        JSONObject dates=new JSONObject();
//        int [][]dateAndValue=new int[endDayIdOfMonth][3];
//        for (int i = 0; i < start; i++) {
//            dateAndValue[i][0]=0;
//            dateAndValue[i][1]=0;
//            dateAndValue[i][2]=cantFlag;
//        }
//        for(int i=start;i<endDayIdOfMonth;i++) {
//            Timestamp timestamp=Timestamp.valueOf(getDateDto.getYear()+"-"+month+"-"+temp+" 00:00:00");
//            int countAlready=getCountAlreadyInDate(timestamp,getDateDto.getSeat());
//            dateAndValue[i][0]=temp;
//            dateAndValue[i][1]=countAlready;
//            if(countAlready>=maxPeopleOfDay||utillService.compareDate(timestamp, LocalDateTime.now())){
//                dateAndValue[i][2]=cantFlag;
//            }
//            temp+=1;
//
//        }
//        return null;
//    }
//}
