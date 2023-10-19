package com.jofre.web.exception;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class Testes {

	public static void main(String[] args) {
//		LocalDate data  = LocalDate.now();
		LocalDate data  = LocalDate.now().plusDays(4);

		
		
		System.out.println(data.getDayOfWeek().getValue());
		System.out.println(data.getDayOfWeek());
		
	}
}
