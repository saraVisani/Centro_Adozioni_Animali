package it.unibo.adozione_animali.model.impl.caratteristica;

import java.util.List;
import java.util.Optional;
import java.sql.Connection;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import it.unibo.adozione_animali.model.api.caratteristica.CaratteristicaPersonale;
import it.unibo.adozione_animali.util.DBConfig;
import it.unibo.adozione_animali.util.Enum.TipoCaratteristica;
import nu.studer.sample.Tables;

public class CaratteristichePersonaliDAO implements CaratteristicaPersonale{

    @Override
    public boolean insertCaratteristicaPersonale(String provincia, String città, int numero, String animale,
            String tipo, Optional<String> codice) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            int righeInserite = ctx.insertInto(Tables.CARATTERISTICA_PERSONALE)
                .set(Tables.CARATTERISTICA_PERSONALE.COD_PROVINCIA, provincia)
                .set(Tables.CARATTERISTICA_PERSONALE.COD_CITTA_, città)
                .set(Tables.CARATTERISTICA_PERSONALE.NUMERO, numero)
                .set(Tables.CARATTERISTICA_PERSONALE.COD_ANIMALE, animale)
                .set(Tables.CARATTERISTICA_PERSONALE.TIPO_CAR_PERSONALE, tipo)
                .set(Tables.CARATTERISTICA_PERSONALE.CODICE, codice.orElse(null))
                .execute();
            return righeInserite > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean ChangeCodiceCaratteristica(String provincia, String città, int numero, String animale, String tipo,
            String Newcodice) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            int righeAggiornate = ctx.update(Tables.CARATTERISTICA_PERSONALE)
                .set(Tables.CARATTERISTICA_PERSONALE.CODICE, Newcodice)
                .where(Tables.CARATTERISTICA_PERSONALE.COD_PROVINCIA.eq(provincia)
                    .and(Tables.CARATTERISTICA_PERSONALE.COD_CITTA_.eq(città))
                    .and(Tables.CARATTERISTICA_PERSONALE.NUMERO.eq(numero))
                    .and(Tables.CARATTERISTICA_PERSONALE.COD_ANIMALE.eq(animale))
                    .and(Tables.CARATTERISTICA_PERSONALE.TIPO_CAR_PERSONALE.eq(tipo)))
                .execute();
            return righeAggiornate > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteCaratteristicaPersonale(String provincia, String città, int numero, String animale,
            String tipo) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            int righeCancellate = ctx.deleteFrom(Tables.CARATTERISTICA_PERSONALE)
                .where(Tables.CARATTERISTICA_PERSONALE.COD_PROVINCIA.eq(provincia)
                    .and(Tables.CARATTERISTICA_PERSONALE.COD_CITTA_.eq(città))
                    .and(Tables.CARATTERISTICA_PERSONALE.NUMERO.eq(numero))
                    .and(Tables.CARATTERISTICA_PERSONALE.COD_ANIMALE.eq(animale))
                    .and(Tables.CARATTERISTICA_PERSONALE.TIPO_CAR_PERSONALE.eq(tipo)))
                .execute();
            return righeCancellate > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getTipi(String provincia, String citta, String numero, String codAnimale, boolean nuovo) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            // Count delle caratteristiche già presenti
            Integer count = ctx.selectCount()
                    .from(Tables.CARATTERISTICA_PERSONALE)
                    .where(Tables.CARATTERISTICA_PERSONALE.COD_PROVINCIA.eq(provincia))
                    .and(Tables.CARATTERISTICA_PERSONALE.COD_CITTA_.eq(citta))
                    .and(Tables.CARATTERISTICA_PERSONALE.NUMERO.eq(Integer.valueOf(numero)))
                    .and(Tables.CARATTERISTICA_PERSONALE.COD_ANIMALE.eq(codAnimale))
                    .fetchOne(0, Integer.class);

            if (nuovo && count != null && count >= 2) {
                // Se nuovo e ci sono già due caratteristiche, ritorna vuoto
                return List.of();
            }

            // Lista completa dei tipi possibili
            List<String> tuttiTipi = List.of(TipoCaratteristica.COMPORTAMENTO.name(), TipoCaratteristica.FISICO.name());

            // Tipi già presenti
            List<String> presenti = ctx.select(Tables.CARATTERISTICA_PERSONALE.TIPO_CAR_PERSONALE)
                    .from(Tables.CARATTERISTICA_PERSONALE)
                    .where(Tables.CARATTERISTICA_PERSONALE.COD_PROVINCIA.eq(provincia))
                    .and(Tables.CARATTERISTICA_PERSONALE.COD_CITTA_.eq(citta))
                    .and(Tables.CARATTERISTICA_PERSONALE.NUMERO.eq(Integer.valueOf(numero)))
                    .and(Tables.CARATTERISTICA_PERSONALE.COD_ANIMALE.eq(codAnimale))
                    .fetchInto(String.class);

            // Restituisci solo i tipi mancanti
            return tuttiTipi.stream()
                    .filter(tipo -> !presenti.contains(tipo))
                    .toList();

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public boolean insertMultiplo(String provincia, String citta, Integer numero, String codAnimale,
            String tipo, List<String> codiciSpecifici) {

        try (Connection conn = DBConfig.getConnection()) {
            // Recupera i tipi di tutti i codici in un'unica query
            List<ItemCodiceTipo> codiciConTipo = codiciSpecifici.stream()
                .map(codice -> {
                    String tipoCod = DSL.using(conn, SQLDialect.MYSQL)
                        .select(Tables.SPECIFICA_RAZZA.TIPO_CAR_GENERALE)
                        .from(Tables.SPECIFICA_RAZZA)
                        .where(Tables.SPECIFICA_RAZZA.CODICE.eq(codice))
                        .fetchOne(Tables.SPECIFICA_RAZZA.TIPO_CAR_GENERALE);
                    return new ItemCodiceTipo(codice, tipoCod);
                })
                .toList();

            // Trova il primo codice di tipo Colorazione
            Optional<ItemCodiceTipo> firstColorazione = codiciConTipo.stream()
                .filter(c -> "Colorazione".equals(c.tipo))
                .findFirst();

            if (firstColorazione.isPresent()) {
                insertCaratteristicaPersonale(provincia, citta, numero, codAnimale, tipo,
                                            Optional.of(firstColorazione.get().codice));
            } else {
                insertCaratteristicaPersonale(provincia, citta, numero, codAnimale, tipo, Optional.empty());
            }

            // Tutti gli altri codici vanno in Appartenenza
            List<String> codiciAppartenenza = codiciConTipo.stream()
                .filter(c -> firstColorazione.isEmpty() || !c.codice.equals(firstColorazione.get().codice))
                .map(c -> c.codice)
                .toList();

            if (!codiciAppartenenza.isEmpty()) {
                new AppartenenzaDAO().insertAppartenenza(codiciAppartenenza, provincia, citta, numero, codAnimale, tipo);
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static class ItemCodiceTipo {
        String codice;
        String tipo;
        ItemCodiceTipo(String codice, String tipo) { this.codice = codice; this.tipo = tipo; }
    }

    public boolean controlForUpdate(String provincia, String citta, Integer valueOf, String codAnimale, String tipo,
            String newCod) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'controlForUpdate'");
    }
}
