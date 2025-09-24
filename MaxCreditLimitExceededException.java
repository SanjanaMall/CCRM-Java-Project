package edu.ccrm.domain;

public class MaxCreditLimitExceededException extends Exception {
	private static final long serialVersionUID = 1L;
    public MaxCreditLimitExceededException(String message) {
        super(message);
    }
}