package com.crossover.trial.weather;
//CR: Should be moved to a different package for e.g. com.crossover.trial.weather.model
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
//CR: Builder Design Pattern should be correctly implemented
/**
 * A collected point, including some information about the range of collected values
 *
 * @author code test administrator
 */
public class DataPoint {

    public double mean = 0.0;// CR: Should be private modifier for encapsulation

    public int first = 0;// CR: Should add private and final modifier for encapsulation and for Builder pattern

    public int second = 0;// CR: Should add private and final modifier for encapsulation and for Builder pattern

    public int third = 0;// CR: Should add private and final modifier for encapsulation and for Builder pattern

    public int count = 0;// CR: Should add private and final modifier for encapsulation and for Builder pattern

    /** private constructor, use the builder to create this object */
    private DataPoint() { }
    // CR: Constructor should be private as per Builder pattern so that it is not set via constructor outside this class
    protected DataPoint(int first, int second, int mean, int third, int count) { 
        this.setFirst(first);
        this.setMean(mean);
        this.setSecond(second);
        this.setThird(third);
        this.setCount(count);
    }

    /** the mean of the observations */
    public double getMean() {
        return mean;
    }

    protected void setMean(double mean) { this.mean = mean; } // CR: Setter method not required explicitly as per Builder Design Pattern. Should be set by Builder Only.

    /** 1st quartile -- useful as a lower bound */
    public int getFirst() {
        return first;
    }

    protected void setFirst(int first) { // CR: Setter method not required explicitly as per Builder Design Pattern. Should be set by Builder Only.
        this.first = first;
    }

    /** 2nd quartile -- median value */
    public int getSecond() {
        return second;
    }

    protected void setSecond(int second) { // CR: Setter method not required explicitly as per Builder Design Pattern. Should be set by Builder Only.
        this.second = second;
    }

    /** 3rd quartile value -- less noisy upper value */
    public int getThird() {
        return third;
    }

    protected void setThird(int third) { // CR: Setter method not required explicitly as per Builder Design Pattern. Should be set by Builder Only.
        this.third = third;
    }

    /** the total number of measurements */
    public int getCount() {
        return count;
    }

    protected void setCount(int count) { // CR: Setter method not required explicitly as per Builder Design Pattern. Should be set by Builder Only.
        this.count = count;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    public boolean equals(Object that) {
        return this.toString().equals(that.toString());
    }

    static public class Builder {
        int first;// CR: Should be marked as with 'private' and 'final' modifiers for encapsulation.
        int mean;// CR: Should be marked as with 'private' and 'final' modifiers for encapsulation.
        int median;// CR: Should be marked as with 'private' and 'final' modifiers for encapsulation.
        int last;// CR: Should be marked as with 'private' and 'final' modifiers for encapsulation.
        int count;// CR: Should be marked as with 'private' and 'final' modifiers for encapsulation.

        public Builder() { }

        public Builder withFirst(int first) {
            first= first;// CR: Should add 'this' keyword to the left hand side. 
            return this;
        }

        public Builder withMean(int mean) {
            mean = mean;// CR: Should add 'this' keyword to the left hand side.
            return this;
        }

        public Builder withMedian(int median) {
            median = median;// CR: Should add 'this' keyword to the left hand side.
            return this;
        }

        public Builder withCount(int count) {
            count = count;// CR: Should add 'this' keyword to the left hand side.
            return this;
        }

        public Builder withLast(int last) {
            last = last;// CR: Should add 'this' keyword to the left hand side.
            return this;
        }

        public DataPoint build() {
            return new DataPoint(this.first, this.mean, this.median, this.last, this.count);
        }
    }
}
