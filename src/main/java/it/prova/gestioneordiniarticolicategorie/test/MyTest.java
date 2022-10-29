package it.prova.gestioneordiniarticolicategorie.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.EntityManagerUtil;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;
import it.prova.gestioneordiniarticolicategorie.service.MyServiceFactory;
import it.prova.gestioneordiniarticolicategorie.service.articolo.ArticoloService;
import it.prova.gestioneordiniarticolicategorie.service.categoria.CategoriaService;
import it.prova.gestioneordiniarticolicategorie.service.ordine.OrdineService;

public class MyTest {
	public static void main(String[] args) {
		OrdineService ordineServiceInstance = MyServiceFactory.getOrdineServiceInstance();
		ArticoloService articoloServiceInstance = MyServiceFactory.getArticoloServiceInstance();
		CategoriaService categoriaServiceInstance = MyServiceFactory.getCategoriaServiceInstance();

		try {

			testInserisciOrdine(ordineServiceInstance);
			System.out.println();

			testAggiornaOrdine(ordineServiceInstance);
			System.out.println();

			testInserisciArticolo(ordineServiceInstance, articoloServiceInstance);
			System.out.println();

			testAggiornaArticolo(ordineServiceInstance, articoloServiceInstance);
			System.out.println();

			testScollegaArticoloDaOrdine(ordineServiceInstance, articoloServiceInstance);
			System.out.println();

			testInserimentoNuovaCategoria(categoriaServiceInstance);
			System.out.println();

			testAggiornamentoCategoria(categoriaServiceInstance);
			System.out.println();

			testInserimentoArticoloACategoria(ordineServiceInstance, articoloServiceInstance, categoriaServiceInstance);
			System.out.println();

			testInserimentoCategoriaAdArticolo(ordineServiceInstance, articoloServiceInstance,
					categoriaServiceInstance);
			System.out.println();

			testRimozioneArticolo(ordineServiceInstance, articoloServiceInstance, categoriaServiceInstance);
			System.out.println();

			testRimozioneCategoria(ordineServiceInstance, articoloServiceInstance, categoriaServiceInstance);
			System.out.println();
			
			testRimozioneOrdine(ordineServiceInstance);
			System.out.println();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			EntityManagerUtil.shutdown();
		}
	}

