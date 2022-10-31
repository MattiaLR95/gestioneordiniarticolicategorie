package it.prova.gestioneordiniarticolicategorie.dao.categoria;

import java.util.Date;
import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.IBaseDAO;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface CategoriaDAO extends IBaseDAO<Categoria> {

	public Categoria findByIdFetchingArticolo(Long id) throws Exception;

	void deleteCategoriaFromJoinTable(Long idCategoria) throws Exception;

	List<Categoria> allCategorieInOrdine(Ordine ordine) throws Exception;

	List<String> allCodiciDiCategorieDegliOrdiniEffettuatiNelMeseDi(Date dataDiRiferimento) throws Exception;
	
}
