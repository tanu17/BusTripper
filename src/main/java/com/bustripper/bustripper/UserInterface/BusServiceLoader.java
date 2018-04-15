package com.bustripper.bustripper.UserInterface;
import android.content.Context;
import com.bustripper.bustripper.Entity.BusService;
import java.util.List;
import android.support.v4.content.AsyncTaskLoader;

public class BusServiceLoader extends AsyncTaskLoader<List<BusService>> {
    private static final String LOG_TAG = BusService.class.getName();
    private String mUrl1, mUrl2, userServiceNo;
    /**
     * Constructs a new {@link BusServiceLoader}.
     * @param context of the activity
     * @param url1 to load data from bus route api
     * @param url2 to load data from bus arrival api
     */
    public BusServiceLoader(Context context, String url1, String url2, String userServiceNo) {
        super(context);
        mUrl1 = url1;
        mUrl2 = url2;
        this.userServiceNo = userServiceNo; }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<BusService> loadInBackground() {
        if (mUrl1 == null || mUrl2 == null) { return null; }
        // Perform the network request, parse the response, and extract a list of bus service.
        List<BusService> busServices = QueryUtils.fetchBusServiceData(mUrl1, mUrl2, userServiceNo);
        return busServices;
    }
}
