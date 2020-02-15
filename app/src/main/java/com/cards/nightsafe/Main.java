package com.cards.nightsafe;

public class Main {
    public static void main(String[] args) {
        Queries query = new Queries();
        Queries.ChangeStatusQuery(1, 50, "2");
        System.out.println(Queries.FriendNamesQuery(MainActivity.username));
        System.out.println(Queries.GroupFindQuery(1, 10));
    }
}
