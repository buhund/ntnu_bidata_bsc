import static java.lang.System.nanoTime;

public class JosefusComplexity {

  public static void main(String[] args) {
    int n = 100; // Number of people in the circle.
    int m = 100; // Up-to-Step, every m-th man to commit suicide. 3 = #1 lives, #2 lives, #3 dies.
    System.out.println("n (p), m (s), time (ns), position");

/*
    // Incrementing full m for every incrementation of n
    for (int i = 1; i < n + 1; i++) {
      for (int j = 1; j < m + 1; j++) {
        JosefusCircle(i, j);
      }
    }
    */

    // Incrementing only m
    for (int j = 1; j < m+1; j++) {
      JosefusCircle(n, j);
    }


  }

  /**
   * Creating a Circular Linked List for the Josephus Circle
   * @param n size of list, i.e. number of people in circle
   * @param m steps, every m-th man to commit suicide
   */
  static void JosefusCircle(int n, int m) {
    Node head = new Node(1);
    Node prev = head;
    for (int i = 2; i <= n; i++) {
      prev.nextNode = new Node(i);
      prev = prev.nextNode;
    }

    prev.nextNode = head;
    Node pointer1 = head;
    Node pointer2 = head;

    long startTime = System.nanoTime();

    while(pointer1.nextNode != pointer1) {
      int counter = 1;
      while(counter != m) {
        pointer2 = pointer1;
        pointer1 = pointer1.nextNode;
        counter++;
      }

      // m-th node commits suicide
      pointer2.nextNode = pointer1.nextNode;
      pointer1 = pointer2.nextNode;
    }

    long endTime = System.nanoTime();
    long runtime = endTime - startTime;
    System.out.println(n + ", " + m + ", " + runtime + ", " + pointer1.data);
    // System.out.println("n = " + n + ", m = " + m);
    // System.out.println("Josefus' Position " + pointer1.data);

  }


  /**
   * Node class
   */
  static class Node {
  public int data ;
  public Node nextNode;
      public Node(int data )
  {
    this.data = data;
  }

}


} // End of class JosefusComplexity
