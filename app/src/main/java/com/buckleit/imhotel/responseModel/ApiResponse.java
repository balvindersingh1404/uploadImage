
package com.buckleit.imhotel.responseModel;

public class ApiResponse {

private Boolean error;
private String message;
private Integer code;
private Data data;

public Boolean getError() {
return error;
}

public void setError(Boolean error) {
this.error = error;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public Integer getCode() {
return code;
}

public void setCode(Integer code) {
this.code = code;
}

public Data getData() {
return data;
}

public void setData(Data data) {
this.data = data;
}

}