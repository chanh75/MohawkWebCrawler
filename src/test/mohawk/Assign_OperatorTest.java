package test.mohawk;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mohawk.webcrawler.ScriptCompiler;
import com.mohawk.webcrawler.lang.ScriptContext;

public class Assign_OperatorTest {

    @Test
    public void test() {
        try {
            String s = "var i3 var st var dec var b00l " +
                         "i3 = 68 st = \"howdy cowboy\" dec = 4.5602 b00l = true " +
                         "print \"{i3},{st},{dec},{b00l}\"";

            StringBuffer textOut = new StringBuffer();

            (new ScriptCompiler())
                .compile(s)
                .executeWithContext(ScriptContext.createWithDefaultConfig())
                .outputTo(textOut);

            assertEquals(textOut.toString(), "Print: 68,howdy cowboy,4.5602,true" + System.lineSeparator());
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }
        fail();
    }

}
