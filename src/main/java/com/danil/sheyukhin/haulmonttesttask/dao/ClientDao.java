package com.danil.sheyukhin.haulmonttesttask.dao;

import com.danil.sheyukhin.haulmonttesttask.entities.Client;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class ClientDao implements Dao<Client> {

    private JdbcTemplate jdbcTemplate;
    private ClientRowMapper clientRowMapper;

    ClientDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        clientRowMapper = new ClientRowMapper();
    }

    @Override
    public int create(Client client) {
        jdbcTemplate.update("INSERT INTO CLIENT(NAME, PHONE, EMAIL, PASSPORT, BANK_ID) VALUES (?, ?, ?, ?, ?)",
                client.getName(),
                client.getPhone(),
                client.getEmail(),
                client.getPassport(),
                client.getBankId());
        return jdbcTemplate.queryForObject("SELECT ID FROM CLIENT WHERE NAME = ? AND PHONE = ? AND EMAIL = ? AND PASSPORT = ? AND BANK_ID = ?", Integer.class,
                client.getName(),
                client.getPhone(),
                client.getEmail(),
                client.getPassport(),
                client.getBankId());
    }

//    @Override
//    public int create(Client client) {
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        jdbcTemplate.update(psc -> {
//            PreparedStatement ps = psc.prepareStatement("INSERT INTO CLIENT(NAME, PHONE, EMAIL, PASSPORT, BANK_ID) VALUES (?, ?, ?, ?, ?)");
//            ps.setString(1, client.getName());
//            ps.setString(2, client.getPhone());
//            ps.setString(3, client.getEmail());
//            ps.setString(4, client.getPassport());
//            ps.setInt(5, client.getBankId());
//            return ps;
//        }, keyHolder);
//        return (int)keyHolder.getKey();
//    }

    @Override
    public Client getById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM CLIENT WHERE ID = ?", clientRowMapper, id);
    }

    @Override
    public List<Client> getAll() {
        return jdbcTemplate.query("SELECT * FROM CLIENT", clientRowMapper);
    }

    @Override
    public void update(Client client) {
        jdbcTemplate.update("UPDATE CLIENT SET NAME = ?, PHONE = ?, EMAIL = ?, PASSPORT = ?, BANK_ID = ? WHERE ID = ?",
                client.getName(),
                client.getPhone(),
                client.getEmail(),
                client.getPassport(),
                client.getBankId(),
                client.getId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM CLIENT WHERE ID = ?", id);
    }

    static class ClientRowMapper implements RowMapper<Client> {
        @Override
        public Client mapRow(ResultSet resultSet, int i) throws SQLException {
            Client client = new Client(resultSet.getString("NAME"),
                    resultSet.getString("PHONE"),
                    resultSet.getString("EMAIL"),
                    resultSet.getString("PASSPORT"));
            client.setId(resultSet.getInt("ID"));
            client.setBankId(resultSet.getInt("BANK_ID"));
            return client;
        }
    }
}
