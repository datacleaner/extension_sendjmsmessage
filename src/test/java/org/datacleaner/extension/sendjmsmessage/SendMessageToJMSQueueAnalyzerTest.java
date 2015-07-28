package org.datacleaner.extension.sendjmsmessage;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

public class SendMessageToJMSQueueAnalyzerTest extends TestCase {

    SendMessageToJMSQueueAnalyzer analyzer = new SendMessageToJMSQueueAnalyzer();

    @Test
    public void testBuildMessageBodyFromTemplate() {
        String template = "a;b";
        String[] keys = { "a", "b" };
        List<Object> values = new ArrayList<Object>();
        values.add("foo");
        values.add("bar");
        String messageBody = analyzer.buildMessageBodyFromTemplate(template, keys, values);
        assertEquals("a;b" + "\n" + "foo;bar", messageBody);

        values = new ArrayList<Object>();
        values.add(Integer.valueOf(1));
        values.add(Integer.valueOf(2));
        messageBody = analyzer.buildMessageBodyFromTemplate(template, keys, values);
        assertEquals(messageBody, "a;b\n1;2");

    }
}
