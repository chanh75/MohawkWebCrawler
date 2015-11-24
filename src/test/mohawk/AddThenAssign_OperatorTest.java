package test.mohawk;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mohawk.webcrawler.ScriptCompiler;
import com.mohawk.webcrawler.lang.ScriptContext;

public class AddThenAssign_OperatorTest {

    @Test
    public void testRun() {
        try {
            String s = "vardef c 2 " +
                         "c += 1 " +
                         "print \"{c}\"";

            StringBuffer textOut = new StringBuffer();

            (new ScriptCompiler())
                .compile(s)
                .executeWithContext(ScriptContext.createWithDefaultConfig())
                .outputTo(textOut);

            assertEquals(textOut.toString(), "Print: 3" + System.lineSeparator());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
