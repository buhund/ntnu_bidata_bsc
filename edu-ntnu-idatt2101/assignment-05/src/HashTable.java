import java.util.LinkedList;

public class HashTable {
  private final int TABLE_SIZE;
  private final LinkedList<String>[] table;

  // Creates a hash table.
  public HashTable(int size) {
    this.TABLE_SIZE = size;
    table = new LinkedList[TABLE_SIZE];
    for (int i = 0; i < TABLE_SIZE; i++) {
      table[i] = new LinkedList<>();
    }
  }

  // Converts names to hash keys.
  private int hash(String key) {
    int sum = 0;
    for (int i = 0; i < key.length(); i++) {
      sum += key.charAt(i) * (i + 1);
    }
    return sum % TABLE_SIZE;
  }

  // Adds a name to the hash table.
  public void insert(String key) {
    int index = hash(key);
    if (!table[index].isEmpty()) {
      for (String name : table[index]) {
        //System.out.println("Kollisjon mellom " + name + " og " + key);
      }
    }
    table[index].add(key);
  }

  // Check if key exists in hash table.
  public boolean lookup(String key) {
    int index = hash(key);
    return table[index].contains(key);
  }

  // A collision occurs when two or more items are mapped to the same slot.
  public int getCollisionsCount() {
    int collisions = 0;
    for (LinkedList<String> list : table) {
      if (list.size() > 1) {
        collisions += list.size() - 1;
      }
    }
    return collisions;
  }

  // Load factor is the ratio of used slots to the total number of slots in the hash table.
  public double getLoadFactor() {
    int usedSlots = 0;
    for (LinkedList<String> list : table) {
      if (!list.isEmpty()) {
        usedSlots++;
      }
    }
    return (double) usedSlots / TABLE_SIZE;
  }
}
