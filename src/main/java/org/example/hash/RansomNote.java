package org.example.hash;

import java.util.HashMap;
import java.util.Map;

/**
 * Given two strings ransomNote and magazine, return true if ransomNote can be constructed by using the letters from magazine and false otherwise.
 * <p>
 * Each letter in magazine can only be used once in ransomNote.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: ransomNote = "a", magazine = "b"
 * Output: false
 */
public class RansomNote {
    public static boolean canConstruct(String ransomNote, String magazine) {
        if(ransomNote.length()>magazine.length()) return false;

        Map<Character, Integer> stringMap = new HashMap<Character,Integer>();
        for(char c: magazine.toCharArray()) {
            stringMap.put(c, stringMap.getOrDefault(c, 0)+1);
        }

        for (char c : ransomNote.toCharArray()) {
            if (stringMap.getOrDefault(c,0)==0) {
                return false;
            }
            stringMap.put(c, stringMap.get(c)-1);
        }
        return true;
    }
}
