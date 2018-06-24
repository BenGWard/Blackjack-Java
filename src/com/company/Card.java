package com.company;

import java.util.Random;

public class Card {
    int type;
    Random randNum = new Random();

    public Card(){
        type = randNum.nextInt(13) + 1;
    }

    public String toString() {
        String returnStr;

        if (type > 1 && type < 11)
            returnStr = Integer.toString(type);
        else if (type == 11)
            returnStr = "Jack";
        else if (type == 12)
            returnStr = "Queen";
        else if (type == 13)
            returnStr = "King";
        else
            returnStr = "Ace";

        return returnStr;
    }

    public int GetType()
    {
        return type;
    }

    public int Value()
    {
        int val;

        //check for jack, queen, king, if so return 10
        //if not return type
        if (type < 10)
            val = type;
        else
            val = 10;

        return val;
    }
}
