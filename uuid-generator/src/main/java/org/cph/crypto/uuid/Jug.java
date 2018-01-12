package org.cph.crypto.uuid;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.NoArgGenerator;
import org.cph.crypto.core.spi.IdGenerator;

public class Jug implements IdGenerator {
	@Override
	public String getNewId() {
		return generator().generate().toString();
	}

	private static NoArgGenerator generator() {
		return Generators.timeBasedGenerator(EthernetAddress.fromInterface());
	}
}
