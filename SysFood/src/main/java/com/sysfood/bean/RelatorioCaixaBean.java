package com.sysfood.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.hibernate.Session;

import com.sysfood.util.jsf.FacesUtil;
import com.sysfood.util.report.ExecutorRelatorio;

@Named
@RequestScoped
public class RelatorioCaixaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date dataInicio;
	private Date dataFim;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	public void emitir() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("data_inicio", this.dataInicio);
		parametros.put("data_fim", this.dataFim);
		parametros.put("REPORT_LOCALE", new Locale("pt","BR"));

		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/relatorio_informacoes_caixa.jasper",
				this.response, parametros, "Relatório Caixa - "+new SimpleDateFormat("dd/MM/yyyy").format(new Date())+".pdf");

		try (Session session = (Session) Persistence.createEntityManagerFactory("SysFoodPU").createEntityManager()) {
			session.doWork(executor);
		}

		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}
	}

	@NotNull
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	@NotNull
	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

}