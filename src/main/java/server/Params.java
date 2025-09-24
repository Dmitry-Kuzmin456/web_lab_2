package server;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Params {
    private byte x;
    private BigDecimal y;
    private byte r;

    private final ArrayList<Byte> xValues = new ArrayList<>(
            Arrays.asList((byte)-5, (byte)-4, (byte)-3, (byte)-2, (byte)-1,
                    (byte)0, (byte)1, (byte)2, (byte)3)
    );
    private final ArrayList<Byte> rValues = new ArrayList<>(
            Arrays.asList((byte)1, (byte)2, (byte)3, (byte)4, (byte)5)
    );

    private final Map<String, String> errors = new HashMap<>();

    public Params(String sx, String sy, String sr) {

        if (sx == null) errors.put("x", "missing");
        else {
            try {
                x = Byte.parseByte(sx);
                if (!xValues.contains(x)) errors.put("x", "must be one of [-2, -1.5, ..., 2]");
            } catch (NumberFormatException e) {
                errors.put("x", "must be one of [-2, -1.5, ..., 2]");
            }
            catch (Exception e) {
                errors.put("x", "must be a number");
            }
        }


        if (sy == null) errors.put("y", "missing");
        else {
            try {
                y = new BigDecimal(sy);
                if (y.compareTo(BigDecimal.valueOf(-5)) <= 0 || y.compareTo(BigDecimal.valueOf(5)) >= 0)
                    errors.put("y", "must be between (-5; 5)");
            } catch (Exception e) {
                errors.put("y", "must be a number");
            }
        }


        if (sr == null) errors.put("r", "missing");
        else {
            try {
                r = Byte.parseByte(sr);
                if (!rValues.contains(r)) errors.put("r", "must be in [1, 2, 3, 4, 5]");
            } catch (NumberFormatException e) {
                errors.put("r", "must be in [1, 2, 3, 4, 5]");
            }
            catch (Exception e) {
                errors.put("r", "must be a number");
            }
        }
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public String getErrors() {
        Map<String, String> result = new HashMap<>();

        result.put("x", errors.getOrDefault("x", "ok"));
        result.put("y", errors.getOrDefault("y", "ok"));
        result.put("r", errors.getOrDefault("r", "ok"));

        StringBuilder res = new StringBuilder();
        if (!(result.get("x").equals("ok"))) {
            res.append("x: ").append(result.get("x")).append("\n");
        }
        if (!(result.get("y").equals("ok"))) {
            res.append("y: ").append(result.get("y")).append("\n");
        }
        if (!(result.get("r").equals("ok"))) {
            res.append("r: ").append(result.get("r"));
        }
        return res.toString();
    }

    public float getX() { return x; }
    public BigDecimal getY() { return y; }
    public byte getR() { return r; }
}
