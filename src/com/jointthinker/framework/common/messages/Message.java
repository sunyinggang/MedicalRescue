package com.jointthinker.framework.common.messages;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Message {

	private static final String BUNDLE_NAME = Message.class.getName();

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);

    private Message() {
    }

    public static String formatString(String key, String[] args) {
    	
        return MessageFormat.format(getString(key), (Object)args);
    }

    public static String formatString(String key, String arg0, String arg1) {
    	
        return MessageFormat.format(getString(key), (Object)new String[] { arg0, arg1 });
    }

    public static String formatString(String key, String arg) {
        return MessageFormat.format(getString(key), (Object)new String[] { arg });
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
    
}
