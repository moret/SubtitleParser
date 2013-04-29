package com.globo.subtitleparser;

public class SubtitleItem {
    public long start;
    public long end;
    public String text;

    public SubtitleItem(long start, long end, String text) {
        this.start = start;
        this.end = end;
        this.text = text;
    }

    @Override
    public String toString() {
        return start + "; " + end + "; " + text;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.toString().equals(obj.toString());
    }
}
