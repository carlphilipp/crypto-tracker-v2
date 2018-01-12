package org.cph.crypto.template;

import org.cph.crypto.core.spi.TemplateService;

public class TemplateAdapter implements TemplateService {
	@Override
	public String welcomeContentEmail(final String baseUrl, final String userId, final String key) {
		return "Hello,<br/><br/>" +
			"Please validate your account by clicking <a href='" + baseUrl + "/validate?key=" + key + "&userId=" + userId + "'>here</a><br/><br/>" +
			"The crypto tracker team";
	}
}
