
/*
 * Lleyton Damon - 1585670
 * Jake Postlewaight - 1590698
 */

import java.util.ArrayList;

public class Trie {
    // Create an array list of children nodes
    public ArrayList<TrieNode> children;
    // The current max phase we are at
    public int MaxPhase = 0;
    // phase the parent is at
    int parentPhase = 0;

    public Trie() {
        children = new ArrayList<TrieNode>();
    }

    /**
     * Searches for a given value in the trie data structure, starting from the
     * current node.
     *
     * @param value the String value to search for
     * @return an integer representing the phase value of the node that matches the
     *         given value, or -1 if the value is not found
     */
    public int find(String value) {
        for (TrieNode child : children) {
            if (child.getValue() == value.charAt(0)) {
                String nextEncode = value.substring(1);
                if (nextEncode.length() == 0) {
                    return child.getPhase();
                }
                return child.Find(value.substring(1));
            }
        }
        return -1;
    }

    public int getMaxPhase() {
        return this.MaxPhase;
    }

    /**
     * Inserts a new value into the trie data structure, starting from the current
     * node.
     *
     * @param value the String value to insert into the trie
     * @return an integer representing the parent phase value of the newly inserted
     *         node
     */
    public int insert(String value) {
        char checkVal = value.charAt(0);
        for (TrieNode child : children) {
            if (child.getValue() == checkVal) {
                TrieNode inserted = child.insert(value.substring(1), this.MaxPhase, this.parentPhase);
                this.MaxPhase = inserted.getPhase();
                return inserted.getParent();
            }
        }
        TrieNode newNode = new TrieNode(checkVal, this.MaxPhase, this.parentPhase);
        children.add(newNode);
        this.MaxPhase = newNode.getPhase();
        return newNode.getParent();
    }
}

/*
 * Node class for all the children of the trie class
 */
class TrieNode {
    ArrayList<TrieNode> children = new ArrayList<TrieNode>();
    int phase;
    char value;
    int parentPhase;

    public int getParent() {
        return this.parentPhase;
    }

    /*
     * Creates a new trie node with a value of value and a phase of phase
     */
    public TrieNode(char value, int phase, int parentPhase) {
        this.parentPhase = parentPhase;
        this.phase = phase + 1;
        this.value = value;
    }

    public int getPhase() {
        return this.phase;
    }

    public char getValue() {
        return this.value;
    }
    /**
     * Searches for a given value in the trie data structure, starting from the
     * current node.
     *
     * @param value the String value to search for
     * @return an integer representing the phase value of the node that matches the
     *         given value, or -1 if the value is not found
     */
    public int Find(String value) {
        for (TrieNode child : children) {
            if (child.getValue() == value.charAt(0)) {
                String nextEncode = value.substring(1);
                if (nextEncode.length() == 0) {
                    return child.getPhase();
                }
                return child.Find(value.substring(1));
            }
        }
        return -1;
    }
    /**
     * Inserts a new value into the trie data structure, starting from the current
     * node.
     *
     * @param value the String value to insert into the trie
     * @return an integer representing the parent phase value of the newly inserted
     *         node
     */
    public TrieNode insert(String value, int currentPhase, int parentPhase) {
        char checkVal = value.charAt(0);
        for (TrieNode child : children) {
            if (child.getValue() == checkVal) {
                return child.insert(value.substring(1), currentPhase, this.phase);
            }
        }
        TrieNode newNode = new TrieNode(checkVal, currentPhase, this.phase);
        children.add(newNode);
        return newNode;
    }
}