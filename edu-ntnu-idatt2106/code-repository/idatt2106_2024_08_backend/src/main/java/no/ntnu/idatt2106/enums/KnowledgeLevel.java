package no.ntnu.idatt2106.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum class for users level for economical knowledge and willingness to habit changes.
 */
public enum KnowledgeLevel {
    LOW("low"), MEDIUM("medium"), HIGH("high");

    private final String level;

    KnowledgeLevel(String level) {
        this.level = level;
    }

    @JsonValue
    public String getLevel() {
        return level;
    }
}
