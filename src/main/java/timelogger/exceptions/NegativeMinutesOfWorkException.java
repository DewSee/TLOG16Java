package timelogger.exceptions;


public class NegativeMinutesOfWorkException extends Exception {


    public NegativeMinutesOfWorkException() {
    }


    public NegativeMinutesOfWorkException(String msg) {
        super(msg);
    }
}
