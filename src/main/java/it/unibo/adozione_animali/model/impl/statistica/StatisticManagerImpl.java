package it.unibo.adozione_animali.model.impl.statistica;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import it.unibo.adozione_animali.model.api.statistica.ConcatDato;
import it.unibo.adozione_animali.model.api.statistica.StatisticManager;
import it.unibo.adozione_animali.util.DBConfig;
import it.unibo.adozione_animali.util.Enum.NomeStatistica;
import nu.studer.sample.Tables;
import nu.studer.sample.tables.records.DatoRecord;
import nu.studer.sample.tables.records.StatisticaRecord;

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

    @Override
    public String getStatistic(String codiceStat, LocalDate dataStat) {
        try(Connection conn = DBConfig.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            StatisticaRecord stat = ctx.selectFrom(Tables.STATISTICA)
                    .where(Tables.STATISTICA.CODICE.eq(codiceStat))
                    .and(Tables.STATISTICA.DATA_STATISTICA.eq(dataStat))
                    .fetchOne();

            if (stat == null) {
                return "Nessuna statistica trovata.";
            }

            // Recupero i dati collegati
            List<DatoRecord> dati = ctx.selectFrom(Tables.DATO)
                    .where(Tables.DATO.COD_STATISTICA.eq(codiceStat))
                    .and(Tables.DATO.DATA_STATISTICA.eq(dataStat))
                    .fetch();

            StringBuilder sb = new StringBuilder("<html>");

            sb.append("<b>Codice Statistica:</b> <font color='blue'>")
            .append(stat.getCodice())
            .append("</font><br>")
            .append("<b>Nome Statistica:</b> <font color='green'>")
            .append(stat.getNome())
            .append("</font><br>")
            .append("<b>Data Statistica:</b> <font color='red'>")
            .append(stat.getDataStatistica().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
            .append("</font><br><br>");

            if (dati.size() > 1) {
                sb.append("<b>Dati:</b><ul>");
            } else {
                sb.append("<b>Dato:</b><ul>");
            }

            for (DatoRecord dato : dati) {
                sb.append("<li>")
                .append("<b>Codice:</b> <font color='blue'>").append(dato.getCodice()).append("</font> | ")
                .append("<b>Nome:</b> <font color='green'>").append(dato.getNome()).append("</font> | ")
                .append("<b>Valore:</b> <font color='red'>").append(dato.getValore()).append("</font>")
                .append("</li>");
            }

            sb.append("</ul></html>");
            return sb.toString();
        } catch (Exception e) {
            return "Error nel Caricamento";
        }
    }
}
