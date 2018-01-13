module cph.crypto.application {
	requires cph.crypto.core;
	requires cph.crypto.uuid;
	requires cph.crypto.template;
	requires cph.crypto.email;
	requires cph.crypto.rest;
	requires cph.crypto.client;
	requires cph.crypto.persistence;

	requires spring.beans;
	requires spring.core;
	requires spring.context;
	requires spring.web;
	requires spring.webmvc;
	requires spring.boot;
	requires spring.boot.autoconfigure;
}
