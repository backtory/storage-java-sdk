package Internal;

import Responses.BacktoryException;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

import java.io.IOException;
import java.lang.annotation.Annotation;

public class ErrorUtils {

    public static BacktoryException parseError(Response<?> response) {
        Converter<ResponseBody, BacktoryException> converter =
                ServiceGenerator.retrofit().responseBodyConverter(BacktoryException.class, new Annotation[0]);
        BacktoryException error;
        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new BacktoryException();
        }
        return error;
    }
}
