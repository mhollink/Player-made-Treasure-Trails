package dev.hollink.partytrails.data.trail;

import dev.hollink.partytrails.data.events.ClueEvent;

public interface Steppable
{

	void onActivate(TrailContext context);

	boolean handlesEvent(ClueEvent event);

	boolean isComplete(TrailContext context, ClueEvent event);

}
