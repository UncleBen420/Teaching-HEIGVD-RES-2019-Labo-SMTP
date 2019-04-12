package prank;

/**
 * Represente un prank, qui est constitue d'un sujet et d'un message
 */
public class Prank {
	String subject, prank;

	/**
	 * Recupere le sujet du prank.
	 * @return le sujet du prank
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Defini le sujet du prank.
	 * @param subject le sujet du prank
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Recupere le message du prank.
	 * @return le message du prank
	 */
	public String getPrank() {
		return prank;
	}

	/**
	 * Defini le message du prank.
	 * @param prank le message du prank
	 */
	public void setPrank(String prank) {
		this.prank = prank;
	}

	@Override
	public String toString() {
		return "Prank [subject=" + subject + ", prank=" + prank + "]";
	}
}