package it.prova.gestioneordiniarticolicategorie.dao.articolo;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.IBaseDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public interface ArticoloDAO extends IBaseDAO<Articolo>{
	
	public Articolo findByIdFetchingCategoria(Long id) throws Exception;

	public void deleteArticoloFromJoinTable(Long idArticolo) throws Exception;

	public Long totalePrezzoArticoliDiCategoria(Categoria categoria) throws Exception;

	public List<Articolo> allArticoliDiOrdiniConProblemi() throws Exception;

}
