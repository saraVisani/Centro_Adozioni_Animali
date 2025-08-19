public class EliminaCentro extends AbstractRoutine {

    public static final String COD_PROVINCIA = "COD_PROVINCIA";
    public static final String COD_CITTA_ = "COD_CITTA_";
    public static final String NUMERO = "NUMERO";
    public static final String COD_ANIMALE = "COD_ANIMALE";
    public static final String CF = "CF";
    public static final String COD_PROVINCIA_RICHIEDENTE = "COD_PROVINCIA_richiedente";
    public static final String COD_CITTA_RICHIEDENTE = "COD_CITTA_richiedente";

    /**
     * Create a new routine call for <code>EliminaCentro</code>.
     */
    public EliminaCentro() {
        super("EliminaCentro");
        addInParameter(COD_PROVINCIA);
        addInParameter(COD_CITTA_);
        addInParameter(NUMERO);
        addInParameter(COD_ANIMALE);
    }

}
