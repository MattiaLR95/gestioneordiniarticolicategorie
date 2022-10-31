package it.prova.gestioneordiniarticolicategorie.service.categoria;

import java.util.Date;
import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.categoria.CategoriaDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface CategoriaService {

	public List<Categoria> listAll() throws Exception;

	public Categoria caricaSingoloElemento(Long id) throws Exception;

	public Categoria caricaSingoloElementoEagerArticoli(Long id) throws Exception;

	public void aggiorna(Categoria cateogriaInstance) throws Exception;

	public void inserisciNuovo(Categoria categoriaInstance) throws Exception;

	public void rimuovi(Long idCategoria) throws Exception;

	// per injection
	public void setCategoriaDAO(CategoriaDAO categoriaDAO);

	void aggiungiArticolo(Articolo articoloInstance, Categoria categoriaInstance) throws Exception;
	
	public void rimuoviCategoriaDaJoinTable(Long idCategoria) throws Exception;
	
	List<Categoria> TutteCategorieInOrdine(Ordine ordine) throws Exception;

	List<String> tuttiCodiciDiCategorieDegliOrdiniEffettuatiNelMeseDi(Date dataDiRiferimento) throws Exception;


}
