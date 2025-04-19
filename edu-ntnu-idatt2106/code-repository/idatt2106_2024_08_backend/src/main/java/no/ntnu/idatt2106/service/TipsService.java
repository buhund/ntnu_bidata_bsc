package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.User;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Service class for retrieving tips for a user based on the knowledge level.
 * User knowledge level is determined by the user's profile settings.
 * The knowledge level is used to determine which file to read tips from.
 * Interacts with the TipsController class.
 */
@Service
public class TipsService {

    private static final Random random = new Random();

    public String getTipsForUser(User user) throws IOException {
        try {
            String resourceName = getResourceNameForUser(user);
            ClassPathResource tipsFileResource = new ClassPathResource(resourceName);
            return readRandomTip(tipsFileResource);
        } catch (IOException e) {
            throw new IOException("Failed to open file: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves the resource name based on the knowledge level of a user.
     * The resource name is used to fetch tips for the user.
     *
     * @param user The User object for which the resource name needs to be determined.
     * @return The resource name based on the user's knowledge level.
     */
    String getResourceNameForUser(User user) {
        return switch (user.getKnowledgeLevel()) {
            case HIGH -> "tips-high";
            case MEDIUM -> "tips-medium";
            default -> "tips-low";
        };
    }

    /**
     * Reads a random tip from the given ClassPathResource.
     *
     * @param resource The ClassPathResource object representing the tips file.
     * @return A randomly selected tip from the tips file.
     * @throws IOException If an I/O error occurs while reading the tips file.
     */
    String readRandomTip(ClassPathResource resource) throws IOException {
        InputStream tipsFile = resource.getInputStream();
        Scanner tipsScanner = new Scanner(tipsFile);
        ArrayList<String> tips = new ArrayList<>();
        while (tipsScanner.hasNextLine()) {
            tips.add(tipsScanner.nextLine());
        }
        tipsScanner.close();
        return tips.get(random.nextInt(tips.size()));
    }
}

