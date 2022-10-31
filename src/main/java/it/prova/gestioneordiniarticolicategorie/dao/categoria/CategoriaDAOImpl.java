package it.prova.gestioneordiniarticolicategorie.dao.categoria;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public class CategoriaDAOImpl implements CategoriaDAO {

	private EntityManager entityManager;

	@Override
	public List<Categoria> list() throws Exception {
		return entityManager.createQuery("from Categoria", Categoria.class).getResultList();
	}

	@Override
	public Categoria get(Long id) throws Exception {
		return entityManager.find(Categoria.class, id);
	}

	@Override
	public void update(Categoria input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		input = entityManager.merge(input);
	}

	@Override
	public void insert(Categoria input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(input);
	}

	@Override
	public void delete(Categoria input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(input));
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Categoria findByIdFetchingArticolo(Long id) throws Exception {
		TypedQuery<Categoria> query = entityManager.createQuery(
				"select c FROM Categoria c left join fetch c.articoli a where c.id = :idCategoria", Categoria.class);
		query.setParameter("idCategoria", id);
		return query.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public void deleteCategoriaFromJoinTable(Long idCategoria) throws Exception {
		entityManager.createNativeQuery("delete from articolo_categoria where categoria_id = ?1")
				.setParameter(1, idCategoria).executeUpdate();
	}
	
	@Override
	public List<Categoria> allCategorieInOrdine(Ordine ordine) throws Exception{
		if(ordine == null || ordine.getId() == null || ordine .getId() < 1)
			throw new Exception("ERRORE! Input inserito non valido");
		
		return entityManager.createQuery("select distinct c from Categoria c inner join c.articoli a where a.ordine.id=?1",Categoria.class).setParameter(1, ordine.getId()).getResultList();
	}
	
	@Override
	public List<String> allCodiciDiCategorieDegliOrdiniEffettuatiNelMeseDi(Date dataDiRiferimento)throws Exception{
		return entityManager.createQuery("select distinct c.codice from Ordine o inner join o.articoli a inner join a.categorie c where month(o.dataSpedizione)=month(?1) and year(o.dataSpedizione)=year(?1)", String.class).setParameter(1,new java.sql.Date(dataDiRiferimento.getTime())).getResultList();
	}

}
