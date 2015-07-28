package org.datacleaner.extension.sendjmsmessage;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.datacleaner.extension.sendjmsmessage.SendMessageToJMSQueueAnalyzer;
import org.junit.Test;

public class SendMessageToJMSQueueAnalyzerTest extends TestCase {

    SendMessageToJMSQueueAnalyzer analyzer = new SendMessageToJMSQueueAnalyzer();
    
    @Test
    public void testBuildMessageBodyFromTemplate() {
        String template = "this is a and this is b";
        String[] keys = {"a", "b"};
        List<Object> values = new ArrayList<Object>();
        values.add("new a");
        values.add("new b");
        String messageBody = analyzer.buildMessageBodyFromTemplate(template, keys, values);
        assertEquals(messageBody, "this is new a new and this is new b");

        values = new ArrayList<Object>();
        values.add(Integer.valueOf(1));
        values.add(Integer.valueOf(2));
        messageBody = analyzer.buildMessageBodyFromTemplate(template, keys, values);
        assertEquals(messageBody, "this is 1 1nd this is 2");

    }
}
