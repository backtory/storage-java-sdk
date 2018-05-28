package Responses;

import java.io.IOException;
import java.util.List;

public class BacktoryResponse<ResponseType> {
    public static final int FILE_NOT_FOUND = -1;
    public static final int OK = 200;
    public static final int OK_CREATED = 201;
    public static final int BAD_REQUEST = 400;
    public static final int UNATHORIZED = 401;
    public static final int NOT_FOUND = 404;
    public static final int EXCEPTION_FAILED = 417;
    public static final int INTERNAL_SERVER_ERROR = 500;

    private BacktoryException backtoryException;
    private int statusCode;
    private ResponseType body;
    private String message;
    private List<String> responseList;

    public BacktoryResponse(int statusCode, ResponseType body) throws IOException {
        this.statusCode = statusCode;
        this.body = body;
        backtoryException = new BacktoryException(this.statusCode, this.body.toString());
    }

    public BacktoryResponse(int statusCode, List<String> responseList) {
        this.statusCode = statusCode;
        this.body = body;
        this.responseList = responseList;
    }

    public boolean isSuccessful(){
        return statusCode >= 200 && statusCode < 300;
    }

    public ResponseType getBody() {
        return body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public BacktoryException getError() {
        return backtoryException;
    }

    public List<String> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<String> responseList) {
        this.responseList = responseList;
    }
}
