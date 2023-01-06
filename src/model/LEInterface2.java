package model;

/** This is a functional interface used to create the lambda expression for flushing the login_activity.txt file.
 * It's necessary to flush this file so that login data is only recorded for each occurrence of the application being launched. */
public interface LEInterface2 {
    /** This is the sole abstract method of this interface. */
    abstract void flushTxtFile(String loginFileName);
}
