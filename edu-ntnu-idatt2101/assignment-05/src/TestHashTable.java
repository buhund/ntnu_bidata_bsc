import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class TestHashTable {
    public static void main(String[] args) {
        // Read names from file
        File file = new File("navn");
        LinkedList<String> names = new LinkedList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                names.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // Create hash table
        int expectedSize = names.size();
        HashTable hashTable = new HashTable(expectedSize * 2);

        // Add names to hash table
        for (String name : names) {
            hashTable.insert(name);
        }

        // Lookup name
        String name = "John-Ivar Dalhaug Eriksen";
        if (hashTable.lookup(name)) {
            System.out.println("\n"+ name + " er med i faget!"); // name is found
        } else {
            System.out.println("\n"+ name + " er ikke med i faget.");// name is not found
        }

        System.out.println("\nLastfaktor: " + hashTable.getLoadFactor()); // load factor
        System.out.println("Total kollisjoner: " + hashTable.getCollisionsCount()); // total collisions
        System.out.println("Gjennomsnittlig kollisjoner per person: " + (double) hashTable.getCollisionsCount() / expectedSize); // mean collision
    }
}
