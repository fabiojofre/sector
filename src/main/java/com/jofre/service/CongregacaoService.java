package com.jofre.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jofre.datatables.Datatables;
import com.jofre.datatables.DatatablesColunas;
import com.jofre.domain.Congregacao;
import com.jofre.domain.Convite;
import com.jofre.repository.CongregacaoRepository;
import com.jofre.repository.ConviteRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class CongregacaoService {

	@Autowired
	private CongregacaoRepository repository;
	
	@Autowired
	private ConviteRepository conviteRepository;
	
	@Autowired
	private Datatables datatables;

	@Transactional(readOnly = false)
	public void salvar(Congregacao congregacao) {
		repository.save(congregacao);		
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> buscarCongregacoes(HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.CONGREGACOES);
		Page<?> page = datatables.getSearch().isEmpty()
				? repository.findAll(datatables.getPageable())
				: repository.findAllByNome(datatables.getSearch(), datatables.getPageable());
		return datatables.getResponse(page);
	}
	
	@Transactional(readOnly = true)
	public Congregacao buscarPorId(Long id) {
		return repository.findById(id).get();
	}

	@Transactional(readOnly = false)
	public void remover(Long id) {
		repository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public List<String> buscarCongregacaoByTermo(String termo) {
		
		return repository.findCongregacoesByTermo(termo);
	}
	
	@Transactional(readOnly = true)
	public Set<Congregacao> buscarPorTitulos(String[] nomes) {
		return repository.findBynomes(nomes);
	}
	
	@Transactional(readOnly = true)
	public List<Congregacao> buscarTodos() {
		return repository.findAll();
	} 
	
	//faz busca das congregações disponíveis por data e áreas e repetidas num período 
	@Transactional(readOnly = true)
	public List<Congregacao> buscarDisponiveis(Long convite) {
		Optional<Convite> c = conviteRepository.findById(convite);
		LocalDate data = c.get().getDataEvento();
		Integer dia = data.getDayOfWeek().getValue();
		Set<Long>indisponiveis = new HashSet<Long>(); 
		List<Integer>areasIndisponiveis =new ArrayList<Integer>();
		indisponiveis.addAll(conviteRepository.findByCongregacoesRepetidas(data));
		indisponiveis.addAll(conviteRepository.findByCongregacesComDiasImportantes(dia));
		areasIndisponiveis.addAll(conviteRepository.findByAreasFestividades(data));
		for(int i =0; i< areasIndisponiveis.size();i++) {
			if(areasIndisponiveis.get(i)==c.get().getArea()) {
				areasIndisponiveis.set(i, 0);
			}
		}
	
		return repository.findByConviteCongregacaoDisponivel(areasIndisponiveis, indisponiveis);
	} 
	
	
	@Transactional(readOnly = true)
	public Set<Congregacao> buscarCongregacaoPorArea(Integer area) {
		
		return repository.findByCongregacaoPorArea(area);
	}
	
	@Transactional(readOnly = true)
	public Congregacao buscarCongregacaoPorNome(String nome) {
		
		return repository.findCongregacaoByNome(nome);
	}

	
	} 
	
//	@Transactional(readOnly = true)
//	public Map<String, Object> buscarEspecialidadesPorespecialista(Long id, HttpServletRequest request) {
//		datatables.setRequest(request);
//		datatables.setColunas(DatatablesColunas.ESPECIALIDADES);
//		Page<Especialidade> page = repository.findByIdMembro(id, datatables.getPageable()); 
//		return datatables.getResponse(page);
//	}
	
//}
