package com.suresh.code;

public class Main {

    public static void main(String[] args) {
        //String s = "6D 7H AH 7S QC 6H 2D TD JD AS";
        //String s = "JH 5D 7H TC JS JD JC TS 5S 7S";
        String s = "2D 3D 4D 5D 6D 3S 4S 5S 6S 7S";
	    int len = s.length()/2;

	    String left = s.substring(0,len);
	    String right = s.substring(len+1);

	    Hand lHand = new Hand(left);
	    Hand rHand = new Hand(right);

        System.out.println("lHand = " + lHand);
        System.out.println("rHand = " + rHand);

        HandComparator hc = new HandComparator();
        hc.compare(lHand, rHand);

    }
}
