package com.suresh.code;

import java.util.List;

public class HandComparator implements PokerComparator{
    @Override
    public void compare(Hand left, Hand right) {
        int index = 1;
        int size = left.getCheckSize();
        System.out.println("size = " + size);
        boolean foundWinner = check (left,right,index,size);
        if (!foundWinner) {
            System.out.println("None");
        }
    }

    private boolean check(Hand leftHand, Hand rightHand, int index, int size) {
        boolean flag = false;
        boolean left = leftHand.getChecks().get(index);
        boolean right = rightHand.getChecks().get(index);
        System.out.println("index = " + index+ " , Check-Parameter:"+leftHand.getCheckMap().get(index));

        if(left && !right){
            System.out.println("left");
            flag =true;
        }

        if(!left && right){
            System.out.println("right");
            flag =true;
        }

        if(left && right){
            flag = true;
            printWinner(leftHand.getRanks(), rightHand.getRanks());
        }
        return flag;
    }

    private void printWinner(List<Integer> a, List<Integer> b) {
        int x = compareRanks(a,b);
        if(x==-1){
            System.out.println("Left");
        }
        if(x==1){
            System.out.println("Right");
        }
        if(x==0){
            System.out.println("None");
        }
    }

    private int compareRanks(List<Integer> a, List<Integer> b) {
        int len = a.size();
        for (int i = 0; i < len; i++) {

            int x = a.get(i);
            int y = b.get(i);

            if(x==y){
                continue;
            }
            else if(x<y){
                return 1;
            }
            else if(x>y){
                return -1;
            }
        }
        return 0;
    }
}
