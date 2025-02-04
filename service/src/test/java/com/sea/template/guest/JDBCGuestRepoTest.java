package com.sea.template.guest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoRule;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.junit.MockitoJUnit.rule;

public class JDBCGuestRepoTest {

    @Rule
    public MockitoRule rule = rule();

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    private String name = "Tom";
    private JDBCGuestRepo unit;


    @Before
    public void setup() {
        unit = new JDBCGuestRepo(jdbcTemplate);
    }

    @Test
    public void should_query() {
        unit.getGuest(name);
        verify(jdbcTemplate).queryForObject(any(),any(MapSqlParameterSource.class),any(RowMapper.class));
    }
}
