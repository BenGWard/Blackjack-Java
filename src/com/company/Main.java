package com.company;

public class Main {

    public static void main(String[] args) {
        HandOfCards player = new HandOfCards();
        HandOfCards dealer = new HandOfCards();
        bool gameOver = false;
        bool gotBlackjack = false;
        char playAgain;
        char hitOrStay;

        Console.WriteLine("Casino Blackjack");
        Console.WriteLine("Dealer hits on soft 17");
        Console.WriteLine();

        do
        {
            //deal first two cards to player and dealer
            for (int i = 0; i < 2; i++)
            {
                player.Draw();
                dealer.Draw();
            }

            //check for blackjack
            if (player.IsBlackjack())
            {
                Console.WriteLine("You got blackjack! You won!");
                gameOver = true;
                gotBlackjack = true;
            }
            else if (dealer.IsBlackjack())
            {
                Console.WriteLine("Dealer got blackjack. You lost.");
                gameOver = true;
                gotBlackjack = true;
            }

            while (!gameOver)
            {
                Console.WriteLine("Dealer:\t");
                dealer.Print(true);
                Console.WriteLine();
                Console.WriteLine("Player:\t");
                player.Print(false);
                Console.WriteLine();

                do
                {
                    Console.Write("Do you want to hit or stay? [H / S]:");
                    hitOrStay = Console.ReadKey().KeyChar;
                    Console.WriteLine();
                    if (char.ToLower(hitOrStay) != 'h' && char.ToLower(hitOrStay) != 's')
                        Console.WriteLine("\nPlease enter H or S.");
                } while (char.ToLower(hitOrStay) != 'h' && char.ToLower(hitOrStay) != 's');

                if (char.ToLower(hitOrStay) == 'h')
                {
                    player.Draw();

                    if (player.ValueHand() > 21)
                        gameOver = true;
                }
                else
                    gameOver = true;
            }

            //dealer has his turn
            dealer.DealerPlay();

            //report final card totals
            Console.WriteLine("\nFinal Results:\n");
            Console.WriteLine("Dealer: " + dealer.ValueHand());
            dealer.Print(false);
            Console.WriteLine('\n');
            Console.WriteLine("Player: " + player.ValueHand());
            player.Print(false);
            Console.WriteLine('\n');

            //if player or dealer got a blackjack, game was already decided
            if (!gotBlackjack)
            {
                //declare winner
                if (player.ValueHand() > 21)
                    Console.WriteLine("You bust. You lose.");
                else if (dealer.ValueHand() > 21)
                    Console.WriteLine("Dealer bust. You won!");
                else if (player.ValueHand() == dealer.ValueHand())
                    Console.WriteLine("Draw.");
                else if (player.ValueHand() > dealer.ValueHand())
                    Console.WriteLine("You won!");
                else
                    Console.WriteLine("You lost.");
            }

            //ask if they want to play again
            do
            {
                Console.Write("\nDo you want to play again? [Y/N]");
                playAgain = Console.ReadKey().KeyChar;
                Console.WriteLine();
                if (char.ToLower(playAgain) != 'y' && char.ToLower(playAgain) != 'n')
                    Console.WriteLine("Please enter Y or N.");

            } while (char.ToLower(playAgain) != 'y' && char.ToLower(playAgain) != 'n');

            //if playing again, reset hands and gameover
            if (char.ToLower(playAgain) == 'y')
            {
                player.Clear();
                dealer.Clear();
                gameOver = false;
                gotBlackjack = false;
            }
        } while (char.ToLower(playAgain) == 'y');
    }

    public class Card
    {
        public int Type { get; set; }

        public override string ToString()
    {
        string returnStr;

        if (Type > 1 && Type < 11)
            returnStr = Type.ToString();
        else if (Type == 11)
            returnStr = "Jack";
        else if (Type == 12)
            returnStr = "Queen";
        else if (Type == 13)
            returnStr = "King";
        else
            returnStr = "Ace";

        return returnStr;
    }

        public int Value()
        {
            int val;

            //check for jack, queen, king, if so return 10
            //if not return type
            if (Type < 10)
                val = Type;
            else
                val = 10;

            return val;
        }
    };

    public class HandOfCards
    {
        List<Card> hand = new List<Card>();

        private static Random randNum = new Random();

        //clears the hand
        public void Clear()
        {
            hand.Clear();
        }

        //deals a new card on the hand
        public void Draw()
        {
            hand.Add(new Card() { Type = randNum.Next(1, 13) });
        }

        //adds up the hand
        public int ValueHand()
        {
            int value = 0;
            int aceCount = 0;

            foreach(Card c in hand)
            {
                if (c.Type == 1)
                {
                    value += 11;
                    aceCount++;
                }
                else
                    value += c.Value();
            }

            //if we have aces, and counting them all as 11 has us bust,
            //try setting to 1
            while (aceCount > 0 && value > 21)
            {
                value -= 10; //swap an ace from 11 to 1
                aceCount--;
            }

            return value;
        }

        //print function with flag on whether or not to print dealer first card
        public void Print(bool hideDealerCard)
        {
            foreach (Card c in hand)
            {
                if (c == hand.First() && hideDealerCard)
                    Console.Write("*\t");
                else
                    Console.Write(c.ToString() + '\t');
            }
        }

        //determines if a hand has been dealt blackjack (only run with two cards dealt)
        public bool IsBlackjack()
        {
            bool blackjack = false;
            int handValue = hand.First().Value() + hand.Last().Value();

            //if the hand is a face card or ten and an ace, the value will be 11
            if (handValue == 11 && (hand.First().Value() == 10 || hand.Last().Value() == 10))
                blackjack = true;

            return blackjack;
        }

        //plays the dealers hand
        public void DealerPlay()
        {
            int handValue = ValueHand();

            while (handValue < 17 || (handValue == 17 && hand.Contains(new Card { Type = 1 })))
            {
                Draw();
                handValue = ValueHand();
            }
        }
    }
}
