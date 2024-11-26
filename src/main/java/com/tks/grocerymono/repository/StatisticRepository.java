package com.tks.grocerymono.repository;

import com.tks.grocerymono.dto.response.bill.BestSellingProductQuantityResponse;
import com.tks.grocerymono.dto.response.statistic.StatisticResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class StatisticRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<StatisticResponse> getYearlyStatistic() {
        String query = "SELECT YEAR(last_modified_date) AS time, SUM(total_price) AS revenue" +
                " FROM bill WHERE status = 'COMPLETED'" +
                " GROUP BY YEAR(last_modified_date)" +
                " ORDER BY YEAR(last_modified_date) ASC";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(StatisticResponse.class));
    }

    public List<StatisticResponse> getMonthlyStatisticByYear(int year) {
        String query = "SELECT MONTH(last_modified_date) AS time, SUM(total_price) AS revenue" +
                " FROM bill WHERE status = 'COMPLETED' AND YEAR(last_modified_date) = " + year +
                " GROUP BY MONTH(last_modified_date)";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(StatisticResponse.class));
    }

    public List<Integer> getAllStatisticYear() {
        String query = "SELECT DISTINCT YEAR(last_modified_date) FROM bill ORDER BY YEAR(last_modified_date) ASC";
        return jdbcTemplate.queryForList(query, Integer.TYPE);
    }

    public List<StatisticResponse> getDailyStatisticByMonth(String month) {
        String query = "SELECT DAY(last_modified_date) AS time, SUM(total_price) AS revenue" +
                " FROM bill WHERE status = 'COMPLETED' AND DATE_FORMAT(last_modified_date, '%m-%Y') = '" + month +
                "' GROUP BY DAY(last_modified_date)";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(StatisticResponse.class));
    }

    public List<BestSellingProductQuantityResponse> getBestSellingProductQuantityList(int recentDays, int size) {
        String query = "SELECT bi.product_id AS id, COUNT(bi.product_id) AS quantity " +
                "FROM bill b JOIN bill_item bi ON b.id = bi.bill_id " +
                "WHERE b.created_date >= DATE_SUB(CURDATE(), INTERVAL " + recentDays + " DAY) " +
                "GROUP BY bi.product_id " +
                "ORDER BY COUNT(bi.product_id) DESC " +
                "LIMIT " + size;
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(BestSellingProductQuantityResponse.class));
    }
}
