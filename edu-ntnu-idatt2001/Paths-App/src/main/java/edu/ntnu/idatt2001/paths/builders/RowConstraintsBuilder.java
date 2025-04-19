/**
 * @project Paths-App
 * @author Gruppe 12, IDATT2001
 */

package edu.ntnu.idatt2001.paths.builders;

import javafx.geometry.VPos;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

/**
 * Builder/Helper class for bulding GridPane Row Constraints.
 */
public class RowConstraintsBuilder {
  private double minHeight;
  private double prefHeight;
  private double maxHeight;

  /**
   * Builder method constructor.
   */
  private RowConstraintsBuilder() {}

  /**
   * Creator method.
   *
   * @return new RowConstraintsBuilder()
   */
  public static RowConstraintsBuilder create() {
    return new RowConstraintsBuilder();
  }

  /**
   * Minimum row heights builder.
   *
   * @param minHeight
   * @return
   */
  public RowConstraintsBuilder withMinHeight(double minHeight) {
    this.minHeight = minHeight;
    return this;
  }

  /**
   * Preferred row heights builder.
   *
   * @param prefHeight
   * @return
   */
  public RowConstraintsBuilder withPrefHeight(double prefHeight) {
    this.prefHeight = prefHeight;
    return this;
  }

  /**
   * Maximum row heights builder.
   *
   * @param maxHeight
   * @return
   */
  public RowConstraintsBuilder withMaxHeight(double maxHeight) {
    this.maxHeight = maxHeight;
    return this;
  }

  /**
   * Build method.
   *
   * @return rowConstraints
   */
  public RowConstraints build() {
    RowConstraints rowConstraints = new RowConstraints();
    rowConstraints.setVgrow(Priority.ALWAYS);
    rowConstraints.setValignment(VPos.CENTER);
    rowConstraints.setMinHeight(minHeight);
    rowConstraints.setPrefHeight(prefHeight);
    rowConstraints.setMaxHeight(maxHeight);
    return rowConstraints;
  }

}
