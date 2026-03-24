package com.stockcompare.domain.model;

/** Visitor — unauthenticated user. Can search, retrieve prices, compare, view graphs. */
public class Visitor extends User {
    public Visitor() { super("guest", "Guest"); }
}
