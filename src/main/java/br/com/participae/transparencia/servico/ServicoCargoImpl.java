package br.com.participae.transparencia.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.participae.transparencia.dominio.Cargo;
import br.com.participae.transparencia.repositorio.CargoDAO;

@Service
public class ServicoCargoImpl implements ServicoCargo {

	private CargoDAO cargoDAO;
	
	@Autowired
	public ServicoCargoImpl(CargoDAO cargoDAO) {
		this.cargoDAO = cargoDAO;
	}
	
	@Override
	public List<String> nomes() {
		return this.cargoDAO.nomes();
	}

	@Override
	public List<Cargo> todos() {
		return this.cargoDAO.findByOrderByNome();
	}

	@Override
	public void salvar(Cargo cargo) {
		this.cargoDAO.save(cargo);
	}
	
}
