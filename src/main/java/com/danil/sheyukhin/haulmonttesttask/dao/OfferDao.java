package com.danil.sheyukhin.haulmonttesttask.dao;

import com.danil.sheyukhin.haulmonttesttask.entities.Offer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Component
public class OfferDao implements Dao<Offer> {
    private JdbcTemplate jdbcTemplate;
    private OfferRowMapper offerRowMapper;
    private CreditDao creditDao;

    public OfferDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        offerRowMapper = new OfferRowMapper();
    }

    @Override
    public int create(Offer offer) {
        jdbcTemplate.update("INSERT INTO OFFER(CLIENT_ID, CREDIT_ID, CREDIT_SUM, PAYMENT_DATE, PAYMENT, BODY_SUM, PERCENTAGE_SUM) VALUES (?, ?, ?, ?, ?, ?, ?)",
                offer.getClientId(),
                offer.getCreditId(),
                offer.getCreditSum(),
                offer.getPaymentGraphic().getPaymentDate(),
                offer.getPaymentGraphic().getPayment(),
                offer.getPaymentGraphic().getBodySum(),
                offer.getPaymentGraphic().getPercentageSum());
        return jdbcTemplate.queryForObject("SELECT ID FROM OFFER WHERE CLIENT_ID = ? AND CREDIT_ID = ? AND CREDIT_SUM = ? AND PAYMENT_DATE = ? AND PAYMENT = ? AND BODY_SUM = ? AND PERCENTAGE_SUM = ?", Integer.class,
                offer.getClientId(),
                offer.getCreditId(),
                offer.getCreditSum(),
                offer.getPaymentGraphic().getPaymentDate(),
                offer.getPaymentGraphic().getPayment(),
                offer.getPaymentGraphic().getBodySum(),
                offer.getPaymentGraphic().getPercentageSum());
    }

    @Override
    public Offer getById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM OFFER WHERE ID = ?", offerRowMapper, id);
    }

    @Override
    public List<Offer> getAll() {
        return jdbcTemplate.query("SELECT * FROM OFFER", offerRowMapper);
    }

    @Override
    public void update(Offer offer) {
        jdbcTemplate.update("UPDATE OFFER SET CLIENT_ID = ?, CREDIT_ID = ?, CREDIT_SUM = ?, PAYMENT_DATE = ?, PAYMENT = ?, BODY_SUM = ?, PERCENTAGE_SUM = ? WHERE ID = ?",
                offer.getClientId(),
                offer.getCreditId(),
                offer.getCreditSum(),
                offer.getPaymentGraphic().getPaymentDate(),
//                new Timestamp(offer.getPaymentGraphic().getPaymentDate().getTime()),
                offer.getPaymentGraphic().getPayment(),
                offer.getPaymentGraphic().getBodySum(),
                offer.getPaymentGraphic().getPercentageSum(),
                offer.getId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM OFFER WHERE ID = ?", id);
    }

    class OfferRowMapper implements RowMapper<Offer> {
        @Override
        public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Offer(rs.getInt("ID"),
                    rs.getInt("CLIENT_ID"),
                    rs.getInt("CREDIT_ID"),
                    rs.getInt("CREDIT_SUM"),
                    rs.getDate("PAYMENT_DATE").toLocalDate(),
                    rs.getInt("PAYMENT"),
                    rs.getInt("BODY_SUM"),
                    rs.getInt("PERCENTAGE_SUM"));
        }
    }
}
