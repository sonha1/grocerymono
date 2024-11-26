package com.tks.grocerymono.service.impl;

import com.tks.grocerymono.dto.request.statistic.GetAllStatisticYearRequest;
import com.tks.grocerymono.dto.request.statistic.GetDailyStatisticByMonthRequest;
import com.tks.grocerymono.dto.request.statistic.GetMonthlyStatisticByYearRequest;
import com.tks.grocerymono.dto.request.statistic.GetYearlyStatisticRequest;
import com.tks.grocerymono.dto.response.statistic.*;
import com.tks.grocerymono.repository.StatisticRepository;
import com.tks.grocerymono.service.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final StatisticRepository statisticRepository;

    @Override
    public GetAllStatisticYearResponse getAllStatisticYear(GetAllStatisticYearRequest requestData) {
        List<Integer> yearList = statisticRepository.getAllStatisticYear()
                .stream()
                .filter(Objects::nonNull)
                .toList();
        return new GetAllStatisticYearResponse(yearList);
    }

    @Override
    public GetDailyStatisticByMonthResponse getDailyStatisticByMonth(GetDailyStatisticByMonthRequest requestData) {
        List<StatisticResponse> statisticResponseList = statisticRepository.getDailyStatisticByMonth(requestData.getMonth());
        return new GetDailyStatisticByMonthResponse(statisticResponseList);
    }

    @Override
    public GetMonthlyStatisticByYearResponse getMonthlyStatisticByYear(GetMonthlyStatisticByYearRequest requestData) {
        List<StatisticResponse> statisticResponseList = statisticRepository.getMonthlyStatisticByYear(requestData.getYear());
        return new GetMonthlyStatisticByYearResponse(statisticResponseList);
    }

    @Override
    public GetYearlyStatisticResponse getYearlyStatistic(GetYearlyStatisticRequest requestData) {
        List<StatisticResponse> statisticResponseList = statisticRepository.getYearlyStatistic();
        return new GetYearlyStatisticResponse(statisticResponseList);
    }

}
