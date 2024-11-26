package com.tks.grocerymono.controller;


import com.tks.grocerymono.base.dto.response.BaseResponse;
import com.tks.grocerymono.base.dto.response.ResponseFactory;
import com.tks.grocerymono.dto.request.statistic.GetAllStatisticYearRequest;
import com.tks.grocerymono.dto.request.statistic.GetDailyStatisticByMonthRequest;
import com.tks.grocerymono.dto.request.statistic.GetMonthlyStatisticByYearRequest;
import com.tks.grocerymono.dto.request.statistic.GetYearlyStatisticRequest;
import com.tks.grocerymono.dto.response.statistic.GetAllStatisticYearResponse;
import com.tks.grocerymono.dto.response.statistic.GetDailyStatisticByMonthResponse;
import com.tks.grocerymono.dto.response.statistic.GetMonthlyStatisticByYearResponse;
import com.tks.grocerymono.dto.response.statistic.GetYearlyStatisticResponse;
import com.tks.grocerymono.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/statistic")
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticService statisticService;
    private final ResponseFactory responseFactory;

    @GetMapping("/year/all")
    ResponseEntity<BaseResponse<GetAllStatisticYearResponse>> getAllStatisticYear() {
        return responseFactory.success(statisticService.getAllStatisticYear(new GetAllStatisticYearRequest()));
    }

    @GetMapping("/daily")
    ResponseEntity<BaseResponse<GetDailyStatisticByMonthResponse>> getDailyStatisticByMonth(
            @RequestParam("month") String month
    ) {
        return  responseFactory.success(statisticService.getDailyStatisticByMonth(new GetDailyStatisticByMonthRequest(month)));
    }

    @GetMapping("/monthly/{year}")
    ResponseEntity<BaseResponse<GetMonthlyStatisticByYearResponse>> getMonthlyStatisticByYear(
            @PathVariable("year") int year
    ) {
        return  responseFactory.success(statisticService.getMonthlyStatisticByYear(new GetMonthlyStatisticByYearRequest(year)));
    }


    @GetMapping("/yearly")
    ResponseEntity<BaseResponse<GetYearlyStatisticResponse>> getYearlyStatistic() {
        return  responseFactory.success(statisticService.getYearlyStatistic(new GetYearlyStatisticRequest()));
    }
}
