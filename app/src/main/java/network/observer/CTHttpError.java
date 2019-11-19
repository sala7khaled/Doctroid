package network.observer;

public class CTHttpError extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private String msg;
    private double statusCode;

    public CTHttpError(String msg, double statusCode) {
        super(msg);
        this.msg = msg;
        this.statusCode = statusCode;
    }

    public String getErrorMsg() {
        return msg;
    }

    public void setErrorMsg(String msg) {
        this.msg = msg;
    }

    public int getStatusCode() {
        return (int) statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
