package br.com.participae.transparencia.erro;

public class ErroCarregamentoDadosExistentes extends Exception {

	private static final long serialVersionUID = 1L;

	public ErroCarregamentoDadosExistentes() {
		super();
	}

	public ErroCarregamentoDadosExistentes(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ErroCarregamentoDadosExistentes(String message, Throwable cause) {
		super(message, cause);
	}

	public ErroCarregamentoDadosExistentes(String message) {
		super(message);
	}

	public ErroCarregamentoDadosExistentes(Throwable cause) {
		super(cause);
	}

}
