package com.sysfood.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.sysfood.dao.UsuarioDao;
import com.sysfood.model.Usuario;

@FacesConverter(forClass = Usuario.class)
public class UsuarioConverter implements Converter {

	@Inject
	private UsuarioDao usuarioDao;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Usuario retorno = new Usuario();

		if (StringUtils.isNotBlank(value)) {
			Long id = new Long(value);
			retorno = usuarioDao.porId(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Usuario usuario = (Usuario) value;
			return usuario.getId() == null ? null : usuario.getId().toString();
		}

		return null;
	}

}