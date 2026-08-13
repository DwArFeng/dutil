package com.dwarfeng.dutil.basic.sdk.io;

import com.dwarfeng.dutil.basic.impl.io.StringOutputStream;
import com.dwarfeng.dutil.basic.sdk.io.CT.MultiLineType;
import com.dwarfeng.dutil.basic.sdk.io.CT.OutputType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CTTest {

    @AfterEach
    public void tearDown() {
        CT.setOutputType(OutputType.FULL_DATE);
        CT.setMutiLineType(MultiLineType.TYPE_2);
    }

    @Test
    public void testTrace() {
        CT.setOutputType(OutputType.FULL_DATE);
        CT.trace("中国智造，惠及全球。");
        CT.trace("the quick fox jumps over a lazy dog.");
        CT.setOutputType(OutputType.HALF_DATE);
        CT.trace("中国智造，惠及全球。");
        CT.trace("the quick fox jumps over a lazy dog.");
        CT.setOutputType(OutputType.NO_DATE);
        CT.trace("中国智造，惠及全球。");
        CT.trace("the quick fox jumps over a lazy dog.");

        // ----------------------------------多行文本测试----------------------------------
        CT.setOutputType(OutputType.FULL_DATE);
        CT.setMutiLineType(MultiLineType.TYPE_1);
        CT.trace("中国智造，惠及全球。" + "\r\n" + "the quick fox jumps over a lazy dog.");
        CT.setMutiLineType(MultiLineType.TYPE_2);
        CT.trace("中国智造，惠及全球。" + "\r\n" + "the quick fox jumps over a lazy dog.");
        CT.setMutiLineType(MultiLineType.TYPE_3);
        CT.trace("中国智造，惠及全球。" + "\r\n" + "the quick fox jumps over a lazy dog.");

        Exception e = new Exception();
        OutputStream sout = new StringOutputStream();
        PrintStream ps = new PrintStream(sout);
        e.printStackTrace(ps);
        ps.close();

        CT.setMutiLineType(MultiLineType.TYPE_1);
        CT.trace(sout.toString());
        CT.setMutiLineType(MultiLineType.TYPE_2);
        CT.trace(sout.toString());
        CT.setMutiLineType(MultiLineType.TYPE_3);
        CT.trace(sout.toString());
    }

    @Test
    public void testOutputType() {
        CT.setOutputType(OutputType.FULL_DATE);
        assertEquals(OutputType.FULL_DATE, CT.getOutputType());
        CT.setOutputType(OutputType.NO_DATE);
        assertEquals(OutputType.NO_DATE, CT.getOutputType());
    }

    @Test
    public void testMultiLineType() {
        CT.setMutiLineType(MultiLineType.TYPE_3);
        assertEquals(MultiLineType.TYPE_3, CT.getMultiLineType());
        CT.setMutiLineType(MultiLineType.TYPE_2);
        assertEquals(MultiLineType.TYPE_2, CT.getMultiLineType());
    }
}
