package com.jofre.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jofre.datatables.Datatables;
import com.jofre.datatables.DatatablesColunas;
import com.jofre.domain.CargoEc;
import com.jofre.repository.CargoEcRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class CargoEcService {

	@Autowired
	private CargoEcRepository repository;
	
	@Autowired
	private Datatables datatables;

	@Transactional(readOnly = false)
	public void salvar(CargoEc cargoEc) {
		repository.save(cargoEc);		
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> buscarCargos(HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.CARGOS);
		Page<?> page = datatables.getSearch().isEmpty()
				? repository.findAll(datatables.getPageable())
				: repository.findAllByNome(datatables.getSearch(), datatables.getPageable());
		return datatables.getResponse(page);
	}
	
	@Transactional(readOnly = true)
	public CargoEc buscarPorId(Long id) {
		return repository.findById(id).get();
	}

	@Transactional(readOnly = false)
	public void remover(Long id) {
		repository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public List<String> buscarCargoByTermo(String termo) {
		
		return repository.findCargosByTermo(termo);
	}
	
	@Transactional(readOnly = true)
	public Set<CargoEc> buscarPorTitulos(String[] nomes) {
		return repository.findBynomes(nomes);
	}
	
	@Transactional(readOnly = true)
	public List<CargoEc> buscarTodos() {
		return repository.findAll();
	} 
	
//	@Transactional(readOnly = true)
//	public Map<String, Object> buscarEspecialidadesPorespecialista(Long id, HttpServletRequest request) {
//		datatables.setRequest(request);
//		datatables.setColunas(DatatablesColunas.ESPECIALIDADES);
//		Page<Especialidade> page = repository.findByIdMembro(id, datatables.getPageable()); 
//		return datatables.getResponse(page);
//	}
	
}
