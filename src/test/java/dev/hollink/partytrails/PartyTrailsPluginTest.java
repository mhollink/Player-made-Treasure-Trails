package dev.hollink.partytrails;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class PartyTrailsPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(PartyTrailsPlugin.class);
		RuneLite.main(args);
	}
}