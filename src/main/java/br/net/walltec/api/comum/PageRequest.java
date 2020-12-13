/**
 * 
 */
package br.net.walltec.api.comum;

/**
 * @author wallace
 *
 */
public class PageRequest implements Pageable {
	
	
	private int page;
	private int pageSize;

	public PageRequest(Integer page, Integer pageSize) {
		this.page = page != null ? page.intValue() : 0;
		this.pageSize = pageSize != null ? pageSize.intValue() : 10;
		
	}	
	
	/* (non-Javadoc)
	 * @see br.net.walltec.api.comum.Pageable#getSizePerPage()
	 */
	@Override
	public int getSizePerPage() {
		// TODO Auto-generated method stub
		return pageSize;
	}

	/* (non-Javadoc)
	 * @see br.net.walltec.api.comum.Pageable#getPage()
	 */
	@Override
	public int getPage() {
		// TODO Auto-generated method stub
		return page;
	}

	//private boolean ultimaPagina = pagina == qtdPaginas -1;
	public final Pageable emptyPageable() {
		return new PageRequest( 0, Integer.MAX_VALUE );
	}
	
}
