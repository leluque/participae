package br.com.participae.transparencia.config;

public interface SecurityService {

	public String findByEmail();

	public void autologin(String email, String password);

}
