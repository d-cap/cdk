/*  $RCSfile$
 *  $Author$
 *  $Date$
 *  $Revision$
 *
 *  Copyright (C) 1997-2003  The Chemistry Development Kit (CDK) project
 *
 *  Contact: cdk-devel@lists.sourceforge.net
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2.1
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.openscience.cdk.test.tools;

import org.openscience.cdk.*;
import org.openscience.cdk.tools.*;
import org.openscience.cdk.io.*;
import org.openscience.cdk.renderer.*;
import org.openscience.cdk.layout.*;
import org.openscience.cdk.templates.*;
import org.openscience.cdk.geometry.*;
import org.openscience.cdk.aromaticity.*;
import org.openscience.cdk.smiles.*;

import java.io.*;
import javax.vecmath.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.util.*;
import java.awt.*;

import junit.framework.*;

/**
 * @author     steinbeck
 * @created    2003-02-20
 */
public class SaturationCheckerTest extends TestCase
{

	SaturationChecker satcheck = null;
	boolean standAlone = false;


	/**
	 *  Constructor for the SaturationCheckerTest object
	 *
	 *@param  name  Description of the Parameter
	 */
	public SaturationCheckerTest(String name)
	{
		super(name);
	}

    /**
    *  The JUnit setup method
    */
    public void setUp() {
        try {
            satcheck = new SaturationChecker();
        } catch (Exception e) {
            fail();
        }
    }

	/**
	 * A unit test suite for JUnit
	 *
	 * @return    The test suite
	 */
    public static Test suite() {
        TestSuite suite = new TestSuite(SaturationCheckerTest.class);
        suite.addTest(HydrogenAdderTest.suite());
        return suite;
	}


	/**
	 *  A unit test for JUnit
	 */
	public void testAllSaturated()
	{
		// test methane with explicit hydrogen
		Molecule m = new Molecule();
		Atom c = new Atom("C");
		Atom h1 = new Atom("H");
		Atom h2 = new Atom("H");
		Atom h3 = new Atom("H");
		Atom h4 = new Atom("H");
		m.addAtom(c);
		m.addAtom(h1);
		m.addAtom(h2);
		m.addAtom(h3);
		m.addAtom(h4);
		m.addBond(new Bond(c, h1));
		m.addBond(new Bond(c, h2));
		m.addBond(new Bond(c, h3));
		m.addBond(new Bond(c, h4));
		assertTrue(satcheck.allSaturated(m));

		// test methane with implicit hydrogen
		m = new Molecule();
		c = new Atom("C");
		c.setHydrogenCount(4);
		m.addAtom(c);
		assertTrue(satcheck.allSaturated(m));
	}


	/**
	 *  A unit test for JUnit
	 */
	public void testIsSaturated()
	{
		// test methane with explicit hydrogen
		Molecule m = new Molecule();
		Atom c = new Atom("C");
		Atom h1 = new Atom("H");
		Atom h2 = new Atom("H");
		Atom h3 = new Atom("H");
		Atom h4 = new Atom("H");
		m.addAtom(c);
		m.addAtom(h1);
		m.addAtom(h2);
		m.addAtom(h3);
		m.addAtom(h4);
		m.addBond(new Bond(c, h1));
		m.addBond(new Bond(c, h2));
		m.addBond(new Bond(c, h3));
		m.addBond(new Bond(c, h4));
		assertTrue(satcheck.isSaturated(c, m));
		assertTrue(satcheck.isSaturated(h1, m));
		assertTrue(satcheck.isSaturated(h2, m));
		assertTrue(satcheck.isSaturated(h3, m));
		assertTrue(satcheck.isSaturated(h4, m));
	}

    /**
     * Tests wether the saturation checker considers negative
     * charges.
     */
	public void testIsSaturated_NegativelyChargedOxygen() {
		// test methane with explicit hydrogen
		Molecule m = new Molecule();
		Atom c = new Atom("C");
		Atom h1 = new Atom("H");
		Atom h2 = new Atom("H");
		Atom h3 = new Atom("H");
		Atom o = new Atom("O");
        o.setFormalCharge(-1);
		m.addAtom(c);
		m.addAtom(h1);
		m.addAtom(h2);
		m.addAtom(h3);
		m.addAtom(o);
		m.addBond(new Bond(c, h1));
		m.addBond(new Bond(c, h2));
		m.addBond(new Bond(c, h3));
		m.addBond(new Bond(c, o));
		assertTrue(satcheck.isSaturated(c, m));
		assertTrue(satcheck.isSaturated(h1, m));
		assertTrue(satcheck.isSaturated(h2, m));
		assertTrue(satcheck.isSaturated(h3, m));
		assertTrue(satcheck.isSaturated(o, m));
	}
    
