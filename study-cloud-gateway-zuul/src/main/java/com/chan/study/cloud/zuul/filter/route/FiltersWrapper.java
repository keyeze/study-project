package com.chan.study.cloud.zuul.filter.route;

import java.util.List;

public interface FiltersWrapper {
    List<RouteFilter> getFilters(String key);
}
