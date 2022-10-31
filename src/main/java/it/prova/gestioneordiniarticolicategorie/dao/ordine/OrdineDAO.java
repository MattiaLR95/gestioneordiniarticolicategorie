package it.prova.gestioneordiniarticolicategorie.dao.ordine;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.IBaseDAO;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface OrdineDAO extends IBaseDAO<Ordine> {
	
	public Ordine findByIdFetchingArticolo(Long id);

	public List<Ordine> findOrdiniInUnaDeterminataCategoria(Categoria input);

	public Ordine findOrdineSpedizionePiuRecenteRelativoACategoria(Categoria categoria) throws Exception;

	public Long getPrezzoTotaleArticoliOrdiniPer(String destinatario) throws Exception;

	public List<String> allIndirizziOrdiniArticoliConNumeroSerialeCome(String parteDelSeriale) throws Exception;
	
}
