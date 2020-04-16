package com.suresh.code;

import java.util.*;
import java.util.stream.Collectors;

public class Hand {

    private String s;
    private Card[] cards;
    private final boolean isRoyal;
    private final boolean isStFlush;
    private final boolean isFullHouse;
    private final boolean isFlush;
    private final boolean isStraight;
    private final boolean isFourKind;
    private final boolean isThreeKind;
    private final boolean isTwoPair;
    private final boolean isOnePair;
    private final boolean isHighCard;
    private final boolean isSameSuit;
    private final int checkSize;
    private final Map<String,Integer> valMap;
    private final Map<String,Integer> countMap;
    private final List<Integer> ranks;
    private final List<Boolean> checks;
    private final Map<Integer,String> checkMap;


    public int getCheckSize() {
        return checkSize;
    }

    public List<Boolean> getChecks() {
        return checks;
    }

    public List<Integer> getRanks() {
        return ranks;
    }

    public Map<Integer, String> getCheckMap() {
        return checkMap;
    }

    Hand(String s){
        this.s=s;
        String[] parts= s.split(" ");
        int len = parts.length;
        cards = new Card[len];
        for (int i = 0; i < len; i++) {
            Card card =new Card();
            String x= parts[i];
            card.setRank(Character.toString(x.charAt(0)));
            card.setSuit(Character.toString(x.charAt(1)));
            cards[i]=card;
        }
        //System.out.println("cards = " + Arrays.toString(cards));
        isSameSuit = isSameSuit();
        valMap = buildValueMap();
        countMap = buildCountMap();
        ranks = buildRanksSorted();
        isRoyal = isRoyal();
        isStFlush = isStraightFlush();
        isFullHouse = isFullHouse();
        isFlush = isFlush();
        isStraight = isStraight();
        boolean[] kindType= processKindType();
        isFourKind = kindType[0];
        isThreeKind = kindType[1];
        isTwoPair = kindType[2];
        isOnePair = kindType[3];
        isHighCard = kindType[4];
        checks= buildChecks();
        checkSize = checks.size();
        checkMap = buildCheckMap();

    }

    private Map<Integer, String> buildCheckMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(0,"isRoyal");
        map.put(1,"isStFlush");
        map.put(2,"isFourKind");
        map.put(3,"isFullHouse");
        map.put(4,"isFlush");
        map.put(5,"isStraight");
        map.put(6,"isThreeKind");
        map.put(7,"isTwoPair");
        map.put(8,"isOnePair");
        return map;
    }


    private List<Boolean> buildChecks() {
        Boolean[] checksArray = {
                isRoyal,
                isStFlush,
                isFourKind,
                isFullHouse,
                isFlush,
                isStraight,
                isThreeKind,
                isTwoPair,
                isOnePair
        };
        return Arrays.asList(checksArray);
    }

    private boolean[] processKindType() {

        boolean isFourKind = false;
        boolean isThreeKind = false;
        boolean isTwoPair = false;
        boolean isOnePair = false;
        boolean isHighCard = false;

        List<Integer> counts = new ArrayList<>();

        for(String key:countMap.keySet()){
            counts.add(countMap.get(key));
        }
        Collections.sort(counts);

        //four
        if(countMap.keySet().size()==2){
            if(counts.get(0)==1 && counts.get(1)==4){
                isFourKind = true;
            }
        }

        //three
        if(countMap.keySet().size()==3){
            if(counts.get(0)==1 && counts.get(1)==1 && counts.get(2)==3){
                isThreeKind = true;
            }
        }

        //two
        if(countMap.keySet().size()==3){
            if(counts.get(0)==1 && counts.get(1)==2 && counts.get(2)==2){
                isTwoPair = true;
            }
        }

        //one
        if(countMap.keySet().size()==4){
            if(counts.get(0)==1 && counts.get(1)==1 && counts.get(2)==1 && counts.get(3)==2){
                isOnePair = true;
            }
        }

        //High
        if(countMap.keySet().size()==5){
            if(counts.get(0)==1 && counts.get(1)==1 && counts.get(2)==1 && counts.get(3)==1
                    && counts.get(4)==1 && !isSameSuit && !isStraight){
                isHighCard = true;
            }
        }

        boolean[] kindType = {
                isFourKind,
                isThreeKind,
                isTwoPair,
                isOnePair,
                isHighCard
        };

        return kindType;
    }

    private boolean isStraight() {
        return isConsecutive();
    }

    private boolean isFlush() {
        return isSameSuit;
    }

    private boolean isFullHouse() {
        Map<String,Integer> fullHouse = countMap;
        if(fullHouse.keySet().size()==2){
            for(String key:fullHouse.keySet()){
                Integer c = fullHouse.get(key);
                if(c==3){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isStraightFlush() {
        return isStraight() && isFlush();
    }

    public boolean isConsecutive(){
        Integer previous = ranks.get(0);
        for (int i = 1; i < ranks.size(); i++) {
            Integer current = ranks.get(i);
            if(!(current-previous==1)){
                return  false;
            }
        }
        return true;
    }



    private boolean isRoyal() {
        // T = ten = 10
        // J = Joker
        //Q = Queen
        //K = King
        // A = Ace
        String[] data = {"T","J","Q","K","A"};
        Set<String> royal = Arrays.stream(data).map(x->x).collect(Collectors.toSet());

        // check for same suit
        if(!isSameSuit){
            return false;
        }

        for (int i = 0; i < cards.length; i++) {
            royal.remove(cards[i]);
        }

        if (!(royal.size()==0)) {
            return false;
        }

        return true;
    }

    private List<Integer> buildRanksSorted() {
        List<Integer> ranks = new ArrayList<>();
        for (int i = 0; i < cards.length; i++) {
            ranks.add(valMap.get(cards[i].getRank()));
        }
        Collections.sort(ranks);
        return ranks;
    }

    private Map<String, Integer> buildCountMap() {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < cards.length; i++) {
            String rank = cards[i].getRank();
            if(map.containsKey(rank)){
                Integer count = map.get(rank);
                map.put(rank, count+1);
            }
            else{
                map.put(rank, 1);
            }
        }
        return map;
    }

    private Map<String, Integer> buildValueMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("2",2);
        map.put("3",3);
        map.put("4",4);
        map.put("5",5);
        map.put("6",6);
        map.put("7",7);
        map.put("8",8);
        map.put("9",9);
        map.put("T",10);
        map.put("J",11);
        map.put("Q",12);
        map.put("K",13);
        map.put("A",14);
        return map;
    }

    private boolean isSameSuit() {
        Set<String> suits = Arrays.stream(cards).map(x->x.getSuit()).collect(Collectors.toSet());
        return suits.size()==1 ? true : false;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Hand[");
        sb.append("cards=").append(Arrays.toString(cards));
        sb.append(", isRoyal=").append(isRoyal);
        sb.append(", isStFlush=").append(isStFlush);
        sb.append(", isFullHouse=").append(isFullHouse);
        sb.append(", isFlush=").append(isFlush);
        sb.append(", isStraight=").append(isStraight);
        sb.append(", isFourKind=").append(isFourKind);
        sb.append(", isThreeKind=").append(isThreeKind);
        sb.append(", isTwoPair=").append(isTwoPair);
        sb.append(", isOnePair=").append(isOnePair);
        sb.append(", isHighCard=").append(isHighCard);
        sb.append(", isSameSuit=").append(isSameSuit);
        sb.append(", ranks=").append(ranks);
        sb.append(']');
        return sb.toString();
    }
}
