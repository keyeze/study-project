package com.chan;

import java.util.Map;

/**
 * CtChanTest
 *
 * @author Flandre
 */
public class CtChanMain {

    public static void main(String[] args) {
        int n = 20;
        System.out.println(!new String(new char[n]).matches("^.?$|^(..+?)\\1+$"));

    }
}
