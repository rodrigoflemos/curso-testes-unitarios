package br.com.lemos.testesunitarios.matchers;

import java.util.Calendar;

public class MyMatchers {

	public static DiaSemanaMatcher caiEm(Integer diaSemana) {
		return new DiaSemanaMatcher(diaSemana);
	}
	
	public static DiaSemanaMatcher caiNumaSegunda() {
		return new DiaSemanaMatcher(Calendar.MONDAY);
	}
	
	public static DataDiferencaDiasMatcher ehHojeComDiferencaDias(Integer diferencaDias) {
		return new DataDiferencaDiasMatcher(diferencaDias);
	}
	
	public static DataDiferencaDiasMatcher ehHoje() {
		return new DataDiferencaDiasMatcher(0);
	}
}
