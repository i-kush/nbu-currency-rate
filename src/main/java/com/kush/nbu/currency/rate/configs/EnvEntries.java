package com.kush.nbu.currency.rate.configs;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.HashMap;
import java.util.Map;

public class EnvEntries {

    private static final Map<String, Object> ENV_ENTRIES_VALUES = new HashMap<>();

    private EnvEntries() {
        throw new UnsupportedOperationException(String.format("No instance of %s for you", EnvEntries.class.getSimpleName()));
    }

    public static Object get(String aEntryName) {
        Object lResult;

        if (!ENV_ENTRIES_VALUES.containsKey(aEntryName)) {
            InitialContext lInitialContext = null;
            Context lContext;

            try {
                lInitialContext = new InitialContext();
                lContext = (Context) lInitialContext.lookup("java:comp/env");

                lResult = lContext.lookup(aEntryName);

                ENV_ENTRIES_VALUES.put(aEntryName, lResult);
            } catch (NamingException aE) {
                throw new RuntimeException(aE);
            } finally {
                if (lInitialContext != null) {
                    try {
                        lInitialContext.close();
                    } catch (NamingException lException) {
                        lException.printStackTrace();
                    }
                }
            }
        } else {
            lResult = ENV_ENTRIES_VALUES.get(aEntryName);
        }

        return lResult;
    }

    public static String getString(String aEntryName) {
        return (String) get(aEntryName);
    }

    public static Boolean getBoolean(String aEntryName) {
        return (Boolean) get(aEntryName);
    }

    public static Integer getInteger(String aEntryName) {
        return (Integer) get(aEntryName);
    }
}
