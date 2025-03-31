// Catherine Neely
// 03.31.25

import java.util.Scanner;

public class lab6 {

    public static LinkedList initialize_deck() {

        LinkedList deck = new LinkedList();

        // populate linked list with a single deck of cards
        for (Card.suites s : Card.suites.values()) {
            for(Card.ranks r : Card.ranks.values()) {
                if (r != Card.ranks.NULL && s != Card.suites.NULL) {
                    Card newCard = new Card(s, r);
                    //newCard.print_card();
                    deck.add_at_tail(newCard);
                }
            }
        }

        return deck;
    }

    private static void play_blind_mans_bluff(LinkedList user, LinkedList computer, LinkedList deck) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n**Starting Blind Mans Bluff**");

        int rounds = 0;
        int userWins = 0;
        int userLosses = 0;
        int prevUserLosses = 0;
        int computerWins = 0;

        while (rounds < 5) {
            System.out.println("\n**Round " + (rounds + 1) + "**\n");

            Card userCard = user.remove_from_head();
            Card computerCard = computer.remove_from_head();

            System.out.println("User card--> ?");
            System.out.print("Computer card--> ");
            computerCard.print_card();

            System.out.print("\nGuess if your card higher or lower (H/L): ");
            String guess = scanner.nextLine();

            int compare = userCard.getRank().compareTo(computerCard.getRank());

            if ((guess.equalsIgnoreCase("H") && compare > 0) || (guess.equalsIgnoreCase("L") && compare < 0)) {
                System.out.println("You guessed correctly!");
                System.out.print("User card--> ");
                userCard.print_card();
                System.out.print("\nComputer card--> ");
                computerCard.print_card();
                System.out.println();
                userWins++;
                prevUserLosses = 0;
            } else if (compare == 0) {
                System.out.println("The ranks are equal!");
                compare = userCard.getSuit().compareTo(computerCard.getSuit());
                if (compare > 0) {
                    System.out.println("User wins based on suit!");
                    System.out.print("User card--> ");
                    userCard.print_card();
                    System.out.print("\nComputer card--> ");
                    computerCard.print_card();
                    System.out.println();
                    userWins++;
                    prevUserLosses = 0;
                } else if (compare < 0) {
                    System.out.println("Computer wins based on suit!");
                    System.out.print("User card--> ");
                    userCard.print_card();
                    System.out.print("\nComputer card--> ");
                    computerCard.print_card();
                    System.out.println();
                    computerWins++;
                    userLosses++;
                    prevUserLosses++;
                }
            } else {
                System.out.println("You guessed incorrectly!");
                System.out.print("User card--> ");
                userCard.print_card();
                System.out.print("\nComputer card--> ");
                computerCard.print_card();
                System.out.println();
                computerWins++;
                userLosses++;
                prevUserLosses++;
            }

            if (userLosses == 3 && prevUserLosses == 2) {
                rage_quit(user, computer, deck);
                return;
            }
            rounds++;
        }
        System.out.println("\n**End of Blind Mans Bluff**");
        System.out.println("User wins: " + userWins);
        System.out.println("Computer wins: " + computerWins);
    }
    public static void rage_quit(LinkedList user, LinkedList computer, LinkedList deck) {
        System.out.println("\n**Rage Quitting**\n");

        while(!deck.isEmpty()) {
            deck.remove_from_head();
        }
        while(!user.isEmpty()) {
            user.remove_from_head();
        }
        while(!computer.isEmpty()) {
            computer.remove_from_head();
        }

        deck = initialize_deck();
        deck.sanity_check();
        deck.shuffle(512);

        int numCardsDealt = 5;
        for (int i = 0; i < numCardsDealt; i++) {
            user.add_at_tail(deck.remove_from_head());
            computer.add_at_tail(deck.remove_from_head());
        }
    }

    public static void main(String[] args) {

        // create a deck (in order)
        LinkedList deck = initialize_deck();
        deck.print();
        deck.sanity_check(); // because we can all use one

        // shuffle the deck (random order)
        deck.shuffle(512);
        deck.sanity_check(); // because we can all use one

        // cards for player 1 (hand)
        LinkedList user = new LinkedList();
        // cards for player 2 (hand)
        LinkedList computer = new LinkedList();

        int num_cards_dealt = 5;
        for (int i = 0; i < num_cards_dealt; i++) {
            // player removes a card from the deck and adds to their hand
            user.add_at_tail(deck.remove_from_head());
            computer.add_at_tail(deck.remove_from_head());
        }

        // let the games begin!
        play_blind_mans_bluff(user, computer, deck);
    }
}
