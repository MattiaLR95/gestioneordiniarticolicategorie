package it.prova.gestioneordiniarticolicategorie.service.ordine;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.ordine.OrdineDAO;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface OrdineService {
	public List<Ordine> listAll() throws Exception;

	public Ordine caricaSingoloElemento(Long id) throws Exception;

	public Ordine caricaSingoloElementoEagerArticoli(Long id) throws Exception;

	public void aggiorna(Ordine ordineInstance) throws Exception;

	public void inserisciNuovo(Ordine ordineInstance) throws Exception;

	public void rimuovi(Long idOrdine) throws Exception;
	
	public List<Ordine> trovaOrdiniInUnaDeterminataCategoria(Categoria input);

	public Ordine trovaOrdineSpedizionePiuRecenteRelativoACategoria(Categoria categoria) throws Exception;

	public Long caricaPrezzoTotaleArticoliOrdiniPer(String destinatario) throws Exception;

	public List<String> tuttiIndirizziOrdiniArticoliConNumeroSerialeCome(String parteDelSeriale) throws Exception;

	// per injection
	public void setOrdineDAO(OrdineDAO ordineDAO);
}
