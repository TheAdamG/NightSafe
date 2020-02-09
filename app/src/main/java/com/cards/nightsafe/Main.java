package com.cards.nightsafe;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        Queries query = new Queries();
        query.ChangeStatusQuery(1, 50, "2");
        System.out.println(query.FriendNamesQuery(MainActivity.username));
        System.out.println(query.GroupFindQuery(1, 10));
    }
}