    /**
     * Tests wether the saturation checker considers positive
     * charges.
     */
	public void testIsSaturated_PositivelyChargedNitrogen() {
		// test methane with explicit hydrogen
		Molecule m = new Molecule();
		Atom n = new Atom("N");
		Atom h1 = new Atom("H");
		Atom h2 = new Atom("H");
		Atom h3 = new Atom("H");
		Atom h4 = new Atom("H");
        n.setFormalCharge(+1);
		m.addAtom(n);
		m.addAtom(h1);
		m.addAtom(h2);
		m.addAtom(h3);
		m.addAtom(h4);
		m.addBond(new Bond(n, h1));
		m.addBond(new Bond(n, h2));
		m.addBond(new Bond(n, h3));
		m.addBond(new Bond(n, h4));
		assertTrue(satcheck.isSaturated(n, m));
		assertTrue(satcheck.isSaturated(h1, m));
		assertTrue(satcheck.isSaturated(h2, m));
		assertTrue(satcheck.isSaturated(h3, m));
		assertTrue(satcheck.isSaturated(h4, m));
	}

	/**
	 *  A unit test for JUnit
	 */
	public void testSaturate()
	{
		// test ethene
		Atom c1 = new Atom("C");
		c1.setHydrogenCount(2);
		Atom c2 = new Atom("C");
		c2.setHydrogenCount(2);
		Bond b = new Bond(c1, c2, 1);
		// force single bond, saturate() must fix that
		Molecule m = new Molecule();
		m.addAtom(c1);
		m.addAtom(c2);
		m.addBond(b);
		satcheck.saturate(m);
		assertTrue(2.0 == b.getOrder());
	}

    /**
     * Test sulfuric acid.
     */
    public void testBug772316() {
		// test methane with explicit hydrogen
		Molecule m = new Molecule();
		Atom s = new Atom("S");
		Atom o1 = new Atom("O");
		Atom o2 = new Atom("O");
		Atom o3 = new Atom("O");
		Atom o4 = new Atom("O");
		Atom h1 = new Atom("H");
		Atom h2 = new Atom("H");
		m.addAtom(s);
		m.addAtom(o1);
		m.addAtom(o2);
		m.addAtom(o3);
		m.addAtom(o4);
		m.addAtom(h1);
		m.addAtom(h2);
		m.addBond(new Bond(s, o1, 2));
		m.addBond(new Bond(s, o2, 2));
		m.addBond(new Bond(s, o3, 1));
		m.addBond(new Bond(s, o4, 1));
		m.addBond(new Bond(h1, o3, 1));
		m.addBond(new Bond(h2, o4, 1));
		assertTrue(satcheck.isSaturated(s, m));
		assertTrue(satcheck.isSaturated(o1, m));
		assertTrue(satcheck.isSaturated(o2, m));
		assertTrue(satcheck.isSaturated(o3, m));
		assertTrue(satcheck.isSaturated(o4, m));
		assertTrue(satcheck.isSaturated(h1, m));
		assertTrue(satcheck.isSaturated(h2, m));
    }
    
