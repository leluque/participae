package br.com.participae.transparencia.repositorio;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.com.participae.transparencia.dominio.Servidor;
import br.com.participae.transparencia.to.PesquisaTO;

public class EspecificacaoPesquisa implements Specification<Servidor> {
	
	private PesquisaTO pesquisaTO;
	
    public EspecificacaoPesquisa(PesquisaTO pesquisaTO) {
		super();
		this.pesquisaTO = pesquisaTO;
	}

	@Override
    public Predicate toPredicate(Root<Servidor> servidor, CriteriaQuery<?> query, CriteriaBuilder builder) {
		return builder.like(builder.lower(servidor.get("nome")), "%" + pesquisaTO.getTexto() + "%");

		/*        Join<AdminPerson, Contact> adminPerson = root.join("adminPerson");
        Pattern pattern = Pattern.compile("[0-9]{2}\\/[0-9]{2}\\/[0-9]{2,4}");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        if(this.hasFilter()) {
            Matcher matcher = pattern.matcher(this.getFilterValue());
            if(matcher.matches()) {
                try {
                    Date date = format.parse(this.getFilterValue());
                    return builder.and(builder.equal(builder.function("date", String.class, root.get("creationDate")), date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                return builder.and(builder.or(
                    builder.like(builder.lower(root.get("email")), "%" + this.getFilterValue() + "%")
                ));
            }
        }
        
        return builder.and(builder.isTrue(root.get("enabled")),
            builder.equal(adminPerson.get("id"), ownerAdminPerson.getId())
        );
*/
    	
    	}
}
