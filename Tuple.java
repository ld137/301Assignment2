import java.lang.reflect.Constructor;

public class Tuple {
    
    private int line = 0;
    private int prefix = 0;
    private Byte suffix = null;
    
    public Tuple(int line, int prefix, Byte suffix){
        this.line = line;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public int getLine() {
        return this.line;
    }

    public void setLine(int x){
        this.line = x;
    }

    public int getprefix() {
        return this.prefix;
    }

    public void setprefix(int y){
        this.prefix = y;
    }

    public Byte getsuffix() {
        return this.suffix;
    }

    public void setsuffix(byte z){
        this.suffix = z;
    }

}
