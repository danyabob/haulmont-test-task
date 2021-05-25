package com.danil.sheyukhin.haulmonttesttask.dao;

import com.danil.sheyukhin.haulmonttesttask.entities.Credit;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class CreditDao implements Dao<Credit> {

    private JdbcTemplate jdbcTemplate;
    private CreditRowMapper creditRowMapper;
    private BankDao bankDao;

    CreditDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        creditRowMapper = new CreditRowMapper();
    }

    @Override
    public int create(Credit credit) {
        jdbcTemplate.update("INSERT INTO CREDIT(BANK_ID, LIMIT, PERCENTAGE) VALUES (?, ?, ?)",
                credit.getBankId(),
                credit.getLimit(),
                credit.getPercentage());
        return jdbcTemplate.queryForObject("SELECT ID FROM CREDIT WHERE BANK_ID = ? AND LIMIT = ? AND PERCENTAGE = ?", Integer.class,
                credit.getBankId(),
                credit.getLimit(),
                credit.getPercentage());
    }

    @Override
    public Credit getById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM CREDIT WHERE ID = ?", creditRowMapper, id);
    }

    @Override
    public List<Credit> getAll() {
        return jdbcTemplate.query("SELECT * FROM CREDIT", creditRowMapper);
    }

    public List<Credit> getAllByBankId(Integer bankId) {
        return jdbcTemplate.query("SELECT * FROM CREDIT WHERE BANK_ID = ?",
                creditRowMapper, bankId);
    }

    @Override
    public void update(Credit credit) {
        jdbcTemplate.update("UPDATE CREDIT SET BANK_ID = ?, LIMIT = ?, PERCENTAGE = ? WHERE ID = ?",
                credit.getBankId(),
                credit.getLimit(),
                credit.getPercentage(),
                credit.getId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM CREDIT WHERE ID = ?", id);
    }


    class CreditRowMapper implements RowMapper<Credit> {
        @Override
        public Credit mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Credit(rs.getInt("ID"),
                    rs.getInt("BANK_ID"),
                    rs.getInt("LIMIT"),
                    rs.getInt("PERCENTAGE"));
        }
    }
}
