package com.jofre;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Teste {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
        List<Integer>sem = Arrays.asList(1,2,4);
        List<String>ds = Arrays.asList("01/03/2024","19/09/2023","25/02/2024","03/03/2024","31/12/2023","28/12/2023","29/02/2024","30/01/2024","26/11/2024");
        
		for(String dis: disponiveis(sem,ds)) {
			System.out.println(dis);
		}
//		diasOcupados();

	}
//	métodos do milhão que informa os dias disponíveis 
	public static List<String> disponiveis(List<Integer>dias, List<String>agendamentos) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		List<String> diasReservados = new ArrayList<>();
		List<String> diasDisponiveis = new ArrayList<>();
		
		diasReservados.addAll(agendamentos);
		
		 for(int i =0;i< dias.size();i++){
			 diasReservados.addAll(diasOcupados(dias.get(i)));
	        }
		
		cal2.add(Calendar.YEAR, 1);

		for (Calendar cal = cal1; cal.compareTo(cal2) <= 0; cal.add(Calendar.DATE, 1)) {
			diasDisponiveis.add(df.format(cal.getTime()));
			
		}
		for(String data : diasReservados ) {
			diasDisponiveis.remove(data);
		}
		return diasDisponiveis;
		
//		
//		for(String diasd: diasDisponiveis) {
//			System.out.println(diasd);
//		}
		
	}

	private static  List<String> diasOcupados(int dia) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		List<String>dias = new ArrayList<>();
		int diaSemana = dia; // TODO quarta-feira, por exemplo
		
		Calendar calendar = Calendar.getInstance();	
		
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		
		//compara o dia da semana buscado comparando com o dia atual
		if(diaSemana != calendar.get(Calendar.DAY_OF_WEEK)) {
			
			//se o dia da semana for diferente do dia atual ele busca a proxima data do dia da semana buscado			
			while(calendar.get(Calendar.DAY_OF_WEEK) != diaSemana) {
				calendar.add(Calendar.DAY_OF_MONTH, 1);
			}
			
			//data inicial achada, só fazer os cálculos
			cal1.setTime(calendar.getTime());
		}
		cal2.add(Calendar.YEAR, 1);
		
		for (Calendar cal = cal1; cal.compareTo(cal2) <= 0; cal.add(Calendar.DATE, 7)) {
//			System.out.println(df.format(cal.getTime()));
			dias.add(df.format(cal.getTime()));
		}
		return dias;
	}


}
