package net.nicovrc.dev;

import java.util.Date;

public class LogData {

    private Date logDate;
    private String URL;
    private String ErrorMessage;
    private String URLType;

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public String getURLType() {
        return URLType;
    }

    public void setURLType(String URLType) {
        this.URLType = URLType;
    }
}
