package br.com.lemos.testesunitarios.matchers;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.com.lemos.testesunitarios.utils.DataUtils;

public class DataDiferencaDiasMatcher extends TypeSafeMatcher<Date> {

	private Integer diferencaDias;
	
	public DataDiferencaDiasMatcher(Integer diferencaDias) {
		this.diferencaDias = diferencaDias;
	}
	
	@Override
	public void describeTo(Description description) {
		Calendar data = Calendar.getInstance();
		data.add(Calendar.DAY_OF_WEEK, diferencaDias);
		String dataExtenso = data.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("pt", "BR"));
		description.appendText(dataExtenso);
	}

	@Override
	protected boolean matchesSafely(Date data) {
		return DataUtils.isMesmaData(data, DataUtils.obterDataComDiferencaDias(diferencaDias));
	}

}
