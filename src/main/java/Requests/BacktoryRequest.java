package Requests;

import Responses.BacktoryResponse;

import java.io.IOException;

interface BacktoryRequest<T> {

    BacktoryResponse<T> perform() throws IOException;
}
