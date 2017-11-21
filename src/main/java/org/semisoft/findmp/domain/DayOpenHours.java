package org.semisoft.findmp.domain;

import java.util.ArrayList;

public class DayOpenHours
{
    ArrayList<Interval> intervals = new ArrayList<>();



    public ArrayList<Interval> getIntervals()
    {
        return intervals;
    }

    public void addInterval(Interval interval)
    {
        intervals.add(interval);
    }
}
