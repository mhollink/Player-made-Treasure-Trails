package dev.hollink.partytrails.data.steps;

import dev.hollink.partytrails.data.InteractionTarget;
import dev.hollink.partytrails.data.events.ClueEvent;
import dev.hollink.partytrails.data.events.InteractionEvent;
import dev.hollink.partytrails.data.trail.TrailContext;
import static dev.hollink.partytrails.encoding.TrailEncoder.writeString;
import java.io.DataOutput;
import java.io.IOException;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public abstract class InteractionStep implements TrailStep
{

	protected final String cipherText;
	protected final InteractionTarget target;

	@Override
	public void onActivate(TrailContext context)
	{
		// noop
	}

	@Override
	public boolean handlesEvent(ClueEvent event)
	{
		return event instanceof InteractionEvent;
	}

	@Override
	public boolean isComplete(TrailContext context, ClueEvent event)
	{
		if (event instanceof InteractionEvent)
		{
			InteractionEvent interactionEvent = (InteractionEvent) event;
			return target.checkEvent(interactionEvent);
		}
		else
		{
			return false;
		}
	}

	@Override
	public void encode(DataOutput out) throws IOException
	{
		writeString(out, cipherText);
		out.writeInt(target.getTargetId());
		writeString(out, target.getTargetName());
		writeString(out, target.getInteractionType());
		out.writeInt(target.getLocation().getX());
		out.writeInt(target.getLocation().getY());
		out.writeInt(target.getLocation().getPlane());
	}
}
