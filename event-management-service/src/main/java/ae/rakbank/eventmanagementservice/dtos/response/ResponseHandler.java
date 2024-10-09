
package ae.rakbank.eventmanagementservice.dtos.response;


import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Map;

public class ResponseHandler<T> {

     public Response<T> getResponseWithData(T data) {
        Response<T> response;
        if ((data instanceof ArrayList || data == null) && (!(data instanceof ArrayList) || ObjectUtils.isEmpty((ArrayList)data))) {
            response = new Response<>();
            response.setMessage("No Data was retrieved/found");
            return response;
        } else {
            response = new Response<>();
            response.setData(data);
            response.setMessage("Success");
            return response;
        }
    }


}
