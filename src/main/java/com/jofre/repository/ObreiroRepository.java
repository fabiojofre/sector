package com.jofre.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.jofre.domain.Obreiro;

public interface ObreiroRepository extends JpaRepository<Obreiro, Long> {

	@Query("select p from Obreiro p where p.nome like concat('%',:nome,'%') order by p.congregacao.area")
	public List<Obreiro> findByNome(String nome);
	
	@Query("select p from Obreiro p where p.congregacao.id = :id and p.bloqueado = false order by p.congregacao.area")
	public List<Obreiro> findByCongregacaoId(Long id);
	
	@Query("select p from Obreiro p where p.cartaoMembro = :cartaoMembro and p.bloqueado = false  order by p.congregacao.area")
	public List<Obreiro> findByCartao(Long cartaoMembro);
	
	@Query("select p from Obreiro p where p.whatsapp = :whatsapp and p.bloqueado = false order by p.congregacao.area")
	public List<Obreiro> findByWhatsapp(String whatsapp);

	@Query("select p from Obreiro p where p.cargoEc.id = :cargo_id and p.bloqueado = false order by p.congregacao.area")
	public  List<Obreiro> findCargoById(Long cargo_id);
	
	@Query("select p from Obreiro p "
			+ "where EXTRACT(MONTH FROM p.dataNascimento) = EXTRACT(MONTH FROM :data)"
			+ " AND EXTRACT(WEEK FROM p.dataNascimento) = EXTRACT(WEEK FROM :data)")
	public List<Obreiro> findByAniversario(@DateTimeFormat(iso = ISO.DATE) LocalDate data);

	@Query("select o from Obreiro o where o.bloqueado = false order by o.congregacao.area ")
	public List<Obreiro> buscaOrdemArea();
	

	
	
	
	
	
//	//esse precisa ser refeito
//	@Query("select p from Obreiro p where p.bloqueado = false or p.bloqueado = null order by p.id")
//	public List<Obreiro> findByData(Date data);
//	
//	//esse também precisa ser refeito
//	@Query("select p from Obreiro p where (p.bloqueado = false or p.bloqueado = null) and p.Congregacao.id = ?1")
//	public List<Obreiro> findByDataCongregacao(Date data,Integer id);
	
}
