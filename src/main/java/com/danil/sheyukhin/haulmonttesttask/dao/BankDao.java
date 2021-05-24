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

    private JdbcTemplate jdbcTemplate;
    private BankRowMapper bankRowMapper;
    private ClientDao clientDao;
    private CreditDao creditDao;

    BankDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        bankRowMapper = new BankRowMapper();
    }

    @Override
    public int create(Bank bank) {
        jdbcTemplate.update("INSERT INTO BANK(NAME) VALUES (?)",
                bank.getName());
        return jdbcTemplate.queryForObject("SELECT ID FROM BANK WHERE NAME = ?", Integer.class,
                bank.getName());
    }

    @Override
    public Bank getById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM BANK WHERE ID = ?", bankRowMapper, id);
    }

    @Override
    public List<Bank> getAll() {
        return jdbcTemplate.query("SELECT * FROM BANK", bankRowMapper);
    }

    @Override
    public void update(Bank bank) {
        jdbcTemplate.update("UPDATE BANK SET NAME = ? WHERE ID = ?",
                bank.getName(),
                bank.getId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM BANK WHERE ID = ?", id);
    }

    class BankRowMapper implements RowMapper<Bank> {
        @Override
        public Bank mapRow(ResultSet resultSet, int i) throws SQLException {
            clientDao = new ClientDao(jdbcTemplate.getDataSource());
            creditDao = new CreditDao(jdbcTemplate.getDataSource());
            return new Bank(resultSet.getInt("ID"),
                    resultSet.getString("NAME"));
        }
    }
}
