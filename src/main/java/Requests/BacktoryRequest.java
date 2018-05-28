package Requests;

import Responses.BacktoryResponse;
import okhttp3.OkHttpClient;

import java.io.IOException;

interface BacktoryRequest<T> {
    OkHttpClient client = new OkHttpClient();
    final String masterAccessToken = null;
    final String xBacktoryStorgeId = null;

    String url = "http://storage.backtory.com/";

    BacktoryResponse<T> perform() throws IOException;
}
