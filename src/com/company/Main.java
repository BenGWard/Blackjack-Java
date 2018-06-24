package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        HandOfCards player = new HandOfCards();
        HandOfCards dealer = new HandOfCards();
        boolean gameOver = false;
        boolean gotBlackjack = false;
        char playAgain;
        char hitOrStay;
        Scanner kb = new Scanner (System.in);

        System.out.println("Casino Blackjack");
        System.out.println("Dealer hits on soft 17");
        System.out.println();

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
                System.out.println("You got blackjack! You won!");
                gameOver = true;
                gotBlackjack = true;
            }
            else if (dealer.IsBlackjack())
            {
                System.out.println("Dealer got blackjack. You lost.");
                gameOver = true;
                gotBlackjack = true;
            }

            while (!gameOver)
            {
                System.out.println("Dealer:\t");
                dealer.Print(true);
                System.out.println();
                System.out.println("Player:\t");
                player.Print(false);
                System.out.println();

                do
                {
                    System.out.print("Do you want to hit or stay? [H / S]:");
                    hitOrStay = kb.next().charAt(0);
                    System.out.println();
                    if (Character.toLowerCase(hitOrStay) != 'h' && Character.toLowerCase(hitOrStay) != 's')
                        System.out.println("\nPlease enter H or S.");
                } while (Character.toLowerCase(hitOrStay) != 'h' && Character.toLowerCase(hitOrStay) != 's');

                if (Character.toLowerCase(hitOrStay) == 'h')
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
            System.out.println("\nFinal Results:\n");
            System.out.println("Dealer: " + dealer.ValueHand());
            dealer.Print(false);
            System.out.println('\n');
            System.out.println("Player: " + player.ValueHand());
            player.Print(false);
            System.out.println('\n');

            //if player or dealer got a blackjack, game was already decided
            if (!gotBlackjack)
            {
                //declare winner
                if (player.ValueHand() > 21)
                    System.out.println("You bust. You lose.");
                else if (dealer.ValueHand() > 21)
                    System.out.println("Dealer bust. You won!");
                else if (player.ValueHand() == dealer.ValueHand())
                    System.out.println("Draw.");
                else if (player.ValueHand() > dealer.ValueHand())
                    System.out.println("You won!");
                else
                    System.out.println("You lost.");
            }

            //ask if they want to play again
            do
            {
                System.out.print("\nDo you want to play again? [Y/N]");
                playAgain = kb.next().charAt(0);
                System.out.println();
                if (Character.toLowerCase(playAgain) != 'y' && Character.toLowerCase(playAgain) != 'n')
                    System.out.println("Please enter Y or N.");

            } while (Character.toLowerCase(playAgain) != 'y' && Character.toLowerCase(playAgain) != 'n');

            //if playing again, reset hands and gameover
            if (Character.toLowerCase(playAgain) == 'y')
            {
                player.Clear();
                dealer.Clear();
                gameOver = false;
                gotBlackjack = false;
            }
        } while (Character.toLowerCase(playAgain) == 'y');
    }
}
