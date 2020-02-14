package br.com.participae.transparencia.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class MyUserDetails extends User {

	private static final long serialVersionUID = 1L;

	private String nomeCompleto;
	private String membroDesde;
	private Object papel;

	public MyUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
			String fullName, String memberSince, Object userRole) {
		super(username, password, authorities);
		this.nomeCompleto = fullName;
		this.membroDesde = memberSince;
		this.papel = userRole;
	}

	public MyUserDetails(String fullName, String memberSince, Object userRole, String username, String password,
			boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.nomeCompleto = fullName;
		this.membroDesde = memberSince;
		this.papel = userRole;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String fullName) {
		this.nomeCompleto = fullName;
	}

	public String getMembroDesde() {
		return membroDesde;
	}

	public void setMembroDesde(String memberSince) {
		this.membroDesde = memberSince;
	}

	public Object getPapel() {
		return papel;
	}

	public void setPapel(Object userRole) {
		this.papel = userRole;
	}

}
