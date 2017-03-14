package com.sysfood.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.sysfood.dao.AdicionalDao;
import com.sysfood.model.Adicional;

@FacesConverter(forClass = Adicional.class)
public class AdicionalConverter implements Converter {

	@Inject
	private AdicionalDao adicionalDao;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Adicional retorno = new Adicional();

		if (StringUtils.isNotBlank(value)) {
			Long id = new Long(value);
			retorno = adicionalDao.porId(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Adicional adicional = (Adicional) value;
			return adicional.getId() == null ? null : adicional.getId().toString();
		}

		return null;
	}

}