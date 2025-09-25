package server;

public class Result {
    private final byte x;
    private final String y;
    private final byte r;
    private final boolean hit;
    private final long execTime;
    private final String currentTime;

    public Result(byte x, String y, byte r, boolean hit, long execTime, String currentTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
        this.execTime = execTime;
        this.currentTime = currentTime;
    }

    public byte getX() { return x; }
    public String getY() { return y; }
    public byte getR() { return r; }
    public boolean isHit() { return hit; }
    public long getExecTime() { return execTime; }
    public String getCurrentTime() { return currentTime; }
}
