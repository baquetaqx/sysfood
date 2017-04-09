package com.sysfood.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import com.sysfood.bo.GraficoPedidosBo;
import com.sysfood.model.SecaoProduto;

@Named
@RequestScoped
public class GraficoPedidosBean {

	private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM");

	private LineChartModel model;

	@Inject
	GraficoPedidosBo graficoPedidosBo;

	public void preRender() {
		this.model = new LineChartModel();
		this.model.setTitle("Grafico dos pedidos");
		this.model.setLegendPosition("e");
		this.model.setAnimate(true);

		this.model.getAxes().put(AxisType.X, new CategoryAxis());

		adicionarSerie(SecaoProduto.PASTEIS);
		adicionarSerie(SecaoProduto.MINI_PASTEIS);
		adicionarSerie(SecaoProduto.REFRIGERANTES);
		adicionarSerie(SecaoProduto.AGUAS);
		adicionarSerie(SecaoProduto.SUCOS);
	}

	private void adicionarSerie(SecaoProduto secao) {
		Map<Date, Integer> quantidadeDeProdutosPorData = graficoPedidosBo.quantidadeDeProdutosPorData(15, secao);

		LineChartSeries series = new LineChartSeries();

		for (Date data : quantidadeDeProdutosPorData.keySet()) {
			series.set(DATE_FORMAT.format(data), quantidadeDeProdutosPorData.get(data));
		}

		series.setLabel(secao.getDescricao());

		this.model.addSeries(series);

	}

	public LineChartModel getModel() {
		return model;
	}

}
