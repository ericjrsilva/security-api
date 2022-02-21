package util;

/**
 * Represents a response in API
 */
public class SecurityAPIResponse {
    private String message;
    private Integer status;
    private Object data;

    public SecurityAPIResponse(HTTPStatus status, String message) {
        this.status = status.httpCode;
        this.message = message;
    }

    public SecurityAPIResponse(HTTPStatus status, String message, Object data) {
        this.message = message;
        this.status = status.httpCode;
        this.data = data;
    }

    public SecurityAPIResponse(HTTPStatus status) {
        this.status = status.httpCode;
        this.message = status.httpMessage;
    }

    /**
     * API message.
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * API message.
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * API status.
     * Additional HTTP code to give status about API from HTTP server response.
     * A server communication can be OK (HTTP 200) but our API cannot be answering OK.
     * @return
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * API status.
     * Additional HTTP code to give status about API from HTTP server response.
     * A server communication can be OK (HTTP 200) but our API cannot be answering OK.
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * API response data.
     * All data as a result of a API call comes here.
     * @return
     */
    public Object getData() {
        return data;
    }

    /**
     * API response data.
     * All data as a result of a API call comes here.
     * @param data
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * Status HTTP to use in addition to HTTP response API.
     */
    public enum HTTPStatus {
        OK(200, "OK"),
        BAD_REQUEST(400, "Bad Request"),
        INTERNAL_SERVER_ERROR(500, "Internal server error"),
        INVALID_JSON_POST(501, "Invalid json post");
        private final Integer httpCode;
        private final String httpMessage;

        HTTPStatus(Integer httpCode, String httpMessage) {
            this.httpCode = httpCode;
            this.httpMessage = httpMessage;

        }

        /**
         * HTTP code.
         * @return
         */
        public Integer getHttpCode() {
            return httpCode;
        }

        /**
         * HTTP message.
         * @return
         */
        public String getHttpMessage() {
            return httpMessage;
        }
    }
}
