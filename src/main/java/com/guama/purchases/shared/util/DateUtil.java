package com.guama.purchases.shared.util;

import lombok.extern.slf4j.Slf4j;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class DateUtil {

    private static final DateTimeFormatter defaultFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter yyyyMMddFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private DateUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Parses a LocalDateTime object into a String using the default formatter.
     * @param inputDate The LocalDateTime object to be parsed.
     * @return A formatted date String.
     * @throws DateTimeException if the parsing fails.
     */
    public static String defaultParse(LocalDateTime inputDate){
        try{

            log.info("[INI] Parsing date {} with default formatter", inputDate);
            String formattedDate = inputDate.format(defaultFormatter);
            log.info("[END] Date parsed successfully: {}", formattedDate);
            return formattedDate;

        }catch (DateTimeException ex){
            log.error("[ERROR] Date parsing failed for date {}: {}", inputDate, ex.getMessage());
            throw new DateTimeException("Failed to parse date: " + inputDate, ex);
        }
    }

    public static LocalDate toLocalDate(String inputDate){
        try{

            log.info("[INI] Parsing date {} with default formatter", inputDate);
            LocalDate parsedDate = LocalDate.parse(inputDate, yyyyMMddFormatter);
            log.info("[END] Date parsed successfully: {}", parsedDate);
            return parsedDate;

        }catch (DateTimeException ex){
            log.error("[ERROR] Date parsing failed for date {}: {}", inputDate, ex.getMessage());
            throw new DateTimeException("Failed to parse date: " + inputDate, ex);
        }
    }
}
