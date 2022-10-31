package it.prova.gestioneordiniarticolicategorie.dao.ordine;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public class OrdineDAOImpl implements OrdineDAO {

	private EntityManager entityManager;

	@Override
	public List<Ordine> list() throws Exception {
		return entityManager.createQuery("from Ordine", Ordine.class).getResultList();
	}

	@Override
	public Ordine get(Long id) throws Exception {
		return entityManager.find(Ordine.class, id);
	}

	@Override
	public void update(Ordine input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		input = entityManager.merge(input);
	}

	@Override
	public void insert(Ordine input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(input);
	}

	@Override
	public void delete(Ordine input) throws Exception {
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
	public Ordine findByIdFetchingArticolo(Long id) {
		TypedQuery<Ordine> query = entityManager.createQuery(
				"select o FROM Ordine o left join fetch o.articoli a where o.id = :idOrdine", Ordine.class);
		query.setParameter("idOrdine", id);
		return query.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public List<Ordine> findOrdiniInUnaDeterminataCategoria(Categoria input) {
		return entityManager
				.createQuery("select o from Ordine o left join o.articoli a left join a.categorie c where c.id=?1",
						Ordine.class)
				.setParameter(1, input.getId()).getResultList();
	}
	
	@Override
	public Ordine findOrdineSpedizionePiuRecenteRelativoACategoria(Categoria categoria)throws Exception{
		return entityManager.createQuery("select o from Ordine o inner join o.articoli a inner join a.categorie c where c.id=?1 order by o.dataSpedizione desc", Ordine.class).setParameter(1, categoria.getId()).getResultStream().findFirst().orElse(null);
	}
	
	@Override
	public Long getPrezzoTotaleArticoliOrdiniPer(String destinatario)throws Exception {
		if(destinatario == null || destinatario.isBlank())
			throw new Exception("Impossibile effettuare la ricerca, input non valido.");
		return entityManager.createQuery("select sum(a.prezzoSingolo) from Ordine o inner join o.articoli a where o.nomeDestinatario like ?1 ", Long.class).setParameter(1, destinatario).getResultStream().findFirst().orElse(null);
	}
	
	@Override
	public List<String> allIndirizziOrdiniArticoliConNumeroSerialeCome(String parteDelSeriale)throws Exception{
		if(parteDelSeriale == null || parteDelSeriale.isEmpty())
			throw new Exception("Impossibile effettuare la ricerca, input non valido.");
		return entityManager.createQuery("select o.indirizzoSpedizione from Ordine o inner join o.articoli a where a.numeroSeriale Like ?1",String.class).setParameter(1, "%"+parteDelSeriale+"%").getResultList();
	}


}
