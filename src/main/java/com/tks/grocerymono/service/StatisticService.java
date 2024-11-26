package com.tks.grocerymono.service;

import com.tks.grocerymono.dto.request.statistic.GetAllStatisticYearRequest;
import com.tks.grocerymono.dto.request.statistic.GetDailyStatisticByMonthRequest;
import com.tks.grocerymono.dto.request.statistic.GetMonthlyStatisticByYearRequest;
import com.tks.grocerymono.dto.request.statistic.GetYearlyStatisticRequest;
import com.tks.grocerymono.dto.response.statistic.GetAllStatisticYearResponse;
import com.tks.grocerymono.dto.response.statistic.GetDailyStatisticByMonthResponse;
import com.tks.grocerymono.dto.response.statistic.GetMonthlyStatisticByYearResponse;
import com.tks.grocerymono.dto.response.statistic.GetYearlyStatisticResponse;

public interface StatisticService {
    GetAllStatisticYearResponse getAllStatisticYear(GetAllStatisticYearRequest requestData);

    GetDailyStatisticByMonthResponse getDailyStatisticByMonth(GetDailyStatisticByMonthRequest requestData);

    GetMonthlyStatisticByYearResponse getMonthlyStatisticByYear(GetMonthlyStatisticByYearRequest requestData);

    GetYearlyStatisticResponse getYearlyStatistic(GetYearlyStatisticRequest requestData);
}
