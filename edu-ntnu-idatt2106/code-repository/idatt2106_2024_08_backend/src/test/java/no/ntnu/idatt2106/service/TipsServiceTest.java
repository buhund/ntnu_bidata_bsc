package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.enums.KnowledgeLevel;
import no.ntnu.idatt2106.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TipsServiceTest {

    @Mock
    private ClassPathResource resourceMock;

    @InjectMocks
    private TipsService tipsService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void getTipsForUser_returnsCorrectTipForKnowledgeLevel_HIGH() throws Exception {
        // Set the user's knowledge level
        user.setKnowledgeLevel(KnowledgeLevel.HIGH);

        // Get the resource name expected for this knowledge level
        String resourceName = tipsService.getResourceNameForUser(user);

        // Use the actual ClassPathResource to read the file
        ClassPathResource resource = new ClassPathResource(resourceName);
        assertTrue(resource.exists(), "The resource file should exist");

        // Read all lines to simulate what the service does
        InputStream inputStream = resource.getInputStream();
        Scanner scanner = new Scanner(inputStream);
        List<String> allTips = new ArrayList<>();
        while (scanner.hasNextLine()) {
            allTips.add(scanner.nextLine());
        }
        scanner.close();

        // Call the actual service method
        String tip = tipsService.getTipsForUser(user);

        // Verify the tip is one of the expected tips from the file
        assertTrue(allTips.contains(tip), "The returned tip should be one of the loaded tips");

        // Positive test. Verify reading correct file
        String EXPECTED_TIP_HIGH = "High level tip";
        assertEquals(EXPECTED_TIP_HIGH, tip);

        // Negative test. Verify not reading the wrong level files
        assertNotEquals("Medium level tip", tip);
        assertNotEquals("Low level tip", tip);

        // Print for manual inspection
        String actualTip = tipsService.getTipsForUser(user);
        printTipForInspection(actualTip, EXPECTED_TIP_HIGH, user.getKnowledgeLevel());
    }


    @Test
    void getTipsForUser_returnsCorrectTipForKnowledgeLevel_MEDIUM() throws Exception {
        // Set the user's knowledge level
        user.setKnowledgeLevel(KnowledgeLevel.MEDIUM);

        // Get the resource name expected for this knowledge level
        String resourceName = tipsService.getResourceNameForUser(user);

        // Use the actual ClassPathResource to read the file
        ClassPathResource resource = new ClassPathResource(resourceName);
        assertTrue(resource.exists(), "The resource file should exist");

        // Read all lines to simulate what the service does
        InputStream inputStream = resource.getInputStream();
        Scanner scanner = new Scanner(inputStream);
        List<String> allTips = new ArrayList<>();
        while (scanner.hasNextLine()) {
            allTips.add(scanner.nextLine());
        }
        scanner.close();

        // Call the actual service method
        String tip = tipsService.getTipsForUser(user);

        // Verify the tip is one of the expected tips from the file
        assertTrue(allTips.contains(tip), "The returned tip should be one of the loaded tips");

        // Positive test. Verify reading correct file
        String EXPECTED_TIP_MEDIUM = "Medium level tip";
        assertEquals(EXPECTED_TIP_MEDIUM, tip);

        // Negative test. Verify not reading the wrong level files
        assertNotEquals("High level tip", tip);
        assertNotEquals("Low level tip", tip);

        // Print for manual inspection
        String actualTip = tipsService.getTipsForUser(user);
        printTipForInspection(actualTip, EXPECTED_TIP_MEDIUM, user.getKnowledgeLevel());
    }

    @Test
    void getTipsForUser_returnsCorrectTipForKnowledgeLevel_LOW() throws Exception {
        // Set the user's knowledge level
        user.setKnowledgeLevel(KnowledgeLevel.LOW);

        // Get the resource name expected for this knowledge level
        String resourceName = tipsService.getResourceNameForUser(user);

        // Use the actual ClassPathResource to read the file
        ClassPathResource resource = new ClassPathResource(resourceName);
        assertTrue(resource.exists(), "The resource file should exist");

        // Read all lines to simulate what the service does
        InputStream inputStream = resource.getInputStream();
        Scanner scanner = new Scanner(inputStream);
        List<String> allTips = new ArrayList<>();
        while (scanner.hasNextLine()) {
            allTips.add(scanner.nextLine());
        }
        scanner.close();

        // Call the actual service method
        String tip = tipsService.getTipsForUser(user);

        // Verify the tip is one of the expected tips from the file
        assertTrue(allTips.contains(tip), "The returned tip should be one of the loaded tips");

        // Positive test. Verify reading correct file
        String EXPECTED_TIP_LOW = "Low level tip";
        assertEquals(EXPECTED_TIP_LOW, tip);

        // Negative test. Verify not reading the wrong level files
        assertNotEquals("High level tip", tip);
        assertNotEquals("Medium level tip", tip);

        // Print for manual inspection
        String actualTip = tipsService.getTipsForUser(user);
        printTipForInspection(actualTip, EXPECTED_TIP_LOW, user.getKnowledgeLevel());
    }

    @Test
    void getTipsForUser_handlesFileNotFound() {
        // Arrange
        User user = new User();
        user.setKnowledgeLevel(KnowledgeLevel.HIGH);
        // Manually set a resource name to a non-existent file
        String resourceName = "nonexistent-file.txt";
        TipsService modifiedService = new TipsService() {
            @Override
            protected String getResourceNameForUser(User user) {
                return resourceName;
            }
        };
        ClassPathResource resource = new ClassPathResource(resourceName);
        assertFalse(resource.exists(), "The resource file should NOT exist");

        try {
            modifiedService.getTipsForUser(user);
            fail("Expected an IOException to be thrown");
        } catch (IOException e) {
            System.out.println("Caught IOException with message: " + e.getMessage());
            // Assert on the exception message
            assertTrue(e.getMessage().contains("Failed to open file"), "Exception message should indicate failure to open file");
        }

    }

    void mockFileInteraction(String fileContent) throws Exception {
        InputStream inputStream = new ByteArrayInputStream(fileContent.getBytes());
        when(resourceMock.getInputStream()).thenReturn(inputStream);
    }

    /**
     * Helper method for printing the tip for manual inspection.
     *
     * @param tip The actual tip returned by the service.
     * @param knowledgeLevel The knowledge level of the user.
     */
    void printTipForInspection(String selectedTip, String expectedTip, KnowledgeLevel knowledgeLevel) {
        System.out.println("Knowledge Level: " + knowledgeLevel);
        System.out.println("Selected Tip: " + selectedTip);
        System.out.println("Expected Tip: " + expectedTip);
        System.out.println();
    }
}
