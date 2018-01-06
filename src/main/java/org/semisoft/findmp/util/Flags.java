package org.semisoft.findmp.util;

public class Flags {
    private int flags = 0;

    public void setFlag(int flag) {
        flags |= flag;
    }

    public boolean hasFlag(int flag) {
        return (flags & flag) == flag;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }
}