package Responses;

import java.util.ArrayList;
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
    private String body;
    private String message;
    private List<ResponseType> responseList;

    public BacktoryResponse(int statusCode, List<ResponseType> responseList, BacktoryException backtoryException) {
        this.statusCode = statusCode;
        this.responseList = responseList;
        this.backtoryException = backtoryException;
    }

//    public BacktoryResponse(int statusCode, String body) throws IOException {
//        this.statusCode = statusCode;
//        this.body = body;
//        backtoryException = new BacktoryException(this.statusCode, this.body);
//    }
//
//    public BacktoryResponse(int statusCode, List<ResponseType> responseList) {
//        this.statusCode = statusCode;
//        this.responseList = responseList;
//    }
//
//    public BacktoryResponse(int statusCode, String body, List<ResponseType> responseList) throws IOException {
//        this.statusCode = statusCode;
//        this.body = body;
//        this.responseList = responseList;
//        backtoryException = statusCode > 200 && statusCode < 300 ? null : new BacktoryException(this.statusCode, this.body);
//    }

    public boolean isSuccessful(){
        return statusCode >= 200 && statusCode < 300;
    }

    public String getBody() {
        return body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public BacktoryException getError() {
        return backtoryException;
    }

    public List<ResponseType> getResponseList() {
        return responseList == null ? new ArrayList<>() : responseList;
    }
}
