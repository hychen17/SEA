package com.sea.template.guest;

import org.slf4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

public class JDBCGuestRepo {

    private static final Logger log = getLogger(JDBCGuestRepo.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JDBCGuestRepo(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Guest getGuest(String name) {
        final String sql = "SELECT * FROM samples WHERE name = :name";
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);

        return jdbcTemplate.queryForObject(sql, params, new GuestRowMapper());
    }


    public int persistGuest(String name) {
        final String sql = "INSERT INTO samples (id, name) VALUES (:id, :name)";
        final MapSqlParameterSource params = new MapSqlParameterSource();
        final UUID guestId = UUID.randomUUID();
        log.info("Assing id [{}] to guest [{}]", guestId, name);
        params.addValue("id", guestId);
        params.addValue("name", name);
        try {
            int success = jdbcTemplate.update(sql, params);
            log.info("Successfully created guest:[{}]", name);
            return success;
        } catch (RuntimeException e) {
            log.error("Unable to create guest:[{}]", name);
            e.printStackTrace();
            throw e;
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
