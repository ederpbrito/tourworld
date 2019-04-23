package br.com.bexs.tourworld.domain.model.exception;

public class TourException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public TourException(String msg) {
		super(msg);
	}

}
