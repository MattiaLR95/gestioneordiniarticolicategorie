package it.prova.gestioneordiniarticolicategorie.dao.articolo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public class ArticoloDAOImpl implements ArticoloDAO {

	private EntityManager entityManager;

	@Override
	public List<Articolo> list() throws Exception {
		return entityManager.createQuery("from Articolo", Articolo.class).getResultList();
	}

	@Override
	public Articolo get(Long id) throws Exception {
		return entityManager.find(Articolo.class, id);
	}

	@Override
	public void update(Articolo input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		input = entityManager.merge(input);
	}

	@Override
	public void insert(Articolo input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(input);
	}

	@Override
	public void delete(Articolo input) throws Exception {
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
	public Articolo findByIdFetchingCategoria(Long id) throws Exception {
		TypedQuery<Articolo> query = entityManager.createQuery(
				"select a FROM Articolo a left join fetch a.categorie c where a.id = :idArticolo", Articolo.class);
		query.setParameter("idArticolo", id);
		return query.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public void deleteArticoloFromJoinTable(Long idArticolo) throws Exception {
		entityManager.createNativeQuery("delete from articolo_categoria where articolo_id = ?1")
				.setParameter(1, idArticolo).executeUpdate();
	}

	@Override
	public Long totalePrezzoArticoliDiCategoria(Categoria categoria) throws Exception {
		if (categoria == null || categoria.getId() == null || categoria.getId() < 1)
			throw new Exception("Impossibile effettuare la ricerca, input errato");
		return entityManager
				.createQuery("select sum(a.prezzoSingolo) from Categoria c left join c.articoli a where c.id=?1",
						Long.class)
				.setParameter(1, categoria.getId()).getResultStream().findFirst().orElse(null);
	}

	@Override
	public List<Articolo> allArticoliDiOrdiniConProblemi() throws Exception {
		return entityManager
				.createQuery("select a from Ordine o inner join o.articoli a where o.dataSpedizione > o.dataScadenza",
						Articolo.class)
				.getResultList();
	}

}
