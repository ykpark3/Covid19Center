package org.androidtown.covid19center.Search.List;

import java.util.Comparator;

public class DistanceComparator implements Comparator<ClinicItem> {
    @Override
    public int compare(ClinicItem o1, ClinicItem o2) {
        double firstValue = o1.getClinicDistance();
        double secondValue = o2.getClinicDistance();

        if (firstValue < secondValue) {
            return -1;
        } else if (firstValue > secondValue) {
            return 1;
        } else {
            return 0;
        }
    }
}
