package com.jofre.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jofre.datatables.Datatables;
import com.jofre.domain.Obreiro;
import com.jofre.exception.AcessoNegadoException;
import com.jofre.repository.ObreiroRepository;

@Service
public class ObreiroService {

	@Autowired
	private ObreiroRepository repo;

	@Autowired
	private Datatables datatables;

	
	@Transactional(readOnly = false)
	public void salvar(Obreiro obreiro) {
		repo.save(obreiro);
		
	}
	@Transactional(readOnly = true)
	public Obreiro buscarPorIdEUsuario(Long id) {
		return repo.findById(id)
				.orElseThrow(() -> new AcessoNegadoException("Acesso negado ao usuário: " ));
	}

	@Transactional(readOnly = false)
	public void editar(Obreiro obreiro) {
		Obreiro o =  buscarPorIdEUsuario(obreiro.getId());
		o.setNome(obreiro.getNome());
		o.setDataNascimento(obreiro.getDataNascimento());
		o.setCartaoMembro(obreiro.getCartaoMembro());
		o.setDataUltimaEscala(obreiro.getDataUltimaEscala());
		o.setBloqueado(obreiro.getBloqueado());
		o.setDiasDisponivel(obreiro.getDiasDisponivel());
		o.setDisponiblidade(obreiro.getDisponiblidade());
		o.seteDomingo(obreiro.geteDomingo());
		o.seteSegunda(obreiro.geteSegunda());
		o.seteTerca(obreiro.geteTerca());
		o.seteQuarta(obreiro.geteQuarta());
		o.seteQuinta(obreiro.geteQuinta());
		o.seteSexta(obreiro.geteSexta());
		o.seteSabado(obreiro.geteSabado());
		o.setEscalaIntermitente(obreiro.getEscalaIntermitente());
		o.setWhatsapp(obreiro.getWhatsapp());
		o.setTrabalhoIntermitente(o.getTrabalhoIntermitente());
		o.setCongregacao(obreiro.getCongregacao());
		o.setCargoEc(obreiro.getCargoEc());
		o.setCirculo(obreiro.getCirculo());
		o.setAcesso(obreiro.getAcesso());
		
	}

	@Transactional(readOnly = false)
	public void remover(Long id) {
		
		repo.deleteById(id);
		
	}


	public Obreiro buscarPorId(Long id) {
		
		return repo.findById(id).get();
	}

	//revisar
	@Transactional(readOnly = true)
	public List<Obreiro> buscarTodos() {
//		Calendar c = Calendar.getInstance();
//		buscarPorData(c.getTime());
		return repo.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Obreiro> buscarPorNome(String nome) {
	
		return repo.findByNome(nome);
	}

	@Transactional(readOnly = true)
	public List<Obreiro> buscarPorCongregacao(Long id) {
		return repo.findByCongregacaoId(id);
	}

	@Transactional(readOnly = true)
	public List<Obreiro> buscarPorCartao(Long cartaoMembro) {
		return repo.findByCartao(cartaoMembro);
	}

	@Transactional(readOnly = true)
	public List<Obreiro> buscarPorWhatsapp(String telefone) {
		// TODO Auto-generated method stub
		return repo.findByWhatsapp(telefone);
	}
//	@Transactional(readOnly = true)
//	public List<Obreiro> buscarPorData(Date data) {
//		// TODO Auto-generated method stub
//		return repo.findByData(data);
//	}
//
//	@Transactional(readOnly = true)
//	public List<Obreiro> buscarPorDataCongregacao(Date data,Integer id) {
//		// TODO Auto-generated method stub
//		return repo.findByDataCongregacao(data,id);
//	}
	public List<Obreiro>  buscarPorCargo(Long cargo_id) {
		
		return repo.findCargoById(cargo_id);
	}
	
	public List<Obreiro>buscarAniversariantes(@DateTimeFormat(iso = ISO.DATE)LocalDate data){
		return repo.findByAniversario(data);
	}
	public List<Obreiro>buscarPorData(@DateTimeFormat(iso = ISO.DATE)LocalDate data) {
		List<Obreiro>lista = repo.buscaOrdemArea();
		List<Obreiro>disponiveis = new ArrayList<>();		
//		System.out.println("----------------> "+data);
		for(Obreiro ob : lista) {
//			System.out.println(ob.toString());
			
			String diasLivres = "";
			
				if(ob.geteSegunda()) diasLivres +="1";
				if(ob.geteTerca()) diasLivres +="2";
				if(ob.geteQuarta())	diasLivres +="3";
				if(ob.geteQuinta()) diasLivres +="4";
				if(ob.geteSexta()) 	diasLivres +="5";
				if(ob.geteSabado())	diasLivres +="6";
				if(ob.geteDomingo())diasLivres +="7";
				
				if((diasLivres.contains(data.getDayOfWeek().getValue()+"")&&    // verificar se o dia da semana contem na variável dias livres
						ob.getTrabalhoIntermitente()==true &&				// verifica se esse obreiro trabalha em regime inetermitente	
								ob.getDataUltimaEscala()!=null&&
									ob.getEscalaIntermitente()!=null)&&    // faço a subtração das datas e tiro o resto da divisão por X+1, se for diferente a zero...
										((ChronoUnit.DAYS.between(data, ob.getDataUltimaEscala())) % (ob.getEscalaIntermitente()+1)!=0)) {
					
//					System.out.println(diasLivres.contains(data.getDayOfWeek().getValue()+""))
//					System.out.println(ob.getNome()+" - dia da semana: -> "+data.getDayOfWeek().getValue()+"");
//					System.out.println("Somatório ------------------------------"+(ChronoUnit.DAYS.between(data, ob.getDataUltimaEscala())));
					disponiveis.add(ob);
					
				}
				else if((diasLivres.contains(data.getDayOfWeek().getValue()+"")&& 
						(ob.getTrabalhoIntermitente()==false || ob.getEscalaIntermitente()==null || ob.getDataUltimaEscala()==null))){
//					System.out.println(ob.getNome()+" - dia da semana: ");
//					System.out.println("*******************************"+(data.getDayOfWeek().getValue()));
					disponiveis.add(ob);
					
				}
		}
		
		
		return disponiveis;
	}
}
