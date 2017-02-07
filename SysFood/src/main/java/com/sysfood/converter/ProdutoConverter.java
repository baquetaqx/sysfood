package com.sysfood.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.sysfood.dao.ProdutoDao;
import com.sysfood.model.Produto;

@FacesConverter(forClass = Produto.class)
public class ProdutoConverter implements Converter {

	@Inject
	private ProdutoDao produtoDao;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Produto retorno = new Produto();

		if (StringUtils.isNotBlank(value)) {
			Long id = new Long(value);
			retorno = produtoDao.porId(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Produto produto = (Produto) value;
			return produto.getId() == null ? null : produto.getId().toString();
		}

		return null;
	}

}