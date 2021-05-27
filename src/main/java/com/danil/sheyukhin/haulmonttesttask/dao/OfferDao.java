/*
 * Copyright (c) 2021.
 * Danil Sheyukhin
 * danya.bob@gmail.com
 */

package com.danil.sheyukhin.haulmonttesttask.dao;

import com.danil.sheyukhin.haulmonttesttask.entities.Offer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class OfferDao implements Dao<Offer> {
    private JdbcTemplate jdbcTemplate;
    private OfferRowMapper offerRowMapper;

    public OfferDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        offerRowMapper = new OfferRowMapper();
    }

    @Override
    public int create(Offer offer) {
        if (offer == null) {
            throw new RuntimeException();
        }
        jdbcTemplate.update("INSERT INTO OFFER(CLIENT_ID, CREDIT_ID, SUMMA, DURATION) VALUES (?, ?, ?, ?)",
                offer.getClientId(),
                offer.getCreditId(),
                offer.getSumma(),
                offer.getDuration());
        return jdbcTemplate.queryForObject("SELECT ID FROM OFFER WHERE CLIENT_ID = ? AND CREDIT_ID = ? AND SUMMA = ? AND DURATION = ?", Integer.class,
                offer.getClientId(),
                offer.getCreditId(),
                offer.getSumma(),
                offer.getDuration());
    }

    @Override
    public Offer getById(Integer id) {
        if (id == null) {
            throw new RuntimeException();
        }
        return jdbcTemplate.queryForObject("SELECT * FROM OFFER WHERE ID = ?", offerRowMapper, id);
    }

    @Override
    public List<Offer> getAll() {
        return jdbcTemplate.query("SELECT * FROM OFFER", offerRowMapper);
    }

    @Override
    public void update(Offer offer) {
        if (offer == null) {
            throw new RuntimeException();
        }
        jdbcTemplate.update("UPDATE OFFER SET CLIENT_ID = ?, CREDIT_ID = ?, SUMMA = ?, DURATION = ? WHERE ID = ?",
                offer.getClientId(),
                offer.getCreditId(),
                offer.getSumma(),
                offer.getDuration(),
                offer.getId());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            throw new RuntimeException();
        }
        jdbcTemplate.update("DELETE FROM OFFER WHERE ID = ?", id);
    }

    class OfferRowMapper implements RowMapper<Offer> {
        @Override
        public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Offer(rs.getInt("ID"),
                    rs.getInt("CLIENT_ID"),
                    rs.getInt("CREDIT_ID"),
                    rs.getInt("SUMMA"),
                    rs.getInt("DURATION"));
        }
    }
}
