package com.sysfood.bean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import com.sysfood.model.SecaoProduto;

@Named
@RequestScoped
public class GraficoPedidosBean {
	
	private LineChartModel model;

	public void preRender(){
		this.model = new LineChartModel();
		
		adicionarSerie(SecaoProduto.PASTEIS);
		adicionarSerie(SecaoProduto.MINI_PASTEIS);
		adicionarSerie(SecaoProduto.REFRIGERANTES);
		adicionarSerie(SecaoProduto.AGUAS);
		adicionarSerie(SecaoProduto.SUCOS);
	}
	
	private void adicionarSerie(SecaoProduto pasteis) {
		LineChartSeries series = new LineChartSeries();
		
		series.set("1", Math.random() * 100);
		series.set("2", Math.random() * 100);
		series.set("3", Math.random() * 100);
		series.set("4", Math.random() * 100);
		series.setLabel(pasteis.getDescricao());
		
		this.model.addSeries(series);
		this.model.setAnimate(true);
		this.model.setLegendPosition("e");
		this.model.setTitle("Grafico dos pedidos");
	}

	public LineChartModel getModel() {
		return model;
	}
	
	

}
