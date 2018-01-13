module cph.crypto.rest {
	exports org.cph.crypto.rest.controller;
	exports org.cph.crypto.rest.dto;

	requires cph.crypto.core;
	requires com.fasterxml.jackson.annotation;
	requires spring.core;
	requires spring.context;
	requires spring.web;
	requires spring.webmvc;
	requires spring.security.core;
	requires slf4j.api;
}
