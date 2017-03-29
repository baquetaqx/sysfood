package com.sysfood.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import com.sysfood.util.jsf.FacesUtil;
import com.sysfood.util.report.ExecutarRelatorio;
import com.sysfood.util.report.ExecutorRelatorio;

@Named
@RequestScoped
public class RelatorioMensalBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date data;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	public void emitir() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("mes", calendar.get(Calendar.MONTH));
		parametros.put("ano", calendar.get(Calendar.YEAR));
		parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));

		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/relatorio_mensal.jasper", this.response,
				parametros, "Relatório Mensal - " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + ".pdf");

		ExecutarRelatorio.executar(executor);

		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}
	}

	@NotNull
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}