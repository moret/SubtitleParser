package com.globo.subtitleparsertest;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.globo.subtitleparser.SRTParser;
import com.globo.subtitleparser.SubtitleItem;

@RunWith(JUnit4.class)
public class SRTParserTest {

    @Test
    public void parserTest() throws Exception {
        Set<SubtitleItem> expectedSubtitleItems = new HashSet<SubtitleItem>();
        expectedSubtitleItems.add(new SubtitleItem(
                51413, 53310, "Era uma vez,")
        );
        expectedSubtitleItems.add(new SubtitleItem(
                53310, 56585, "em um reino mágico\nchamado Andalasia,")
        );
        expectedSubtitleItems.add(new SubtitleItem(
                56585, 59482, "uma rainha malvada.")
        );
        expectedSubtitleItems.add(new SubtitleItem(
                59482, 62447, "Egoísta e cruel,\nvivia aterrorizada")
        );
        expectedSubtitleItems.add(new SubtitleItem(
                111413, 113310, "até que abriu os olhos e perguntou:")
        );
        expectedSubtitleItems.add(new SubtitleItem(
                113310, 116585, "no céu tem pão?")
        );
        expectedSubtitleItems.add(new SubtitleItem(
                116585, 118482, "")
        );
        expectedSubtitleItems.add(new SubtitleItem(
                118585, 119482, "")
        );
        expectedSubtitleItems.add(new SubtitleItem(
                119482, 122447, "E morreu.")
        );
        expectedSubtitleItems.add(new SubtitleItem(
                124482, 128447, "THE END")
        );

        File srtFile = new File("fixtures/andalasia.srt");
        byte[] contents = new byte[(int) srtFile.length()];

        RandomAccessFile raf = new RandomAccessFile(srtFile, "r");
        raf.readFully(contents);
        raf.close();

        String subtitles = new String(contents, "UTF-8");
        SRTParser parser = new SRTParser(subtitles);

        Assert.assertTrue(
                "not the same subtitles",
                expectedSubtitleItems.containsAll(parser.subtitleItems)
        );
    }

}
