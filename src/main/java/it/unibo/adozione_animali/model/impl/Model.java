package it.unibo.adozione_animali.model.impl;

import it.unibo.adozione_animali.model.api.statistica.StatisticManager;
import it.unibo.adozione_animali.model.impl.animale.AnimaleDAO;
import it.unibo.adozione_animali.model.impl.animale.CentroDAO;
import it.unibo.adozione_animali.model.impl.caratteristica.AppartenenzaDAO;
import it.unibo.adozione_animali.model.impl.caratteristica.CaratteristichePersonaliDAO;
import it.unibo.adozione_animali.model.impl.caratteristica.ContenutoDAO;
import it.unibo.adozione_animali.model.impl.caratteristica.RiferimentoDAO;
import it.unibo.adozione_animali.model.impl.caratteristica.SpecificaRazzaDAO;
import it.unibo.adozione_animali.model.impl.indirizzo.CittaDAO;
import it.unibo.adozione_animali.model.impl.indirizzo.IndirizzoDAO;
import it.unibo.adozione_animali.model.impl.indirizzo.ProvinciaDAO;
import it.unibo.adozione_animali.model.impl.spazio.AbitanteDAO;
import it.unibo.adozione_animali.model.impl.spazio.SpazioDAO;
import it.unibo.adozione_animali.model.impl.spazio.SpazioPersonaDAO;
import it.unibo.adozione_animali.model.impl.statistica.DatoDAO;
import it.unibo.adozione_animali.model.impl.statistica.StatisticManagerImpl;
import it.unibo.adozione_animali.model.impl.statistica.StatisticaDAO;

public class Model {

    private TurnoLavorativoDAO turnoLavorativoDAO = new TurnoLavorativoDAO();
    private TaskDAO taskDAO = new TaskDAO();
    private RicoveroDAO ricoveroDAO = new RicoveroDAO();
    private RichiestaDAO richiestaDAO = new RichiestaDAO();
    private RichiedenteDAO richiedenteDAO = new RichiedenteDAO();
    private RazzaDAO razzaDAO = new RazzaDAO();
    private ProblemaDAO problemaDAO = new ProblemaDAO();
    private PersonaleDAO personaleDAO = new PersonaleDAO();
    private PersonaDAO personaDAO = new PersonaDAO();
    private PaginaDAO paginaDAO = new PaginaDAO();
    private FascicoloDAO fascicoloDAO = new FascicoloDAO();
    private EsameDAO esameDAO = new EsameDAO();
    private ComponenteDAO componenteDAO = new ComponenteDAO();
    private DatoDAO datoDAO = new DatoDAO();
    private StatisticaDAO statisticaDAO = new StatisticaDAO();
    private StatisticManager statisticManager = new StatisticManagerImpl();
    private SpazioDAO spazioDAO = new SpazioDAO();
    private SpazioPersonaDAO spazioPersonaDAO = new SpazioPersonaDAO();
    private AbitanteDAO abitanteDAO = new AbitanteDAO();
    private CittaDAO cittaDAO = new CittaDAO();
    private ProvinciaDAO provinciaDAO = new ProvinciaDAO();
    private IndirizzoDAO indirizzoDAO = new IndirizzoDAO();
    private AppartenenzaDAO appartenenzaDAO = new AppartenenzaDAO();
    private CaratteristichePersonaliDAO caratteristicaPersonaliDAO = new CaratteristichePersonaliDAO();
    private ContenutoDAO contenutoDAO = new ContenutoDAO();
    private RiferimentoDAO riferimentoDAO = new RiferimentoDAO();
    private SpecificaRazzaDAO specificaRazzaDAO = new SpecificaRazzaDAO();
    private AnimaleDAO animaleDAO = new AnimaleDAO();
    private CentroDAO centroDAO = new CentroDAO();

    public TurnoLavorativoDAO getTurnoLavorativoDAO() {
        return turnoLavorativoDAO;
    }
    public TaskDAO getTaskDAO() {
        return taskDAO;
    }
    public RicoveroDAO getRicoveroDAO() {
        return ricoveroDAO;
    }
    public RichiestaDAO getRichiestaDAO() {
        return richiestaDAO;
    }
    public RichiedenteDAO getRichiedenteDAO() {
        return richiedenteDAO;
    }
    public RazzaDAO getRazzaDAO() {
        return razzaDAO;
    }
    public ProblemaDAO getProblemaDAO() {
        return problemaDAO;
    }
    public PersonaleDAO getPersonaleDAO() {
        return personaleDAO;
    }
    public PersonaDAO getPersonaDAO() {
        return personaDAO;
    }
    public PaginaDAO getPaginaDAO() {
        return paginaDAO;
    }
    public FascicoloDAO getFascicoloDAO() {
        return fascicoloDAO;
    }
    public EsameDAO getEsameDAO() {
        return esameDAO;
    }
    public ComponenteDAO getComponenteDAO() {
        return componenteDAO;
    }
    public DatoDAO getDatoDAO() {
        return datoDAO;
    }
    public StatisticaDAO getStatisticaDAO() {
        return statisticaDAO;
    }
    public StatisticManager getStatisticManager() {
        return statisticManager;
    }
    public SpazioDAO getSpazioDAO() {
        return spazioDAO;
    }
    public SpazioPersonaDAO getSpazioPersonaDAO() {
        return spazioPersonaDAO;
    }
    public AbitanteDAO getAbitanteDAO() {
        return abitanteDAO;
    }
    public CittaDAO getCittaDAO() {
        return cittaDAO;
    }
    public ProvinciaDAO getProvinciaDAO() {
        return provinciaDAO;
    }
    public IndirizzoDAO getIndirizzoDAO() {
        return indirizzoDAO;
    }
    public AppartenenzaDAO getAppartenenzaDAO() {
        return appartenenzaDAO;
    }
    public CaratteristichePersonaliDAO getCaratteristicaPersonaliDAO() {
        return caratteristicaPersonaliDAO;
    }
    public ContenutoDAO getContenutoDAO() {
        return contenutoDAO;
    }
    public RiferimentoDAO getRiferimentoDAO() {
        return riferimentoDAO;
    }
    public SpecificaRazzaDAO getSpecificaRazzaDAO() {
        return specificaRazzaDAO;
    }
    public AnimaleDAO getAnimaleDAO() {
        return animaleDAO;
    }
    public CentroDAO getCentroDAO() {
        return centroDAO;
    }
}
