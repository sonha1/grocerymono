package com.tks.grocerymono.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateTimeFormatUtil {

    private static final Map<String, DateTimeFormatter> DATE_TIME_FORMATTER_MAP = new HashMap<>();
    private static final Map<String, SimpleDateFormat> SIMPLE_DATE_FORMAT_MAP = new HashMap<>();

    /* pattern MM/yyyy */
    private static final DateTimeFormatter DATE_TIME_FORMATTER_MM_SLASH_yyyy
            = DateTimeFormatter.ofPattern(DateTimeFormat.MM_SLASH_yyyy.getFormat());
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT_MM_SLASH_yyyy
            = new SimpleDateFormat(DateTimeFormat.MM_SLASH_yyyy.getFormat());

    /* pattern dd/MM/yyyy HHmmss */
    private static final DateTimeFormatter DATE_TIME_FORMATTER_dd_SLASH_MM_SLASH_yyyy_SPACE_HH_mm_ss
            = DateTimeFormatter.ofPattern(DateTimeFormat.dd_SLASH_MM_SLASH_yyyy_SPACE_HH_mm_ss.getFormat());
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT_dd_SLASH_MM_SLASH_yyyy_SPACE_HH_mm_ss
            = new SimpleDateFormat(DateTimeFormat.dd_SLASH_MM_SLASH_yyyy_SPACE_HH_mm_ss.getFormat());

    /* pattern dd/MM/yyyy HH:mm:ss */
    private static final DateTimeFormatter DATE_TIME_FORMATTER_dd_SLASH_MM_SLASH_yyyy_SPACE_HH_COLON_mm_COLON_ss
            = DateTimeFormatter.ofPattern(DateTimeFormat.dd_SLASH_MM_SLASH_yyyy_SPACE_HH_COLON_mm_COLON_ss.getFormat());
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT_dd_SLASH_MM_SLASH_yyyy_SPACE_HH_COLON_mm_COLON_ss
            = new SimpleDateFormat(DateTimeFormat.dd_SLASH_MM_SLASH_yyyy_SPACE_HH_COLON_mm_COLON_ss.getFormat());


    /* pattern dd/MM/yyyy */
    private static final DateTimeFormatter DATE_TIME_FORMATTER_dd_SLASH_MM_SLASH_yyyy
            = DateTimeFormatter.ofPattern(DateTimeFormat.dd_SLASH_MM_SLASH_yyyy.getFormat());
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT_dd_SLASH_MM_SLASH_yyyy
            = new SimpleDateFormat(DateTimeFormat.dd_SLASH_MM_SLASH_yyyy.getFormat());

    /* pattern yyyyMMdd HHmmss */
    private static final DateTimeFormatter DATE_TIME_FORMATTER_yyyyMMdd_SPACE_HHmmss
            = DateTimeFormatter.ofPattern(DateTimeFormat.yyyyMMdd_SPACE_HHmmss.getFormat());
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT_yyyyMMdd_SPACE_HHmmss
            = new SimpleDateFormat(DateTimeFormat.yyyyMMdd_SPACE_HHmmss.getFormat());

    /* pattern yyyy-MM-dd HH:mm:ss */
    private static final DateTimeFormatter DATE_TIME_FORMATTER_dd_DASH_MM_DASH_yyyy_SPACE_HH_COLON_mm_COLON_ss
            = DateTimeFormatter.ofPattern(DateTimeFormat.dd_DASH_MM_DASH_yyyy_SPACE_HH_COLON_mm_COLON_ss.getFormat());
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT_dd_DASH_MM_DASH_yyyy_SPACE_HH_COLON_mm_COLON_ss
            = new SimpleDateFormat(DateTimeFormat.dd_DASH_MM_DASH_yyyy_SPACE_HH_COLON_mm_COLON_ss.getFormat());

    /* pattern yyyyMMddHHmmss */
    private static final DateTimeFormatter DATE_TIME_FORMATTER_yyyyMMddHHmmss
            = DateTimeFormatter.ofPattern(DateTimeFormat.yyyyMMddHHmmss.getFormat());
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT_yyyyMMddHHmmss
            = new SimpleDateFormat(DateTimeFormat.yyyyMMddHHmmss.getFormat());

    /* pattern "dd/MM/yyyy HH:mm" */
    private static final DateTimeFormatter DATE_TIME_FORMATTER_dd_SLASH_MM_SLASH_yyyy_SPACE_HH_COLON_mm
            = DateTimeFormatter.ofPattern(DateTimeFormat.dd_SLASH_MM_SLASH_yyyy_SPACE_HH_COLON_mm.getFormat());
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT_dd_SLASH_MM_SLASH_yyyy_SPACE_HH_COLON_mm
            = new SimpleDateFormat(DateTimeFormat.dd_SLASH_MM_SLASH_yyyy_SPACE_HH_COLON_mm.getFormat());

    /* pattern "yyyy-MM-dd_HH:mm:ss" */
    private static final DateTimeFormatter DATE_TIME_FORMATTER_dd_DASH_MM_DASH_yyyy_UNDERSCORE_HH_COLON_mm_COLON_ss
            = DateTimeFormatter.ofPattern(DateTimeFormat.dd_DASH_MM_DASH_yyyy_UNDERSCORE_HH_COLON_mm_COLON_ss.getFormat());
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT_dd_DASH_MM_DASH_yyyy_UNDERSCORE_HH_COLON_mm_COLON_ss
            = new SimpleDateFormat(DateTimeFormat.dd_DASH_MM_DASH_yyyy_UNDERSCORE_HH_COLON_mm_COLON_ss.getFormat());

    /* pattern "yyMMddHHmmssS" */
    private static final DateTimeFormatter DATE_TIME_FORMATTER_yy_MM_dd_HH_mm_ss_S
            = DateTimeFormatter.ofPattern(DateTimeFormat.yy_MM_dd_HH_mm_ss_S.getFormat());
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT_yy_MM_dd_HH_mm_ss_S
            = new SimpleDateFormat(DateTimeFormat.yy_MM_dd_HH_mm_ss_S.getFormat());

    /* pattern "yyyy-MM-dd" */
    private static final DateTimeFormatter DATE_TIME_FORMATTER_yyyy_DASH_MM_DASH_dd
            = DateTimeFormatter.ofPattern(DateTimeFormat.yyyy_DASH_MM_DASH_dd.getFormat());
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT_yyyy_DASH_MM_DASH_dd
            = new SimpleDateFormat(DateTimeFormat.yyyy_DASH_MM_DASH_dd.getFormat());

    static {
        /* pattern MM/yyyy */
        DATE_TIME_FORMATTER_MAP.put(DateTimeFormat.MM_SLASH_yyyy.getFormat(),
                DATE_TIME_FORMATTER_MM_SLASH_yyyy);
        SIMPLE_DATE_FORMAT_MAP.put(DateTimeFormat.MM_SLASH_yyyy.getFormat(),
                SIMPLE_DATE_FORMAT_MM_SLASH_yyyy);

        /* pattern dd/MM/yyyy HHmmss */
        DATE_TIME_FORMATTER_MAP.put(DateTimeFormat.dd_SLASH_MM_SLASH_yyyy_SPACE_HH_mm_ss.getFormat(),
                DATE_TIME_FORMATTER_dd_SLASH_MM_SLASH_yyyy_SPACE_HH_mm_ss);
        SIMPLE_DATE_FORMAT_MAP.put(DateTimeFormat.dd_SLASH_MM_SLASH_yyyy_SPACE_HH_mm_ss.getFormat(),
                SIMPLE_DATE_FORMAT_dd_SLASH_MM_SLASH_yyyy_SPACE_HH_mm_ss);

        /* pattern dd/MM/yyyy HH:mm:ss */
        DATE_TIME_FORMATTER_MAP.put(DateTimeFormat.dd_SLASH_MM_SLASH_yyyy_SPACE_HH_COLON_mm_COLON_ss.getFormat(),
                DATE_TIME_FORMATTER_dd_SLASH_MM_SLASH_yyyy_SPACE_HH_COLON_mm_COLON_ss);
        SIMPLE_DATE_FORMAT_MAP.put(DateTimeFormat.dd_SLASH_MM_SLASH_yyyy_SPACE_HH_COLON_mm_COLON_ss.getFormat(),
                SIMPLE_DATE_FORMAT_dd_SLASH_MM_SLASH_yyyy_SPACE_HH_COLON_mm_COLON_ss);

        /* pattern dd/MM/yyyy */
        DATE_TIME_FORMATTER_MAP.put(DateTimeFormat.dd_SLASH_MM_SLASH_yyyy.getFormat(),
                DATE_TIME_FORMATTER_dd_SLASH_MM_SLASH_yyyy);
        SIMPLE_DATE_FORMAT_MAP.put(DateTimeFormat.dd_SLASH_MM_SLASH_yyyy.getFormat(),
                SIMPLE_DATE_FORMAT_dd_SLASH_MM_SLASH_yyyy);

        /* pattern yyyyMMdd HHmmss */
        DATE_TIME_FORMATTER_MAP.put(DateTimeFormat.yyyyMMdd_SPACE_HHmmss.getFormat(),
                DATE_TIME_FORMATTER_yyyyMMdd_SPACE_HHmmss);
        SIMPLE_DATE_FORMAT_MAP.put(DateTimeFormat.yyyyMMdd_SPACE_HHmmss.getFormat(),
                SIMPLE_DATE_FORMAT_yyyyMMdd_SPACE_HHmmss);

        /* pattern yyyy-MM-dd HH:mm:ss */
        DATE_TIME_FORMATTER_MAP.put(DateTimeFormat.dd_DASH_MM_DASH_yyyy_SPACE_HH_COLON_mm_COLON_ss.getFormat(),
                DATE_TIME_FORMATTER_dd_DASH_MM_DASH_yyyy_SPACE_HH_COLON_mm_COLON_ss);
        SIMPLE_DATE_FORMAT_MAP.put(DateTimeFormat.dd_DASH_MM_DASH_yyyy_SPACE_HH_COLON_mm_COLON_ss.getFormat(),
                SIMPLE_DATE_FORMAT_dd_DASH_MM_DASH_yyyy_SPACE_HH_COLON_mm_COLON_ss);

        /* pattern yyyyMMddHHmmss */
        DATE_TIME_FORMATTER_MAP.put(DateTimeFormat.yyyyMMddHHmmss.getFormat(),
                DATE_TIME_FORMATTER_yyyyMMddHHmmss);
        SIMPLE_DATE_FORMAT_MAP.put(DateTimeFormat.yyyyMMddHHmmss.getFormat(),
                SIMPLE_DATE_FORMAT_yyyyMMddHHmmss);

        /* pattern "dd/MM/yyyy HH:mm" */
        DATE_TIME_FORMATTER_MAP.put(DateTimeFormat.dd_SLASH_MM_SLASH_yyyy_SPACE_HH_COLON_mm.getFormat(),
                DATE_TIME_FORMATTER_dd_SLASH_MM_SLASH_yyyy_SPACE_HH_COLON_mm);
        SIMPLE_DATE_FORMAT_MAP.put(DateTimeFormat.dd_SLASH_MM_SLASH_yyyy_SPACE_HH_COLON_mm.getFormat(),
                SIMPLE_DATE_FORMAT_dd_SLASH_MM_SLASH_yyyy_SPACE_HH_COLON_mm);

        /* pattern "yyyy-MM-dd_HH:mm:ss" */
        DATE_TIME_FORMATTER_MAP.put(DateTimeFormat.dd_DASH_MM_DASH_yyyy_UNDERSCORE_HH_COLON_mm_COLON_ss.getFormat(),
                DATE_TIME_FORMATTER_dd_DASH_MM_DASH_yyyy_UNDERSCORE_HH_COLON_mm_COLON_ss);
        SIMPLE_DATE_FORMAT_MAP.put(DateTimeFormat.dd_DASH_MM_DASH_yyyy_UNDERSCORE_HH_COLON_mm_COLON_ss.getFormat(),
                SIMPLE_DATE_FORMAT_dd_DASH_MM_DASH_yyyy_UNDERSCORE_HH_COLON_mm_COLON_ss);

        /* pattern "yyMMddHHmmssS" */
        DATE_TIME_FORMATTER_MAP.put(DateTimeFormat.yy_MM_dd_HH_mm_ss_S.getFormat(),
                DATE_TIME_FORMATTER_yy_MM_dd_HH_mm_ss_S);
        SIMPLE_DATE_FORMAT_MAP.put(DateTimeFormat.yy_MM_dd_HH_mm_ss_S.getFormat(),
                SIMPLE_DATE_FORMAT_yy_MM_dd_HH_mm_ss_S);

        /* pattern "yyMMddHHmmssS" */
        DATE_TIME_FORMATTER_MAP.put(DateTimeFormat.yyyy_DASH_MM_DASH_dd.getFormat(),
                DATE_TIME_FORMATTER_yyyy_DASH_MM_DASH_dd);
        SIMPLE_DATE_FORMAT_MAP.put(DateTimeFormat.yyyy_DASH_MM_DASH_dd.getFormat(),
                SIMPLE_DATE_FORMAT_yyyy_DASH_MM_DASH_dd);
    }

    public static DateTimeFormatter getDateTimeFormatter(String pattern) {
        if (pattern == null) {
            return null;
        }

        return DATE_TIME_FORMATTER_MAP.get(pattern);
    }

    public static SimpleDateFormat getSimplerDateFormat(String pattern) {
        if (pattern == null) {
            return null;
        }

        return SIMPLE_DATE_FORMAT_MAP.get(pattern);
    }

    public static DateTimeFormatter getDateTimeFormatter(DateTimeFormat pattern) {
        if (pattern == null) {
            return null;
        }

        return DATE_TIME_FORMATTER_MAP.get(pattern.getFormat());
    }

    public static SimpleDateFormat getSimplerDateFormat(DateTimeFormat pattern) {
        if (pattern == null) {
            return null;
        }

        return SIMPLE_DATE_FORMAT_MAP.get(pattern.getFormat());
    }

    public static String cast(Date date, DateTimeFormat format) {
        if (date == null || format == null) {
            return "";
        }

        SimpleDateFormat simpleDateFormat = getSimplerDateFormat(format);
        return simpleDateFormat.format(date);
    }


    public static String format(LocalDate time, DateTimeFormat format) {
        DateTimeFormatter formatter = DateTimeFormatUtil.getDateTimeFormatter(format);
        return formatter.format(time);
    }

    public static String format(LocalDateTime time, DateTimeFormat format) {
        DateTimeFormatter formatter = DateTimeFormatUtil.getDateTimeFormatter(format);
        return formatter.format(time);
    }

    public static String format(Date date, DateTimeFormat pattern) {
        SimpleDateFormat formatter = DateTimeFormatUtil.getSimplerDateFormat(pattern);
        return formatter.format(date);
    }

    public static LocalDate castToLocalDate(String dateStr, DateTimeFormat format) {
        DateTimeFormatter formatter = DateTimeFormatUtil.getDateTimeFormatter(format);
        return LocalDate.parse(dateStr, formatter);
    }

    public static LocalDate castToLocalDate(String dateStr, String formatPattern) {
        DateTimeFormatter format = new DateTimeFormatterBuilder()
                .appendPattern(formatPattern)
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .toFormatter();
        return LocalDate.parse(dateStr, format);
    }

    public static LocalDateTime castToLocalDateTime(String dateStr, DateTimeFormat format) {
        DateTimeFormatter formatter = DateTimeFormatUtil.getDateTimeFormatter(format);
        return LocalDateTime.parse(dateStr, formatter);
    }

    public static Date castToDate(String dateStr, DateTimeFormat format) {
        SimpleDateFormat formatter = DateTimeFormatUtil.getSimplerDateFormat(format);
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    /**
     * Get now local date follow pattern format.
     *
     * @param pattern
     * @return
     */
    public static String getLocalDateNow(DateTimeFormat pattern) {
        return format(LocalDate.now(), pattern);
    }

    public static LocalDateTime parse_ddMMyyyy(String time_ddMMyyyy) {
        Date date = castToDate(time_ddMMyyyy, DateTimeFormat.dd_SLASH_MM_SLASH_yyyy);
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static String formatRangeDate(LocalDateTime fromDate, LocalDateTime toDate) {
        try {
            return String.format("%d tháng %d, %d đến %d tháng %d, %d", 1, fromDate.getMonthValue(), fromDate.getYear(), toDate.getDayOfMonth(), toDate.getMonthValue(), toDate.getYear());
        } catch (Exception ignored) {
        }

        return "";
    }
}
