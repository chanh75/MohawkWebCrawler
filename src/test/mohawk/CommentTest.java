package test.mohawk;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.mohawk.webcrawler.CommentRemover;

public class CommentTest {

    @Test
    public void testRemove() {
        //fail("Not yet implemented");

        String[] s = new String[] {
            "vardef test1\r\n",
            "// this is a line comment\r\n",
            "vardef test2\r\n",
            "var test3 /* this is a block comment */ for() \r\n",
            "/*\r\n",
            "* huge block comment\r\n",
            "*\r\n",
            "*/\r\ncontinue\r\n",
            "the end"
        };

        CommentRemover cr = new CommentRemover();

        StringBuffer sb = new StringBuffer();
        for (String line : s) {
            sb.append(cr.remove(line));
        }

        String check = "vardef test1\r\nvardef test2\r\nvar test3  for() \r\n\r\ncontinue\r\nthe end";

        assertEquals(check, sb.toString());
    }

}
