public class JosefusProblem {

  public static void main(String[] args) {
    int n = 41; // Number of people in the circle.
    int m = 3; // Step, every m-th man to commit suicide. 3 = #1 lives, #2 lives, #3 dies.
    JosefusCircle(n, m);
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
    System.out.println("Josef ben Mattitjahu ha-Kohen should cunningly maneuver himself into position " + pointer1.data + " to avoid having to commit suicide.");
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