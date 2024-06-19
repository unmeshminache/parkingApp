package com.infy.parking.assignment.jpa;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Test {
    public static void main(String[] args){
        LocalDateTime entry = LocalDateTime.of(2024, 06, 13, 16, 0, 0);
        LocalDateTime exit = LocalDateTime.of(2024, 06, 14, 19, 0, 0);


        Long totalMinutes= Duration.between(exit, entry).toMinutes();
        Long totalDays=Duration.between(exit,entry).toDays();
        System.out.println(entry.isBefore(exit));
        LocalDate entrydate=LocalDate.of(2024,06,13);
        LocalDate exitdate=LocalDate.of(2024,06,15);
        System.out.println(entrydate.isBefore(exitdate));
    }
}
