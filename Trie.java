import java.util.ArrayList;

public class Trie {
    public ArrayList<TrieNode> children;
    public int currentPhase = 0;
    int parentPhase = 0;

    public Trie() {
        children = new ArrayList<TrieNode>();
    }

    public int find(String value) {
        //System.err.println("Finding:" + value);
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

    public int insert(String value) {
        char checkVal = value.charAt(0);
        for (TrieNode child : children) {
            if (child.getValue() == checkVal) {
                TrieNode inserted = child.insert(value.substring(1), currentPhase, this.parentPhase);
                this.currentPhase = inserted.getPhase();
                return inserted.getParent();
            }
        }
        TrieNode newNode = new TrieNode(checkVal, currentPhase, this.parentPhase);
        children.add(newNode);
        this.currentPhase = newNode.getPhase();
        return newNode.getParent();
    }
}

class TrieNode {
    ArrayList<TrieNode> children = new ArrayList<TrieNode>();
    int phase;
    char value;
    int parentPhase;

    public int getParent() {
        return this.parentPhase;
    }

    public TrieNode(char value, int phase, int parentPhase) {
        //System.out.println("Creating new node: Parent: " + parentPhase + " Current: " + (phase+1) + " with value: " + value);
        this.parentPhase = parentPhase;
        this.phase = phase+1;
        this.value = value;
    }

    public int getPhase() {
        return this.phase;
    }

    public char getValue() {
        return this.value;
    }

    public int Find(String value) {
        for (TrieNode child : children) {
            if (child.getValue() == value.charAt(0)) {
                String nextEncode = value.substring(1);
                if (nextEncode.length() == 0){   
                    return child.getPhase();
                }
                return child.Find(value.substring(1));
            }
        }
        return -1;
    }

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