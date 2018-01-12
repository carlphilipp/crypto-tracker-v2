module cph.crypto.email {
	exports org.cph.crypto.email;

	requires cph.crypto.core;
	requires slf4j.api;
	requires jasypt;
	requires mail;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.dataformat.yaml;
	requires java.management;
}
