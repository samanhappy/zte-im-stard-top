package com.zte.im.util;

import java.util.UUID;

public class IdGen {
    
    private IdGen() {
    }

    public static String generateId() {
        long l = UUID.randomUUID().getLeastSignificantBits();
        return getS((long) (Math.random() * 100) % 62) + getS(l);
    }

    public static String getS(long l) {
        char[] buff = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
                'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
                'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
                'V', 'W', 'X', 'Y', 'Z' };
        long tmp = Math.abs(l);
        StringBuilder sb = new StringBuilder();
        while (tmp > 0) {
            int t = (int) (tmp % 62);
            tmp = tmp / 62;
            sb.append(buff[t]);
        }

        return sb.reverse().toString();
    }

    public static long getL(String id) {
        char[] buff = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
                'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
                'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
                'V', 'W', 'X', 'Y', 'Z' };
        String indexes = new String(buff);
        long tmp = 0L;
        StringBuilder sb = new StringBuilder(id);
        sb.reverse();

        for (int i = 0; i < sb.length(); i++) {
            int index = indexes.indexOf(sb.charAt(i));
            tmp = tmp + index * (long) Math.pow(62, i);
        }

        return tmp;
    }

}
