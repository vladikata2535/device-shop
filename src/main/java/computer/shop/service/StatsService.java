package computer.shop.service;

import computer.shop.models.view.StatsView;

public interface StatsService {
    void onRequest();
    StatsView getStats();
}
