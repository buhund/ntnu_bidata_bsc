package no.ntnu.idatt2106.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import no.ntnu.idatt2106.enums.ChallengeType;
import no.ntnu.idatt2106.enums.ProductCategory;

import java.time.LocalDate;

/**
 * Challenges are connected to preferably a specific Goal,
 * or at least a specific User.
 * Each Challenge will track and update its progress,
 * and contributes to the overall progress of a Goal.
 */
@Entity
@Table(name = "challenge")
public class Challenge {

    /**
     * Unique identifier for a given Challenge.
     * Automatically generated.
     * Acts as primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    /**
     * Challenge Type determines how the progress will
     * be calculated towards the Challenge target amount.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChallengeType challengeType;

    /**
     * The target amount or threshold that determines if the
     * challenge is successful.
     * Less than, more than or equal to, depending on
     * the type of challenge.
     */
    @Column(nullable = false)
    private int target;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductCategory category;

    @Column(nullable = false)
    private int progress;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column
    private boolean completed;

    /**
     * Use user_id as foreign key to connect a challenge
     * a specific user.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "user-challenge")
    private User user;

    /**
     * Use goal_id as foreign key to connect a challenge
     * to a specific goal.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id")
    @JsonBackReference(value = "goal-challenge")
    private Goal goal;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public ChallengeType getChallengeType() {
        return challengeType;
    }

    public void setChallengeType(ChallengeType challengeType) {
        this.challengeType = challengeType;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }
}
