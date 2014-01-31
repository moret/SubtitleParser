package com.globo.subtitleparser;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.globo.subtitleparser.SubtitleItem;

public class SRTParser {
    Pattern TRIM_PATTERN = Pattern.compile("^\\s+|\\s+$");
    Pattern BLOCK_PATTERN = Pattern.compile("\n?(\r?\n){2}");
    Pattern LINES_PATTERN = Pattern.compile("\r?\n");
    Pattern TIME_PATTERN = Pattern.compile(
            "([0-9]{2}):([0-9]{2}):([0-9]{2}),([0-9]*)?"
    );

    public Set<SubtitleItem> subtitleItems;

    public SRTParser(String subtitlesText) {
        subtitleItems = new HashSet<SubtitleItem>();

        String[] blocks = BLOCK_PATTERN.split(subtitlesText);
        for (int i = 0; i < blocks.length; i++) {
            String block = blocks[i];
            if (isValidBlock(block)) {
                parseSubtitleBlockAndAddItem(block);
            }
        }
    }

    public String getByTime(long time) {
        for (SubtitleItem subtitleItem : subtitleItems) {
            if (time >= subtitleItem.start && time <= subtitleItem.end)
                return subtitleItem.text;
        }

        return null;
    }

    void parseSubtitleBlockAndAddItem(String block) {
        String[] blockLines = LINES_PATTERN.split(block);
        if (blockLines.length >= 2) {
            Matcher timeMatcher = TIME_PATTERN.matcher(blockLines[1]);
            Long start = null;
            Long end = null;

            if (timeMatcher.find())
                start = milliesFromTimestamp(timeMatcher);
            if (timeMatcher.find())
                end = milliesFromTimestamp(timeMatcher);

            if (start != null && end != null) {
                StringBuilder sb = new StringBuilder();
                for (int i = 2; i < blockLines.length; i++) {
                    sb.append(blockLines[i]).append("\n");
                }
                if (sb.length() > 1)
                    sb.deleteCharAt(sb.length() - 1);
                subtitleItems.add(new SubtitleItem(
                        start.longValue(),
                        end.longValue(),
                        sb.toString()
                ));
            }
        }
    }

    boolean isValidBlock(String value) {
        return trim(value) != "";
    }

    String trim(String value) {
        return TRIM_PATTERN.matcher(value).replaceAll("");
    }

    Long milliesFromTimestamp(Matcher match) {
        long millis = Long.parseLong(match.group(1)) * 60 * 60 * 1000;
        millis += Long.parseLong(match.group(2)) * 60 * 1000;
        millis += Long.parseLong(match.group(3)) * 1000;

        if (!match.group(4).isEmpty())
            millis += Long.parseLong(match.group(4));

        return new Long(millis);
    }
}
