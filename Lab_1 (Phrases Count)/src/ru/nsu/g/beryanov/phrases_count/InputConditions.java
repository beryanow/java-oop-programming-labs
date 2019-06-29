package ru.nsu.g.beryanov.phrases_count;

public class InputConditions {
    private int n;
    private int m;
    private String s;

    String get_s() {
        return s;
    }

    int get_n() {
        return n;
    }

    int get_m() {
        return m;
    }

    void resolve_conditions(String[] args) {
        n = 2;
        m = 2;
        try {
            switch (args.length) {
                case 0:
                    break;
                case 1:
                    s = args[0];
                    break;
                case 3:
                    if (args[0].equals("-n"))
                        n = Integer.parseInt(args[1]);
                    else if (args[0].equals("-m"))
                        m = Integer.parseInt(args[1]);
                    else throw new IllegalArgumentException("Incorrect input");
                    s = args[2];
                    if ((n < 0) || (m < 0))
                        throw new IllegalArgumentException("Negative values are not allowed");
                    break;
                case 5:
                    if (args[0].equals("-n")) {
                        n = Integer.parseInt(args[1]);
                        m = Integer.parseInt(args[3]);
                    } else if (args[0].equals("-m")) {
                        n = Integer.parseInt(args[3]);
                        m = Integer.parseInt(args[1]);
                    } else throw new IllegalArgumentException("Incorrect input");
                    s = args[4];
                    if ((n < 0) || (m < 0))
                        throw new IllegalArgumentException("Negative values are not allowed");
                    break;
                default: {
                    throw new IllegalArgumentException("Incorrect input");
                }
            }
        } catch (IllegalArgumentException err) {
            System.err.println(err);
            System.exit(1);
        }
    }
}