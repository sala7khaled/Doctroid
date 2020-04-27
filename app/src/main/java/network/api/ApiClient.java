package network.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import app.App;
import app.Constants;
import helpers.Logger;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utilities.InternetUtilities;
import utilities.PrefManager;

public class ApiClient {

    static final String BASE_URL = Constants.BASE_URL;
    private static Retrofit retrofit = null;

    private static final long CACHE_SIZE = 20 * 1024 * 1024; // 10 MB
    private static OkHttpClient.Builder clientBuilder;

    public static HashMap<String, String> getHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("time-zone", TimeZone.getDefault().getID());

        if (PrefManager.getToken(App.getContext()) != null) {
            headers.put("x-auth-token", PrefManager.getToken(App.getContext()));
        }

        return headers;
    }

    static {
        clientBuilder = new OkHttpClient
                .Builder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .cache(new Cache(App.getContext().getCacheDir(), CACHE_SIZE))
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    okhttp3.Headers headers = request.headers();

                    Logger.instance().v("", "️");
                    Logger.instance().v("⬇️ :Request Start", "⬇️");
                    Logger.instance().v("🌎 Request URL", request.url());
                    Logger.instance().v("🧩 Request Headers ", headers.toString());
                    Logger.instance().v("📥 Request Body", (request.body() != null) ? bodyToString(request.body()) : "NULL/Empty");
                    Logger.instance().v("⬆️ :Request End", "⬆️");
                    Logger.instance().v("", "️");

                    if (InternetUtilities.isConnected(App.getApplication())) {
                        request = request.newBuilder().header("Cache-Control", "public, max-age=" + 50000).build();
                    } else {
                        request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                    }
                    return chain.proceed(request);
                });
    }

    private static String bodyToString(final RequestBody request) {
        try {
            Buffer buffer = new Buffer();
            request.writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "BodyToString: " + e.getMessage();
        }
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(clientBuilder.build())
                    .build();
        }
        return retrofit;
    }
}
