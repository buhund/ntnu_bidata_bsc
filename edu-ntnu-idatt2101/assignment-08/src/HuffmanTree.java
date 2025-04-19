import java.util.PriorityQueue;

class HuffmanTree {

    public static HuffmanNode buildTree(char[] charArray, int[] charFreq, int n) {
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>(n, new Compare());

        for (int i = 0; i < n; i++) {

            HuffmanNode hn = new HuffmanNode();

            hn.c = charArray[i];
            hn.data = charFreq[hn.c];

            hn.left = null;
            hn.right = null;

            queue.add(hn);
        }

        // create a root node
        HuffmanNode root = null;

        while (queue.size() > 1) {

            // first minimum extract
            HuffmanNode l = queue.peek();
            queue.poll();

            // second minimum extract
            HuffmanNode r = queue.peek();
            queue.poll();

            // new node f which is equal to the sum of the frequency of the two nodes
            HuffmanNode f = new HuffmanNode();


            assert r != null;
            f.data = l.data + r.data;
            f.c = Character.MIN_VALUE;


            f.left = l;

            f.right = r;

            root = f;
            queue.add(f);
        }
        return root;
    }
}