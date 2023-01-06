package model;

/** This is a functional interface used to create the lambda expression for checking the User's locale.
 * The User's locale is checked in order to see if the resource bundle needs to be utilized to change the language in
 * the application to French. */
public interface LEInterface1 {
    /** This is the sole abstract method of this interface. */
    abstract void checkLocale();
}

