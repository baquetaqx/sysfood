package com.sysfood.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.sysfood.dao.AdicionaisDao;
import com.sysfood.model.Adicionais;

@FacesConverter(forClass = Adicionais.class)
public class AdicionalConverter implements Converter {

	@Inject
	private AdicionaisDao adicionaisDao;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Adicionais retorno = new Adicionais();

		if (StringUtils.isNotBlank(value)) {
			Long id = new Long(value);
			retorno = adicionaisDao.porId(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Adicionais adicionais = (Adicionais) value;
			return adicionais.getId() == null ? null : adicionais.getId().toString();
		}

		return null;
	}

}