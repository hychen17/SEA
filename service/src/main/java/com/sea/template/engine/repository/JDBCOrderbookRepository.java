package com.sea.template.engine.repository;

import com.sea.template.lang.JsonParser;
import com.sea.template.lang.RawRecordStatus;
import com.sea.template.model.ModelUtils;
import com.sea.template.model.SpotDepthRawRecord;
import com.sea.template.model.SpotDepthRecord;
import org.slf4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.annotation.Nullable;
import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;

public class JDBCOrderbookRepository implements OkexTradeDataRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String PERSIST_SQL = "INSERT INTO raw_spot_depth (id, instrument, message, status) VALUES (:id, :instrument, :message, :status)";
    private static final String GET_BY_STATUS_SQL = "SELECT * FROM raw_spot_depth WHERE status = :status";
    private static final String GET_BY_INSTRUMENT_SQL = "SELECT * FROM raw_spot_depth WHERE instrument = :instrument";
    private static final String UPDATE_STATUS_SQL = "UPDATE raw_spot_depth SET status = :status WHERE id = :id";

    private static final Logger log = getLogger(JDBCOrderbookRepository.class);

    public JDBCOrderbookRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public int persistRecord(String instrumentSpotDepthRecord, String instrument, String recordStatus) {
        final MapSqlParameterSource params = new MapSqlParameterSource();

        final UUID recordId = UUID.randomUUID();
        params.addValue("id", recordId);
        params.addValue("instrument", instrument);
        params.addValue("message", instrumentSpotDepthRecord);
        params.addValue("status", recordStatus);

        try {
            log.info("Persisting record - id: <{}> msg: <{}>", recordId, instrumentSpotDepthRecord);
            int success = jdbcTemplate.update(PERSIST_SQL, params);
            log.info("Persisted record <{}>", recordId);
            return success;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateRecord(String recordId, String recordStatus) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", recordId);
        params.addValue("status", recordStatus);

        try {
            log.info("Updating record - id: <{}> status: <{}>", recordId, recordStatus);
            int success = jdbcTemplate.update(UPDATE_STATUS_SQL, params);
            log.info("Updated record <{}> to <{}>", recordId, recordStatus);
            return success;
        } catch (Exception e) {
            log.error("Unable to update record - id: <{}> to status: <{}>", recordId, recordStatus);
            e.printStackTrace();
            return 0;
        }


    }

    public List<SpotDepthRecord> getRecordsByStatus(String status) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("status", status);

        log.info("Fetching {} record(s)", status);
        List<SpotDepthRecord> records = jdbcTemplate.query(GET_BY_STATUS_SQL, params, new SpotDepthRecordRowMapper());
        log.info("Fetched record(s) <{}>", records.stream().map(r -> r.getId()).collect(toList()));

        return records;
    }

    public List<SpotDepthRecord> getRecordsByInstruments(String instrument) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("instrument", instrument);

        log.info("Fetching {} record(s)", instrument);
        List<SpotDepthRecord> records = jdbcTemplate.query(GET_BY_INSTRUMENT_SQL, params, new SpotDepthRecordRowMapper());
        log.info("Fetched record(s) <{}>", records.stream().map(r -> r.getId()).collect(toList()));


        return records;
    }

    static class SpotDepthRecordRowMapper implements RowMapper<SpotDepthRecord> {
        @Override
        public SpotDepthRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
            String recordBody = rs.getString("message");
            try {
                SpotDepthRawRecord rawRecord = ModelUtils.fromMap(JsonParser.fromJson2Map(recordBody));

                return SpotDepthRecord.builder()
                        .id(rs.getString("id"))
                        .instrument(rs.getString("instrument"))
                        .asks(rawRecord.getAsks())
                        .bids(rawRecord.getBids())
                        .timestamp(rawRecord.getTimestamp())
                        .status(rs.getString("status"))
                        .build();
            } catch (IOException e) {
                log.error("Unable to parse to object: {}", recordBody);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }


}
