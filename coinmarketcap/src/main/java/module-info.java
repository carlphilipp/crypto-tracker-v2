module cph.crypto.client {
	exports org.cph.crypto.client.coinmarketcap;

	requires cph.crypto.core;
	requires slf4j.api;
	requires spring.core;
	requires spring.web;
	requires com.fasterxml.jackson.annotation;
}