    public void testBug777529() {
      // test methane with explicit hydrogen
      Molecule m = new Molecule();
      m.addAtom(new Atom("C"));
      m.addAtom(new Atom("C"));
      m.addAtom(new Atom("C"));
      m.addAtom(new Atom("C"));
      m.addAtom(new Atom("C"));
      m.addAtom(new Atom("C"));
      m.addAtom(new Atom("C"));
      m.addAtom(new Atom("C"));
      m.addAtom(new Atom("C"));
      m.addAtom(new Atom("C"));
      m.addAtom(new Atom("C"));
      m.addAtom(new Atom("C"));
      m.addAtom(new Atom("C"));
      m.addAtom(new Atom("C"));
      m.addAtom(new Atom("C"));
      m.addAtom(new Atom("C"));
      m.addAtom(new Atom("C"));
      m.addAtom(new Atom("C"));
      m.addAtom(new Atom("C"));
      m.addAtom(new Atom("C"));
      m.addAtom(new Atom("O"));
      m.addAtom(new Atom("O"));
      m.addAtom(new Atom("F"));
      m.getAtomAt(0).setHydrogenCount(1);
      m.getAtomAt(2).setHydrogenCount(1);
      m.getAtomAt(3).setHydrogenCount(1);
      m.getAtomAt(6).setHydrogenCount(1);
      m.getAtomAt(7).setHydrogenCount(1);
      m.getAtomAt(8).setHydrogenCount(1);
      m.getAtomAt(9).setHydrogenCount(1);
      //m.getAtomAt(10).setHydrogenCount(1);
      //m.getAtomAt(12).setHydrogenCount(1);
      m.getAtomAt(14).setHydrogenCount(1);
      m.getAtomAt(15).setHydrogenCount(1);
      m.getAtomAt(17).setHydrogenCount(1);
      m.getAtomAt(18).setHydrogenCount(1);
      m.getAtomAt(19).setHydrogenCount(3);
      m.addBond(0, 1, 1);
      m.addBond(1, 2, 1);
      m.addBond(2, 3, 1);
      m.addBond(3, 4, 1);
      m.addBond(4, 5, 1);
      m.addBond(5, 6, 1);
      m.addBond(6, 7, 1);
      m.addBond(7, 8, 1);
      m.addBond(8, 9, 1);
      m.addBond(5, 10, 1);
      m.addBond(9, 10, 1);
      m.addBond(10, 11, 1);
      m.addBond(0, 12, 1);
      m.addBond(4, 12, 1);
      m.addBond(11, 12, 1);
      m.addBond(11, 13, 1);
      m.addBond(13, 14, 1);
      m.addBond(14, 15, 1);
      m.addBond(15, 16, 1);
      m.addBond(16, 17, 1);
      m.addBond(13, 18, 1);
      m.addBond(17, 18, 1);
      m.addBond(20, 16, 1);
      m.addBond(11, 21, 1);
      m.addBond(22, 1, 1);
      m.addBond(20, 19, 1);
      m.getAtomAt(0).setFlag(CDKConstants.ISAROMATIC,true);
      m.getAtomAt(1).setFlag(CDKConstants.ISAROMATIC,true);
      m.getAtomAt(2).setFlag(CDKConstants.ISAROMATIC,true);
      m.getAtomAt(3).setFlag(CDKConstants.ISAROMATIC,true);
      m.getAtomAt(4).setFlag(CDKConstants.ISAROMATIC,true);
      m.getAtomAt(12).setFlag(CDKConstants.ISAROMATIC,true);
      m.getAtomAt(5).setFlag(CDKConstants.ISAROMATIC,true);
      m.getAtomAt(6).setFlag(CDKConstants.ISAROMATIC,true);
      m.getAtomAt(7).setFlag(CDKConstants.ISAROMATIC,true);
      m.getAtomAt(8).setFlag(CDKConstants.ISAROMATIC,true);
      m.getAtomAt(9).setFlag(CDKConstants.ISAROMATIC,true);
      m.getAtomAt(10).setFlag(CDKConstants.ISAROMATIC,true);
      m.getBondAt(0).setFlag(CDKConstants.ISAROMATIC,true);
      m.getBondAt(1).setFlag(CDKConstants.ISAROMATIC,true);
      m.getBondAt(2).setFlag(CDKConstants.ISAROMATIC,true);
      m.getBondAt(3).setFlag(CDKConstants.ISAROMATIC,true);
      m.getBondAt(5).setFlag(CDKConstants.ISAROMATIC,true);
      m.getBondAt(6).setFlag(CDKConstants.ISAROMATIC,true);
      m.getBondAt(7).setFlag(CDKConstants.ISAROMATIC,true);
      m.getBondAt(8).setFlag(CDKConstants.ISAROMATIC,true);
      m.getBondAt(9).setFlag(CDKConstants.ISAROMATIC,true);
      m.getBondAt(10).setFlag(CDKConstants.ISAROMATIC,true);
      m.getBondAt(12).setFlag(CDKConstants.ISAROMATIC,true);
      m.getBondAt(13).setFlag(CDKConstants.ISAROMATIC,true);
      satcheck.saturate(m);
      assertTrue(m.getBondAt(4).getOrder()==1);
      assertTrue(m.getBondAt(9).getOrder()==2 ^ m.getBondAt(5).getOrder()==2);
      assertTrue(m.getBondAt(13).getOrder()==2 ^ m.getBondAt(3).getOrder()==2);
    }

    /**
	 *  The main program for the SaturationCheckerTest class
	 *
	 *@param  args  The command line arguments
	 */
	public static void main(String[] args)
	{
		SaturationCheckerTest sct = new SaturationCheckerTest("SaturationCheckerTest");
		sct.standAlone = true;
		sct.setUp();
		sct.testSaturate();
		sct.testIsSaturated();
		sct.testAllSaturated();
	}
}

