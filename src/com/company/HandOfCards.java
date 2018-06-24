package com.company;

import java.util.LinkedList;
import java.util.List;

public class HandOfCards {
    List<Card> hand = new LinkedList<Card>();

    //clears the hand
    public void Clear()
    {
        hand.clear();
    }

    //deals a new card on the hand
    public void Draw()
    {
        hand.add(new Card());
    }

    //adds up the hand
    public int ValueHand()
    {
        int value = 0;
        int aceCount = 0;

        for(Card c : hand)
        {
            if (c.GetType() == 1)
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
    public void Print(boolean hideDealerCard)
    {
        for (Card c : hand)
        {
            if (c == hand.get(0) && hideDealerCard)
                System.out.print("*\t");
            else
                System.out.print(c.toString() + '\t');
        }
    }

    //determines if a hand has been dealt blackjack (only run with two cards dealt)
    public boolean IsBlackjack()
    {
        boolean blackjack = false;
        int handValue = hand.get(0).Value() + hand.get(1).Value();

        //if the hand is a face card or ten and an ace, the value will be 11
        if (handValue == 11 && (hand.get(0).Value() == 10 || hand.get(1).Value() == 10))
            blackjack = true;

        return blackjack;
    }

    public boolean HasAce()
    {
        boolean ace = false;

        for (Card c : hand)
        {
            if (c.GetType() == 1)
                ace = true;
        }

        return ace;
    }

    //plays the dealers hand
    public void DealerPlay()
    {
        int handValue = ValueHand();

        while (handValue < 17 || (handValue == 17 && this.HasAce()))
        {
            Draw();
            handValue = ValueHand();
        }
    }
}
