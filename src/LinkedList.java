import java.util.Random;

// linked list class for a deck of cards
public class LinkedList {

    public Node head;
    public Node tail;
    public int size = 0;

    LinkedList(){
        head = null;
        tail = null;
        size = 0;
    }

    public void shuffle(int shuffle_count) {

        Random rand = new Random();
        for(int i = 0; i < shuffle_count; i++) {
            // pick two random integers
            int r1 = rand.nextInt(52);
            int r2 = rand.nextInt(52);

            swap(r1,r2); // swap nodes at these indices
        }
    }

    // remove a card from a specific index
    public Card remove_from_index(int index) {
        // checks if the index exists
        if (index >= size) {
            return null;
        }
        Node curr = head;
        for(int i = 0; i < index; i++) {
            curr = curr.next;
        }
        // removes the head
        if (curr == head) {
            head = curr.next;
        }
        // removes the tail
        if (curr == tail) {
            tail = curr.prev;
        }
        // changes the next of the prev card to be the current card's next
        if (curr.prev != null) {
            curr.prev.next = curr.next;
        }
        // changes the prev of the next card to be the current card's prev
        if (curr.next != null) {
            curr.next.prev = curr.prev;
        }
        size--;
        return curr.data;
    }

    // insert a card at a specific index
    public void insert_at_index(Card x, int index) {
        Node newCard = new Node(x);
        // if the index is greater than or equal to the size, the new card is added at the tail
        if (index >= size) {
            add_at_tail(x);
            return;
        }
        Node curr = head;
        for(int i = 0; i < index; i++) {
            curr = curr.next;
        }
        // adds the new card as the head (if the index is zero)
        if (curr == head) {
            head = newCard;
        }
        newCard.next = curr;
        newCard.prev = curr.prev;
        if (curr.prev != null) {
            curr.prev.next = newCard;
        }
        curr.prev = newCard;
        size++;
    }

    // swap two cards in the deck at the specific indices
    public void swap(int index1, int index2) {
        // FIXME
    }

    // add card at the end of the list
    public void add_at_tail(Card data) {
        Node newCard = new Node(data);
        if (tail == null) {
            head = newCard;
            tail = newCard;
        } else {
            tail.next = newCard;
            newCard.prev = tail;
            tail = newCard;
        }
        size++;
    }

    // remove a card from the beginning of the list
    public Card remove_from_head() {
        if (head == null) {
            return null;
        }
        Card tempCard = head.data;
        head = head.next;
        // If the head is not null, the list is not empty, but if the head it null,
        // the list is empty, so the tail must also be null.
        if (head != null) {
            head.prev = null;
        } else {
            tail = null;
        }
        size--;
        return tempCard;
    }

    // check to make sure the linked list is implemented correctly by iterating forwards and backwards
    // and verifying that the size of the list is the same when counted both ways.
    // 1) if a node is incorrectly removed
    // 2) and head and tail are correctly updated
    // 3) each node's prev and next elements are correctly updated
    public void sanity_check() {
        // count nodes, counting forward
        Node curr = head;
        int count_forward = 0;
        while (curr != null) {
            curr = curr.next;
            count_forward++;
        }

        // count nodes, counting backward
        curr = tail;
        int count_backward = 0;
        while (curr != null) {
            curr = curr.prev;
            count_backward++;
        }

        // check that forward count, backward count, and internal size of the list match
        if (count_backward == count_forward && count_backward == size) {
            System.out.println("Basic sanity Checks passed");
        }
        else {
            // there was an error, here are the stats
            System.out.println("Count forward:  " + count_forward);
            System.out.println("Count backward: " + count_backward);
            System.out.println("Size of LL:     " + size);
            System.out.println("Sanity checks failed");
            System.exit(-1);
        }
    }

    // print the deck
    public void print() {
        Node curr = head;
        int i = 1;
        while(curr != null) {
            curr.data.print_card();
            if(curr.next != null)
                System.out.print(" -->  ");
            else
                System.out.println(" X");

            if (i % 7 == 0) System.out.println("");
            i = i + 1;
            curr = curr.next;
        }
        System.out.println("");
    }
}