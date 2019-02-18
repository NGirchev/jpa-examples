package ru.girchev.examples.jpa.domain.chapter7;

/**
 * @author Girchev N.A.
 * Date: 12.02.2019
 */
public class Dto {
    long count, max, min, sum;
    double avg;

    public Dto(long count, long max, long min, long sum, double avg) {
        this.count = count;
        this.max = max;
        this.min = min;
        this.sum = sum;
        this.avg = avg;
    }

    @Override
    public String toString() {
        return "Dto{" +
                "count=" + count +
                ", max=" + max +
                ", min=" + min +
                ", sum=" + sum +
                ", avg=" + avg +
                '}';
    }
}
