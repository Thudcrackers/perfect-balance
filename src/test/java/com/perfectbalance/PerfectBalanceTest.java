package com.perfectbalance;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class PerfectBalanceTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(PerfectBalancePlugin.class);
		RuneLite.main(args);
	}
}