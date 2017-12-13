
package com.buckleit.imhotel.responseModel;

import com.buckleit.imhotel.requestModel.FileUpdateData;

public class UpdateApiResponse {

private Boolean error;
private String message;
private Integer code;
private FileUpdateData data;

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

public FileUpdateData getData() {
return data;
}

public void setData(FileUpdateData data) {
this.data = data;
}


}