	private static void testInserisciOrdine(OrdineService ordineServiceInstance) throws Exception {
		System.out.println("Inizio test InserisciOrdine");

		Ordine nuovoOrdine = new Ordine("Mattia", "Via Catania", new Date(),
				new SimpleDateFormat("dd-mm-yyyy").parse("30-10-2022"));
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);
		if (nuovoOrdine.getId() == null)
			throw new RuntimeException("Test inserisciOrdine: FALLITO");
		System.out.println("Test inserisciOrdine: FINE");
	}

	private static void testAggiornaOrdine(OrdineService ordineServiceInstance) throws Exception {
		System.out.println("Inizio testAggiornaOrdine");

		Ordine nuovoOrdine = new Ordine("Carlo", "Via Catania", new Date(),
				new SimpleDateFormat("dd-mm-yyyy").parse("30-10-2022"));
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);

		String vecchioDestinatario = nuovoOrdine.getNomeDestinatario();

		nuovoOrdine.setNomeDestinatario("Franco");
		ordineServiceInstance.aggiorna(nuovoOrdine);
		if (nuovoOrdine.getNomeDestinatario().equals(vecchioDestinatario))
			throw new RuntimeException("Test aggiornaOrdine: FALLITO");
		System.out.println("Test aggiornaOrdine: FINE");
	}

	private static void testInserisciArticolo(OrdineService ordineServiceInstance,
			ArticoloService articoloServiceInstance) throws Exception {
		System.out.println("inizio testInserisciArticolo");

		Articolo nuovoArticolo = new Articolo("Reflex", "abc", 600, new Date());
		nuovoArticolo.setOrdine(ordineServiceInstance.listAll().get(0));
		articoloServiceInstance.inserisciNuovo(nuovoArticolo);
		if (nuovoArticolo.getId() == null)
			throw new Exception("Test inserisciArticolo: FALLITO");
		System.out.println("Test inserisci Articolo: FINE");
	}

	private static void testAggiornaArticolo(OrdineService ordineServiceInstance,
			ArticoloService articoloServiceInstance) throws Exception {
		System.out.println("Inizio testAggiornaArticolo");

		Articolo articoloDaModificare = articoloServiceInstance.listAll()
				.get(articoloServiceInstance.listAll().size() - 1);
		String descrizioneVecchia = articoloDaModificare.getDescrizione();
		articoloDaModificare.setDescrizione("Mirrorless");
		articoloServiceInstance.aggiorna(articoloDaModificare);
		if (articoloDaModificare.getDescrizione().equals(descrizioneVecchia))
			throw new RuntimeException("Test AggiornaArticolo: FALLITO");
		System.out.println("Test AggiornaArticolo: COMPLETATO");
	}

	private static void testScollegaArticoloDaOrdine(OrdineService ordineServiceInstance,
			ArticoloService articoloServiceInstance) throws Exception {
		System.out.println("Inizio testScollegaArticoloDaOrdine");

		Articolo articoloDaEliminare = articoloServiceInstance.listAll()
				.get(articoloServiceInstance.listAll().size() - 1);
		Long idArticoloDaEliminare = articoloDaEliminare.getId();

		articoloServiceInstance.rimuovi(idArticoloDaEliminare);
		List<Articolo> articoliAggiornati = articoloServiceInstance.listAll();
		for (Articolo articoloItem : articoliAggiornati) {
			if (idArticoloDaEliminare.equals(articoloItem.getId()))
				throw new Exception("Test scollegaArticoloDaOrdine: FALLITO");
		}
		System.out.println("Test scollegaArticoloDaOrdine: FINE");
	}

	private static void testInserimentoNuovaCategoria(CategoriaService categoriaServiceInstance) throws Exception {
		System.out.println("Inizio test inserimentoNuovaCategoria");

		Categoria nuovaCategoria = new Categoria("Fotocamera", "Wf45612");
		categoriaServiceInstance.inserisciNuovo(nuovaCategoria);

		if (nuovaCategoria.getId() == null)
			throw new RuntimeException("Test inserimentoNuovaCategoria: FALLITO");
		System.out.println("Test inserimentoNuovaCategoria: FINE");
	}

	private static void testAggiornamentoCategoria(CategoriaService categoriaServiceInstance) throws Exception {
		System.out.println("Inizio test aggiornamentoCategoria");

		Categoria categoriaDaAggiornare = categoriaServiceInstance.listAll()
				.get(categoriaServiceInstance.listAll().size() - 1);
		String vecchiaDescrizione = categoriaDaAggiornare.getDescrizione();
		categoriaDaAggiornare.setDescrizione("Reflex");
		categoriaServiceInstance.aggiorna(categoriaDaAggiornare);
		if (categoriaDaAggiornare.getDescrizione().equals(vecchiaDescrizione))
			throw new RuntimeException("Test aggiornamentoCategoria: FALLITO");
		System.out.println("Test aggiornamentoCategoria: FINE");
	}

	private static void testInserimentoArticoloACategoria(OrdineService ordineServiceInstance,
			ArticoloService articoloServiceInstance, CategoriaService categoriaServiceInstance) throws Exception {
		System.out.println("Inizio test inserimentoArticoloACategoria");

		Categoria nuovaCategoria = new Categoria("Fotocamera", "Wf45612");
		categoriaServiceInstance.inserisciNuovo(nuovaCategoria);

		Articolo nuovoArticolo = new Articolo("Reflex", "abc", 600, new Date());
		nuovoArticolo.setOrdine(ordineServiceInstance.listAll().get(0));
		articoloServiceInstance.inserisciNuovo(nuovoArticolo);

		articoloServiceInstance.aggiungiCategoria(nuovoArticolo, nuovaCategoria);

		Categoria nuovaCategoriaRicaricata = categoriaServiceInstance
				.caricaSingoloElementoEagerArticoli(nuovaCategoria.getId());
		if (nuovaCategoriaRicaricata.getArticoli().size() != 1)
			throw new RuntimeException("Test inserimentoArticoloACategoria: FALLITO");
		System.out.println("Test inserimentoArticoloACategoria: COMPLETATO");
	}

	private static void testInserimentoCategoriaAdArticolo(OrdineService ordineServiceInstance,
			ArticoloService articoloServiceInstance, CategoriaService categoriaServiceInstance) throws Exception {
		System.out.println("Inizio test inserimentoCategoriaAdArticolo");

		Articolo nuovoArticolo = new Articolo("Reflex", "abc", 600, new Date());
		nuovoArticolo.setOrdine(ordineServiceInstance.listAll().get(0));
		articoloServiceInstance.inserisciNuovo(nuovoArticolo);

		Categoria nuovaCategoria = new Categoria("Fotocamera", "Wf45612");
		categoriaServiceInstance.inserisciNuovo(nuovaCategoria);

		articoloServiceInstance.aggiungiCategoria(nuovoArticolo, nuovaCategoria);

		Articolo nuovoArticoloRicaricato = articoloServiceInstance
				.caricaSingoloElementoEagerCategorie(nuovoArticolo.getId());
		if (nuovoArticoloRicaricato.getCategorie().size() != 1)
			throw new RuntimeException("Test inserimentoCategoriaAdArticolo: FALLITO");
		System.out.println("Test inserimentoCategoriaAdArticolo: FINE");
	}

	private static void testRimozioneArticolo(OrdineService ordineServiceInstance,
			ArticoloService articoloServiceInstance, CategoriaService categoriaServiceInstance) throws Exception {
		System.out.println("Inizio test rimozioneArticolo");

		Articolo nuovoArticolo = new Articolo("Reflex", "abc", 600, new Date());
		nuovoArticolo.setOrdine(ordineServiceInstance.listAll().get(0));
		articoloServiceInstance.inserisciNuovo(nuovoArticolo);

		Categoria nuovaCategoria = new Categoria("Fotocamera", "Wf45612");
		categoriaServiceInstance.inserisciNuovo(nuovaCategoria);

		articoloServiceInstance.aggiungiCategoria(nuovoArticolo, nuovaCategoria);

		Long idArticoloDaRimuovere = nuovoArticolo.getId();

		articoloServiceInstance.rimuoviArticoloDaJoinTable(nuovoArticolo.getId());

		articoloServiceInstance.rimuovi(nuovoArticolo.getId());

		List<Articolo> articoliAggiornatiDatabase = articoloServiceInstance.listAll();

		for (Articolo articoloItem : articoliAggiornatiDatabase) {
			if (idArticoloDaRimuovere == articoloItem.getId())
				throw new RuntimeException("Test rimozioneArticolo: FALLITO");
		}
		System.out.println("Test rimozioneArticolo: COMPLETATO");
	}

	private static void testRimozioneCategoria(OrdineService ordineServiceInstance,
			ArticoloService articoloServiceInstance, CategoriaService categoriaServiceInstance) throws Exception {
		System.out.println("Inizio test rimozioneCategoria");

		Articolo nuovoArticolo = new Articolo("Reflex", "abc", 600, new Date());
		nuovoArticolo.setOrdine(ordineServiceInstance.listAll().get(0));
		articoloServiceInstance.inserisciNuovo(nuovoArticolo);

		Categoria nuovaCategoria = new Categoria("Fotocamera", "Wf45612");
		categoriaServiceInstance.inserisciNuovo(nuovaCategoria);

		articoloServiceInstance.aggiungiCategoria(nuovoArticolo, nuovaCategoria);

		Long idCategoriaDaRimuovere = nuovaCategoria.getId();

		categoriaServiceInstance.rimuoviCategoriaDaJoinTable(nuovaCategoria.getId());

		categoriaServiceInstance.rimuovi(nuovaCategoria.getId());

		List<Categoria> categorieAggiornateDatabase = categoriaServiceInstance.listAll();

		for (Categoria categoriaItem : categorieAggiornateDatabase) {
			if (idCategoriaDaRimuovere == categoriaItem.getId())
				throw new RuntimeException("Test rimozioneCategoria: FALLITO");
		}
		System.out.println("Test rimozioneCategoria: COMPLETATO");
	}
	
	private static void testRimozioneOrdine (OrdineService ordineServiceInstance) throws Exception{
		System.out.println("Inizio test rimozioneOrdine");
		
		Ordine ordineDaEliminare = ordineServiceInstance.caricaSingoloElementoEagerArticoli(ordineServiceInstance.listAll().get(0).getId());
		if(ordineDaEliminare.getArticoli().isEmpty())
			ordineServiceInstance.rimuovi(ordineDaEliminare.getId());
		else
			throw new RuntimeException("Attenzione! L'ordine selezionato contiene articoli all'interno.");
		System.out.println("Test rimozioneOrdine: COMPLETATO");
	}

}
