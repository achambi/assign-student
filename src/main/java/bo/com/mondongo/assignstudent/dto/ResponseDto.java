package bo.com.mondongo.assignstudent.dto;

public class ResponseDto {
    private final boolean error;
    private final String message;

    public ResponseDto(boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
