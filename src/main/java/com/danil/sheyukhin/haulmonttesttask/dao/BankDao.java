package com.danil.sheyukhin.haulmonttesttask.dao;

import com.danil.sheyukhin.haulmonttesttask.entities.Bank;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class BankDao implements Dao<Bank> {

    private CreditDao creditDao;
    private ClientDao clientDao;
    private JdbcTemplate jdbcTemplate;

    BankDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int create(Bank object) {
        return 0;
    }

    @Override
    public void update(Bank object) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Bank getById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM BANK WHERE ID = ?", new BankRowMapper(), id);
    }

    @Override
    public List<Bank> getAll() {
        return jdbcTemplate.query("SELECT * FROM BANK", new BankRowMapper());
    }

    static class BankRowMapper implements RowMapper<Bank> {
        @Override
        public Bank mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Bank(resultSet.getInt("ID"),
                    resultSet.getString("NAME"));
        }
    }
}
