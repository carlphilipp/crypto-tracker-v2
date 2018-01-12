package org.cph.crypto.email;

public final class EmailProperties {
	private Server server;
	private Email email;

	public EmailProperties() {
	}

	public EmailProperties(Server server, Email email) {
		this.server = server;
		this.email = email;
	}

	public final Server getServer() {
		return this.server;
	}

	public final Email getEmail() {
		return this.email;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public static final class Server {
		private String host;
		private String port;

		public Server() {
		}

		public Server(String host, String port) {
			this.host = host;
			this.port = port;
		}

		public final String getHost() {
			return this.host;
		}

		public final void setHost(String var1) {
			this.host = var1;
		}

		public final String getPort() {
			return this.port;
		}

		public final void setPort(String var1) {
			this.port = var1;
		}
	}

	public static final class Email {
		private String username;
		private String password;
		private String from;

		public Email() {
		}

		public Email(String username, String password, String from) {
			this.username = username;
			this.password = password;
			this.from = from;
		}

		public final String getUsername() {
			return this.username;
		}

		public final void setUsername(String var1) {
			this.username = var1;
		}

		public final String getPassword() {
			return this.password;
		}

		public final void setPassword(String var1) {
			this.password = var1;
		}

		public final String getFrom() {
			return this.from;
		}

		public final void setFrom(String var1) {
			this.from = var1;
		}
	}
}
