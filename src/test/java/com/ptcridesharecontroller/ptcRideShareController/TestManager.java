package com.ptcridesharecontroller.ptcRideShareController;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestManager {
    public static final String connectionURL = "jdbc:sqlserver://jdsteltz.database.windows.net:1433;database=EnterpriseApps;user=jdsteltz@jdsteltz;password=Dawson226!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

    public static String getConnectionurl() {
        return connectionURL;
    }

    /**
     * Returns a given object as a json string
     * @param obj the object that needs to be converted to JSON
     * @return the Object in JSON string format
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //public static int searchDatabaseForRideBy
}
