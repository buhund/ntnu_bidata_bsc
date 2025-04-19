package edu.ntnu.idatt1002.baecon.utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Class responsible for formatting and parsing dates.
 */
public class BaeconDateFormatter {
  private static final String DATE_FORMAT = "dd.MM.yyyy HH:mm:ss";
  private static final String DATE_WITHOUT_TIME_FORMAT = "dd.MM.yyyy";

  /**
   * Method to format a LocalDate object to a string with time.
   *
   * @param dateToFormat the date to format
   * @return the formatted date string with time
   */
  public static String format(LocalDate dateToFormat) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    return dateTimeFormatter.format(dateToFormat.atStartOfDay());
  }

  /**
   * Method to parse a LocalDate object from a string.
   *
   * @param dateString the date string to parse
   * @return the parsed LocalDate object
   */
  public static LocalDate parse(String dateString) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    return LocalDate.parse(dateString, dateTimeFormatter);
  }

  /**
   * Method to format a LocalDate object to a string without time.
   *
   * @param dateToFormat the date to format
   * @return the formatted date string without time
   */
  public static String formatWithoutTime(LocalDate dateToFormat) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_WITHOUT_TIME_FORMAT);
    return dateTimeFormatter.format(dateToFormat);
  }
}
