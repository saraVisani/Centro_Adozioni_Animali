package it.unibo.adozione_animali.model.impl.statistica;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import it.unibo.adozione_animali.model.api.statistica.ConcatDato;
import it.unibo.adozione_animali.model.api.statistica.StatisticManager;
import it.unibo.adozione_animali.util.DBConfig;
import it.unibo.adozione_animali.util.Enum.NomeStatistica;
import nu.studer.sample.Tables;

public class StatisticManagerImpl implements StatisticManager{

    private final ConcatDato concatDato = new ConcatDatoImpl();

    @Override
    public boolean insertStatistica(String codice, String nome, LocalDate data) {
        if (codice == null || nome == null || data == null) {
            return false;
        }

        if(NomeStatistica.isValidDescrizione(nome)) {
            nome = NomeStatistica.getDescrizioneFrom(nome);
            int annoCorrente = LocalDate.now().getYear();
            if(data.getYear() < annoCorrente) {
                return false;
            }
            StatisticaDAO statisticaDAO = new StatisticaDAO();
            return statisticaDAO.insertStatistica(codice, data, nome);
        } else {
            return false;
        }
    }

    @Override
    public boolean insertDato(String codice, List<String> nome, List<String> valore, String statistica, LocalDate data) {
        if (codice == null || nome == null || nome.isEmpty() || valore == null
                || valore.isEmpty() || statistica == null || data == null) {
            return false;
        }
        String realName;
        String realValue;
        String realNameStat;

        try(Connection conn = DBConfig.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            realNameStat = ctx.select(Tables.STATISTICA.NOME)
                                .from(Tables.STATISTICA)
                                .where(Tables.STATISTICA.CODICE.eq(statistica))
                                .and(Tables.STATISTICA.DATA_STATISTICA.eq(data))
                                .fetchOneInto(String.class);
            if(!NomeStatistica.isValidDescrizione(realNameStat)){
                return false;
            }else{
                realNameStat = NomeStatistica.getDescrizioneFrom(realNameStat);
            }
        } catch (Exception e) {
            return false;
        }

        try {
            realName = concatDato.concatNome(nome, realNameStat);
            realValue = concatDato.concatValore(valore, realNameStat);
            if (realName == null || realValue == null) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        DatoDAO datoDAO = new DatoDAO();
        if (!datoDAO.insertDato(codice, realName, realValue, statistica, data)) {
            return false;
        }
        return true;
    }
}
