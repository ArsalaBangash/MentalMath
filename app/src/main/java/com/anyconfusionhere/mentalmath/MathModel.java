package com.anyconfusionhere.mentalmath;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by Arsala on 2016-12-09.
 */

public class MathModel {

    private HashMap<Integer,Integer> exponentMap;
    private int operator, a, b, c, answer;
    private Random rand;

    public MathModel() {
        exponentMap = new HashMap<Integer, Integer>();
        exponentMap.put(2,5);
        exponentMap.put(3,3);
        exponentMap.put(4,2);
        exponentMap.put(5,2);
        exponentMap.put(6,2);
        exponentMap.put(7,1);
        exponentMap.put(8,1);
        exponentMap.put(9,1);
        exponentMap.put(10,2);
        exponentMap.put(11,1);
        exponentMap.put(12,1);
        exponentMap.put(13,1);
        rand = new Random();
        operator = rand.nextInt(5);
    }


    public SpannableStringBuilder newProblem() {

        operator = rand.nextInt(72);
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        if (operator <= 10) {
            a = rand.nextInt(21) + 1;
            b = rand.nextInt(21) + 1;
            answer = a + b;
            stringBuilder.append(Integer.toString(a) + "+" + Integer.toString(b) + " =");
        } else if (operator > 10 && operator <= 20) {
            a = rand.nextInt(21) + 1;
            b = rand.nextInt(21) + 1;
            answer = a - b;
            stringBuilder.append(Integer.toString(a) + "-" + Integer.toString(b) + " =");

        } else if (operator > 20 && operator <= 30) {
            a = rand.nextInt(12) + 1;
            b = rand.nextInt(12) + 1;
            answer = a * b;
            stringBuilder.append(Integer.toString(a) + "*" + Integer.toString(b) + " =");
        } else if (operator > 30 && operator <= 40) {
            answer = rand.nextInt(12) + 1;
            b = rand.nextInt(12) + 1;
            a = answer * b;
            stringBuilder.append(Integer.toString(a) + "\u00F7" + Integer.toString(b) + " =");
        } else if (operator > 40 && operator <= 49) {
            a = rand.nextInt(10) + 3;
            b = rand.nextInt(exponentMap.get(a)) + 2;
            answer = ((int) Math.pow((double) a, (double) b));
            stringBuilder.append(String.valueOf(a) + String.valueOf(b) + " =");
            if (stringBuilder.length() == 4) {
                stringBuilder.setSpan(new SuperscriptSpan(), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                stringBuilder.setSpan(new RelativeSizeSpan(0.75f), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if (stringBuilder.length() == 5) {
                if (a - 10 >= 0) {
                    stringBuilder.setSpan(new SuperscriptSpan(), 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    stringBuilder.setSpan(new RelativeSizeSpan(0.75f), 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    stringBuilder.setSpan(new SuperscriptSpan(), 1, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    stringBuilder.setSpan(new RelativeSizeSpan(0.75f), 1, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        } else if (operator > 49 && operator <= 58) {
            answer = rand.nextInt(10) + 3;
            b = rand.nextInt(exponentMap.get(answer)) + 2;
            a = (int) Math.pow((double)answer, (double)b);
            if(b == 2) {
                stringBuilder.append("\u221A" + String.valueOf(a));
            } else {
                stringBuilder.append(String.valueOf(b) + "\u221A" + String.valueOf(a));
                stringBuilder.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                stringBuilder.setSpan(new RelativeSizeSpan(0.5f), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        } else if (operator > 58 && operator <= 67) {
            b = rand.nextInt(10) + 3;
            answer = rand.nextInt(exponentMap.get(b)) + 2;
            a = ((int) Math.pow((double) b, (double) answer));
            stringBuilder.append("log" + String.valueOf(b) + "(" + String.valueOf(a) + ")=");
            if (String.valueOf(b).length() == 1) {
                stringBuilder.setSpan(new RelativeSizeSpan(0.35f), 3, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if (String.valueOf(b).length() == 2) {
                stringBuilder.setSpan(new RelativeSizeSpan(0.35f), 3, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        } else if (operator > 67 && operator <= 71) {
            a = rand.nextInt(20) + 1;
            b = rand.nextInt(20) + 1;
            answer = a % b;
            stringBuilder.append(Integer.toString(a) + "\uFF05" + Integer.toString(b) + " =");
        }
        return stringBuilder;
    }

    public int getAnswer() {
        return answer;
    }
}
