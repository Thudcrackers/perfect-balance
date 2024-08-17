package com.perfectbalance;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.ImageUtil;

import java.awt.image.BufferedImage;

@Slf4j
@PluginDescriptor(
	name = "Perfect Balance",
	description = "Changes the skill tab icon to be perfectly balanced."
)
public class PerfectBalancePlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Override
	protected void startUp() throws Exception
	{
		overrideSkillIcon();
	}

	@Override
	protected void shutDown() throws Exception
	{
		client.getWidgetSpriteCache().reset();
		setSkillIcon("898");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			overrideSkillIcon();
		}
	}

	private SpritePixels getFileSpritePixels(String file)
	{
		try
		{
			System.out.println("Loading: " + file);
			BufferedImage image = ImageUtil.loadImageResource(this.getClass(), file);
			return ImageUtil.getImageSpritePixels(image, client);
		}
		catch (RuntimeException ex)
		{
			System.out.println("Unable to load image: " + ex);
		}

		return null;
	}

	private void overrideSkillIcon()
	{
		clientThread.invoke(() ->
		{
			if (client.getGameState().getState() <= GameState.LOGIN_SCREEN.getState())
			{
				return false;
			}
			setSkillIcon("interfaceicon");
			System.out.println("Attempting to override");
			return true;
		});
	}

	private void setSkillIcon(String iconName)
	{
		String spriteFile = iconName + ".png";
		SpritePixels spritePixels = getFileSpritePixels(spriteFile);
		client.getSpriteOverrides().put(898, spritePixels);
	}
}
