package no.ntnu.idatt2106.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

/**
 * Badge is connected to a specific Challenge or Goal.
 * It will contain a name, description and a file path to the badge image.
 */
@Entity
@Table(name = "badge")
public class Badge {

    @Id
    @Column(nullable = false)
    private String name;


    @Column(nullable = false)
    private String description;

    @Column(unique = true, nullable = false)
    private String fileName;

    // To be adjusted for actual file path for badge images.
    @Column(nullable = false)
    private String filePath = "classpath:/badgeassets/" + fileName;

    @OneToMany(mappedBy = "badge", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "user-badge")
    private List<UserBadge> userBadges;

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<UserBadge> getUserBadges() {
        return userBadges;
    }

    public void setUserBadges(List<UserBadge> userBadges) {
        this.userBadges = userBadges;
    }
}
