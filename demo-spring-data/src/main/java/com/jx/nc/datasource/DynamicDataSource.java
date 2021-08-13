package com.jx.nc.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        Object obj = DynamicDataSourceContextHolder.getDataSourceType();
        return obj;
    }
}
