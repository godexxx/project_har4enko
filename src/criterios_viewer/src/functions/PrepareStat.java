package src.functions;

import java.sql.PreparedStatement;

/**
 * Created by AlxEx on 23.12.2015.
 */
public enum PrepareStat {
    SELECT_ALL_FROM_SESSION("SELECT * FROM SESSION"), SELECT_FROM_MARK("SELECT * FROM MARK WHERE SESSION_ID = ?"),
    SELECT_FROM_CRITERION("SELECT * FROM CRITERION WHERE ID = ?");

    private String prepareStat;

    PrepareStat(String prepareStat) {
        this.prepareStat = prepareStat;
    }

    public String getPrepareStat() {
        return prepareStat;
    }
}