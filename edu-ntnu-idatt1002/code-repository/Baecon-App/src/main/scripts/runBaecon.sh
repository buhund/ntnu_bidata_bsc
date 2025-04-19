#!/bin/sh

cd "$(dirname "$0")"
chmod +x Baecon.jar
java --module-path lib --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.swing,javafx.web,javafx.media -jar Baecon.jar