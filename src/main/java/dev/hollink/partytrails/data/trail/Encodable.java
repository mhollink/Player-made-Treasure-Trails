package dev.hollink.partytrails.data.trail;

import dev.hollink.partytrails.data.StepType;
import java.io.DataOutput;
import java.io.IOException;

public interface Encodable
{
	StepType type();

	void encode(DataOutput out)
		throws IOException;
}
