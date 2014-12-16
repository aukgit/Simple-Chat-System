/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestingCode;

/**
 *
 * @author Alim
 */
public class FieldTest extends ParentFieldTest {
    private static final long serialVersionUID = 1L;

	private String pri;
	protected String pro;
	public String pub;

	public FieldTest() {
	}

	public FieldTest(String pri, String pro, String pub) {
		this.pri = pri;
		this.pro = pro;
		this.pub = pub;
	}

}