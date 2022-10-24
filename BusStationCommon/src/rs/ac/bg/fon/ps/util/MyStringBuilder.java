/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.util;

/**
 *
 * @author Vladimir Jecić
 */
public class MyStringBuilder {

    public static StringBuilder sb;

    public synchronized static String concatStrings(String... argStrings) {
        if (sb == null) {
            sb = new StringBuilder();
        } else {
            sb.setLength(0);
        }
        for (String string : argStrings) {
            sb.append(string);
        }
        return sb.toString();
    }
}
