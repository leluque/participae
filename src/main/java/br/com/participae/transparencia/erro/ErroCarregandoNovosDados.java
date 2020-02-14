package br.com.participae.transparencia.erro;

public class ErroCarregandoNovosDados extends Exception {

	private static final long serialVersionUID = 1L;

	public ErroCarregandoNovosDados() {
		super();
	}

	public ErroCarregandoNovosDados(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ErroCarregandoNovosDados(String message, Throwable cause) {
		super(message, cause);
	}

	public ErroCarregandoNovosDados(String message) {
		super(message);
	}

	public ErroCarregandoNovosDados(Throwable cause) {
		super(cause);
	}

}
