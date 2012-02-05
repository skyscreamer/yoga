package org.skyscreamer.jsonassert;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 * Date: 1/29/12
 * Time: 3:05 PM
 */
public class JSONCompareResult {
    private boolean _success;
    private String _message;

    public JSONCompareResult() {
        this(true, null);
    }

    private JSONCompareResult(boolean success, String message) {
        _success = success;
        _message = message == null ? "" : message;
    }

    public boolean passed() {
        return _success;
    }
    
    public boolean failed() {
        return !_success;
    }
    
    public String getMessage() {
        return _message;
    }
    
    protected void fail(String message) {
        _success = false;
        if (_message.length() == 0) {
            _message = message;
        }
        else {
            _message += " ; " + message;
        }
    }

    protected void fail(String field, Object expected, Object actual) {
        StringBuffer message= new StringBuffer();
        message.append(field);
        message.append("\nExpected: ");
        message.append(expected + "");
        message.append("\n     got: ");
        message.append(actual + "");
        message.append("\n");
        fail(message.toString());
    }
}
