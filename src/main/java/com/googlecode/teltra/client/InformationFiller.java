package com.googlecode.teltra.client;

public interface InformationFiller {

    void fillAccountInfo(String[] data, int index);

    void fillUsageInfo(String[] data, int index);

    BigpondUsageInformation getFilledInformation();
}
