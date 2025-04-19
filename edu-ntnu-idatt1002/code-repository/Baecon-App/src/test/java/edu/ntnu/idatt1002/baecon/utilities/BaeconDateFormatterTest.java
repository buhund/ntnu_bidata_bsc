package edu.ntnu.idatt1002.baecon.utilities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class BaeconDateFormatterTest {

  @Test
  public void format_date_to_string() {
    LocalDate date = LocalDate.of(2020, 1, 1);
    String expectedDate = "01.01.2020 00:00:00";

    String formattedDate = BaeconDateFormatter.format(date);
    assertEquals(expectedDate, formattedDate);
  }

  @Test
  public void format_date_to_string_without_time() {
    LocalDate date = LocalDate.of(2020, 1, 1);
    String expectedDate = "01.01.2020";

    String formattedDate = BaeconDateFormatter.formatWithoutTime(date);
    assertEquals(expectedDate, formattedDate);
  }

  @Test
  public void parse_date_from_string() {
    String date = "01.01.2020 00:00:00";
    LocalDate expectedDate = LocalDate.of(2020, 1, 1);

    LocalDate parsedDate = BaeconDateFormatter.parse(date);
    assertEquals(expectedDate, parsedDate);
  }
}
