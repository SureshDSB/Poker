package com.suresh.code;

public class Card {

    private String rank;
    private String suit;
    private int rankVal;

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public int getRankVal() {
        return rankVal;
    }

    public void setRankVal(int rankVal) {
        this.rankVal = rankVal;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Card{");
        sb.append("rank='").append(rank).append('\'');
        sb.append(", suit='").append(suit).append('\'');
        sb.append(", rankVal=").append(rankVal);
        sb.append('}');
        return sb.toString();
    }
}
