package com.sea.template.Repository;

import org.slf4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

public class JDBCOrderbookRepository {

    private static final Logger log = getLogger(JDBCOrderbookRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JDBCOrderbookRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public int persistRecord(String instrumentSpotDepthRecord, String recordStatus) {
        final String sql = "INSERT INTO raw_spot_depth (id, message, status) VALUES (:id, :message, :status)";
        final MapSqlParameterSource params = new MapSqlParameterSource();

        final UUID recordId = UUID.randomUUID();
        params.addValue("id", recordId);
        params.addValue("message", instrumentSpotDepthRecord);
        params.addValue("status", recordStatus);

        try {
            log.info("Persisting record - id: <{}> msg: <{}>", recordId, instrumentSpotDepthRecord);
            int success = jdbcTemplate.update(sql, params);
            log.info("Persisted record <{}>", recordId);
            return success;
        } catch (Exception e) {
            log.error("Unable to persist record:<{}>", recordId);
            e.printStackTrace();
            return 0;
        }
    }

    static class GuestRowMapper implements RowMapper<Guest> {
        @Override
        public Guest mapRow(ResultSet rs, int rowNum) throws SQLException {
                return Guest.builder()
                        .id(rs.getString("id"))
                        .name(rs.getString("name"))
                        .build();

        }
    }
}
