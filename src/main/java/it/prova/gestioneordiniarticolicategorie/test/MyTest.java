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
			
			testTuttiOrdiniInDeterminataCategoria(ordineServiceInstance, categoriaServiceInstance, articoloServiceInstance);
			System.out.println();
			
			testTutteCategorieInOrdine(ordineServiceInstance, categoriaServiceInstance, articoloServiceInstance);
			System.out.println();
			
			testSommaPrezzoArticoliDiCategoria(ordineServiceInstance, categoriaServiceInstance, articoloServiceInstance);
			System.out.println();
			
			testOrdinePiuRecenteConArticoliDiCategoria(categoriaServiceInstance, articoloServiceInstance ,ordineServiceInstance);
			System.out.println();
			
			testTuttiCodiciDiCategorieDegliOrdiniDelMeseDi(categoriaServiceInstance, ordineServiceInstance);
			System.out.println();
			
			testPrezzoTotaleArticoliOrdiniPer(ordineServiceInstance, articoloServiceInstance);
			System.out.println();
			
			testTuttiIndirizziOrdiniConArticoliConNumeroSerialeCome(ordineServiceInstance, articoloServiceInstance);
			System.out.println();
			
			testListaArticoliOrdiniConErrori(articoloServiceInstance, ordineServiceInstance);

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
	
	private static void testTuttiOrdiniInDeterminataCategoria(OrdineService ordineService,
			CategoriaService categoriaService, ArticoloService articoloService) throws Exception {
		System.out.println("\n.....inizio testTuttiOrdiniConArticoliDiCategoria");
		List<Articolo> tuttiArticoliSuDB = articoloService.listAll();
		List<Categoria> tutteCategorieSuDB = categoriaService.listAll();
		List<Ordine> tuttiOrdiniSuDB = ordineService.listAll();
		if (tuttiOrdiniSuDB.isEmpty() || tutteCategorieSuDB.isEmpty() || tuttiArticoliSuDB.isEmpty())
			throw new RuntimeException("FAIL : una o piu' tabelle del DB sono vuote.");
		List<Ordine> result = ordineService.trovaOrdiniInUnaDeterminataCategoria(tutteCategorieSuDB.get(1));
		if (result.isEmpty())
			throw new RuntimeException("FAIL : la ricerca non ha dato i risultati attesi.");
		System.out.println("..... fine testTuttiOrdiniConArticoliDiCategoria : PASS");
	}

	private static void testTutteCategorieInOrdine(OrdineService ordineService,
			CategoriaService categoriaService, ArticoloService articoloService) throws Exception {
		System.out.println("\n.....inizio testTutteCategorieDistinteNellOrdine");
		
		Ordine nuovoOrdine = new Ordine("Mattia", "Via Catania", new Date(),
				new SimpleDateFormat("dd-mm-yyyy").parse("30-10-2022"));
		ordineService.inserisciNuovo(nuovoOrdine);
		
		Articolo nuovoArticolo = new Articolo("Reflex", "abc", 600, new Date());
		nuovoArticolo.setOrdine(nuovoOrdine);
		articoloService.inserisciNuovo(nuovoArticolo);
		
		Categoria nuovaCategoria = new Categoria("Fotocamera", "Wf45612");
		categoriaService.inserisciNuovo(nuovaCategoria);
		articoloService.aggiungiCategoria(nuovoArticolo, nuovaCategoria);
		
		List<Categoria> result = categoriaService.TutteCategorieInOrdine(nuovoOrdine);
		if (result.size() != 1)
			throw new RuntimeException("FAIL : la ricerca non ha dato i risultati attesi.");
		System.out.println("..... fine testTutteCategorieDistinteNellOrdine : PASS");
	}

	private static void testSommaPrezzoArticoliDiCategoria(OrdineService ordineService,CategoriaService categoriaService,
			ArticoloService articoloService) throws Exception {
		System.out.println("\n.....inizio testSommaPrezzoArticoliDiCategoria");
		
		Ordine nuovoOrdine = new Ordine("Mattia", "Via Catania", new Date(),
				new SimpleDateFormat("dd-mm-yyyy").parse("30-10-2022"));
		ordineService.inserisciNuovo(nuovoOrdine);
		
		Articolo nuovoArticolo = new Articolo("Reflex", "abc", 600, new Date());
		nuovoArticolo.setOrdine(nuovoOrdine);
		articoloService.inserisciNuovo(nuovoArticolo);
		
		Categoria nuovaCategoria = new Categoria("Fotocamera", "Wf45612");
		categoriaService.inserisciNuovo(nuovaCategoria);
		articoloService.aggiungiCategoria(nuovoArticolo, nuovaCategoria);
		
		Long result = articoloService.sommaPrezzoArticoliDiCategoria(nuovaCategoria);
		if (result != nuovoArticolo.getPrezzoSingolo())
			throw new RuntimeException("FAIL : la somma dei prezzi non e' corretta.");
		System.out.println("..... fine testSommaPrezzoArticoliDiCategoria : PASS");
	}

	private static void testOrdinePiuRecenteConArticoliDiCategoria(CategoriaService categoriaService, ArticoloService articoloService ,
			OrdineService ordineService) throws Exception {
		System.out.println("\n.....inizio testOrdinePiuRecenteConArticoliDiCategoria");
		
		Ordine nuovoOrdine = new Ordine("Mattia", "Via Catania", new Date(),
				new SimpleDateFormat("dd-mm-yyyy").parse("30-10-2022"));
		ordineService.inserisciNuovo(nuovoOrdine);
		
		Articolo nuovoArticolo = new Articolo("Reflex", "abc", 600, new Date());
		nuovoArticolo.setOrdine(nuovoOrdine);
		articoloService.inserisciNuovo(nuovoArticolo);
		
		Categoria nuovaCategoria = new Categoria("Fotocamera", "Wf45612");
		categoriaService.inserisciNuovo(nuovaCategoria);
		articoloService.aggiungiCategoria(nuovoArticolo, nuovaCategoria);
		
		Ordine result = ordineService.trovaOrdineSpedizionePiuRecenteRelativoACategoria(nuovaCategoria);
		if (result == null)
			throw new RuntimeException("FAIL : la ricerca non ha dato i risultati attesi.");
		System.out.println("..... fine testOrdinePiuRecenteConArticoliDiCategoria : PASS");
	}

	private static void testTuttiCodiciDiCategorieDegliOrdiniDelMeseDi(CategoriaService categoriaService,
			OrdineService ordineService) throws Exception {
		System.out.println("\n.....inizio testTuttiCodiciDiCategorieDegliOrdiniDelMeseDi");
		List<Categoria> tutteCategorieSuDB = categoriaService.listAll();
		List<Ordine> tuttiOrdiniSuDB = ordineService.listAll();
		if (tuttiOrdiniSuDB.isEmpty() || tutteCategorieSuDB.isEmpty())
			throw new RuntimeException("FAIL : una o piu' tabelle del DB sono vuote.");
		List<String> result = categoriaService.tuttiCodiciDiCategorieDegliOrdiniEffettuatiNelMeseDi(new Date());
		if (result.size() != 1)
			throw new RuntimeException("FAIL : la ricerca non ha dato i risultati attesi.");
		System.out.println("..... fine testTuttiCodiciDiCategorieDegliOrdiniDelMeseDi : PASS");
	}

	private static void testPrezzoTotaleArticoliOrdiniPer(OrdineService ordineService, ArticoloService articoloService)
			throws Exception {
		System.out.println("\n.....inizio testPrezzoTotaleArticoliOrdiniPer");
		Ordine nuovoOrdine = new Ordine("Mario", "Via Catania", new Date(),
				new SimpleDateFormat("dd-mm-yyyy").parse("30-10-2022"));
		ordineService.inserisciNuovo(nuovoOrdine);
		
		Articolo nuovoArticolo = new Articolo("Reflex", "abc", 600, new Date());
		nuovoArticolo.setOrdine(nuovoOrdine);
		articoloService.inserisciNuovo(nuovoArticolo);
		
		Long result = ordineService.caricaPrezzoTotaleArticoliOrdiniPer("Mario");
		if (result != 600)
			throw new RuntimeException("FAIL : la ricerca non ha dato i risultati attesi.");
		
		articoloService.rimuovi(nuovoArticolo.getId());
		ordineService.rimuovi(nuovoOrdine.getId());
		System.out.println("..... fine testPrezzoTotaleArticoliOrdiniPer : PASS");
	}

	private static void testTuttiIndirizziOrdiniConArticoliConNumeroSerialeCome(OrdineService ordineService,
			ArticoloService articoloService) throws Exception {
		System.out.println("\n.....inizio testListaIndirizziOrdiniConArticoliConNumeroSerialeCome");
		
		Ordine nuovoOrdine = new Ordine("Paolo", "Via Catania", new Date(),
				new SimpleDateFormat("dd-mm-yyyy").parse("30-10-2022"));
		ordineService.inserisciNuovo(nuovoOrdine);
		
		Articolo nuovoArticolo = new Articolo("Reflex", "glkm", 600, new Date());
		nuovoArticolo.setOrdine(nuovoOrdine);
		articoloService.inserisciNuovo(nuovoArticolo);
		
		List<String> result = ordineService.tuttiIndirizziOrdiniArticoliConNumeroSerialeCome("lk");
		if (result.isEmpty())
			throw new RuntimeException("FAIL : la ricerca non ha dato i risultati attesi.");
		System.out.println("..... fine testListaIndirizziOrdiniConArticoliConNumeroSerialeCome : PASS");
	}

	private static void testListaArticoliOrdiniConErrori(ArticoloService articoloService, OrdineService ordineService)
			throws Exception {
		System.out.println("\n.....inizio testListaArticoliOrdiniConErrori");
		ordineService.inserisciNuovo(
				new Ordine("Franco", "via Umberto 15", new SimpleDateFormat("dd-MM-yyyy").parse("05-11-2021"),
						new SimpleDateFormat("dd-MM-yyyy").parse("25-12-2020")));
		Articolo nuovoArticolo = new Articolo("farina", "FRN8JNB", 4, new Date());
		nuovoArticolo.setOrdine(ordineService.listAll().get(0));
		articoloService.inserisciNuovo(nuovoArticolo);
		List<Articolo> result = articoloService.tuttiArticoliDiOrdiniConProblemi();
		if (result.isEmpty())
			throw new RuntimeException("FAIL : la ricerca non ha dato i risultati attesi.");
		System.out.println("..... fine testListaArticoliOrdiniConErrori : PASS");
	}

}
