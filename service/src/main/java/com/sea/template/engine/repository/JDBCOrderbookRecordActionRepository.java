package com.sea.template.engine.repository;

import org.slf4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.transaction.Transactional;

import static org.slf4j.LoggerFactory.getLogger;

public class JDBCOrderbookRecordActionRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final Logger log = getLogger(JDBCOrderbookRecordActionRepository.class);

    private static final String PERSIST_SQL = "INSERT INTO processed_record_and_action (id, action, timestamp) VALUES (:id, :action, :timestamp)";

    public JDBCOrderbookRecordActionRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public int persistRecord(String recordId, String action, String timestamp) {
        final MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("id", recordId);
        params.addValue("action", action);
        params.addValue("timestamp", timestamp);

        try {
            log.info("Persisting record - id: <{}> acion: <{}>", recordId, action);
            int success = jdbcTemplate.update(PERSIST_SQL, params);
            log.info("Persisted record <{}>", recordId);
            return success;
        } catch (DuplicateKeyException e) {
            log.error("Unable to persist record:<{}> as it exists", recordId);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
