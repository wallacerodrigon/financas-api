/**
 * 
 */
package br.net.walltec.api.comum;

/**
 * @author wallace
 *
 */
public interface Pageable {
	
	int getSizePerPage();
	
	int getPage();
	
	default Pageable emptyPageable() {
		return new PageRequest(0, Integer.MAX_VALUE);
	}
}
