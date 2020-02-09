package com.cards.nightsafe;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        Queries query = new Queries();
        System.out.println(query.FriendNamesQuery(3));
        System.out.println(query.GroupFindQuery(1, 10));
    }
}
