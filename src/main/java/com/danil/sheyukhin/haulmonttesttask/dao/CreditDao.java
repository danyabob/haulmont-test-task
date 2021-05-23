package com.danil.sheyukhin.haulmonttesttask.dao;

import com.danil.sheyukhin.haulmonttesttask.entities.Credit;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class CreditDao {

    private JdbcTemplate jdbcTemplate;
    CreditDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    Credit getCreditById(int id) {
        String name = jdbcTemplate.queryForObject("SELECT NAME FROM CREDIT WHERE ID = '" + id + "'", String.class);
        Integer limit = jdbcTemplate.queryForObject("SELECT LIMIT FROM CREDIT WHERE ID = '" + id + "'", Integer.class);
        Integer percent = jdbcTemplate.queryForObject("SELECT PERCENT FROM CREDIT WHERE ID = '" + id + "'", Integer.class);
        return new Credit(name, limit, percent);
    }
}
