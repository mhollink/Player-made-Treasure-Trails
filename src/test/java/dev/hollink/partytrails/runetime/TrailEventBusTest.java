package dev.hollink.partytrails.runetime;

import dev.hollink.partytrails.events.EventListener;
import dev.hollink.partytrails.events.Subscription;
import dev.hollink.partytrails.events.events.AnimationEvent;
import dev.hollink.partytrails.events.events.TrailEvent;
import dev.hollink.partytrails.events.TrailEventBus;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.runelite.api.coords.WorldPoint;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import org.junit.Before;
import org.junit.Test;

public class TrailEventBusTest
{

	private TrailEventBus eventBus;

	@Before
	public void setUp() throws Exception
	{
		eventBus = new TrailEventBus();
	}

	@Test
	public void shouldDeliverEventToRegisteredListener()
	{
		List<TrailEvent> received = new ArrayList<>();

		EventListener<TrailEvent> listener = received::add;
		eventBus.register(TrailEvent.class, listener);

		TrailEvent event = new AnimationEvent(1, new WorldPoint(10, 10, 0));
		eventBus.publish(event);

		assertThat(received, hasItem(event));
	}

	@Test
	public void shouldNotDeliverEventToUnregisteredListener()
	{
		List<TrailEvent> received = new ArrayList<>();

		EventListener<TrailEvent> listener = received::add;
		Subscription subscription = eventBus.register(TrailEvent.class, listener);
		subscription.unsubscribe();

		TrailEvent event = new AnimationEvent(1, new WorldPoint(10, 10, 0));
		eventBus.publish(event);

		assertThat(received.size(), equalTo(0));
	}

	@Test
	public void shouldDeliverEventToAllRegisteredListeners()
	{
		List<TrailEvent> received1 = new ArrayList<>();
		List<TrailEvent> received2 = new ArrayList<>();

		eventBus.register(TrailEvent.class, received1::add);
		eventBus.register(TrailEvent.class, received2::add);

		TrailEvent event = new AnimationEvent(1, new WorldPoint(10, 10, 0));
		eventBus.publish(event);

		assertThat(received1, hasItem(event));
		assertThat(received2, hasItem(event));
	}
}