package com.crossover.trial.weather;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
//CR: Should be moved to a different package for e.g. com.crossover.trial.weather.model
/**
 * Basic airport information.
 *
 * @author code test administrator
 */
public class AirportData {

    /** the three letter IATA code */
    String iata;// CR: Should add private final modifier for encapsulation

    /** latitude value in degrees */
    double latitude;// CR: Should add private final modifier for encapsulation

    /** longitude value in degrees */
    double longitude;// CR: Should add private final modifier for encapsulation

    public AirportData() { } // CR: Instead of having getters and setters , they should be removed to make it immutable class since it is used as a key in map in RestWeatherQueryEndpoint.java

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    public boolean equals(Object other) {
        if (other instanceof AirportData) {
            return ((AirportData)other).getIata().equals(this.getIata());
        }

        return false;
    }// CR: Should be adding corresponding hashCode() methods as well so that it can be used in a Map
}
