package net.syuui.acoust.dataif;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class StaticToolsTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSpellShort() {
		byte[] tmp = new byte[2];
		tmp[0] = (byte)0x00;
		tmp[1] = (byte)0x00;
		assertEquals(0x0000, StaticTools.spellShort(tmp));

		tmp[0] = (byte)0x00;
		tmp[1] = (byte)0xFF;
		assertEquals(0xFF00, StaticTools.spellShort(tmp));
		
		tmp[0] = (byte)0xFF;
		tmp[1] = (byte)0x00;
		assertEquals(0x00FF, StaticTools.spellShort(tmp));
		
		tmp[0] = (byte)0x01;
		tmp[1] = (byte)0x00;
		assertEquals(0x0001, StaticTools.spellShort(tmp));
		
		tmp[0] = (byte)0x00;
		tmp[1] = (byte)0x01;
		assertEquals(0x0001, StaticTools.spellShort(tmp));
		
		tmp[0] = (byte)0xFF;
		tmp[1] = (byte)0x00;
		assertEquals(0x0000, StaticTools.spellShort(tmp));
		
		tmp[0] = (byte)0xFF;
		tmp[1] = (byte)0x00;
		assertEquals(0x00FF, StaticTools.spellShort(tmp));
		
		
		//fail("Not yet implemented");
	}

	@Test
	public void testSpellInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testSplitShort() {
		fail("Not yet implemented");
	}

	@Test
	public void testSplitInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testSwitchShort() {
		fail("Not yet implemented");
	}

	@Test
	public void testSwitchInt() {
		fail("Not yet implemented");
	}

}
