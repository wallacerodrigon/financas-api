/**
 * 
 */
package br.net.walltec.api.comum;

/**
 * @author wallace
 *
 */
public class EmptyPageable implements Pageable {

	private static final EmptyPageable INSTANCE = new EmptyPageable();
	
	private EmptyPageable() {
		
	}
	
	public static final EmptyPageable getInstance() {
		return INSTANCE;
	}
	
	/* (non-Javadoc)
	 * @see br.net.walltec.api.comum.Pageable#getSizePerPage()
	 */
	@Override
	public int getSizePerPage() {
		// TODO Auto-generated method stub
		return Integer.MAX_VALUE;
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.comum.Pageable#getPage()
	 */
	@Override
	public int getPage() {
		// TODO Auto-generated method stub
		return 0;
	}

}
