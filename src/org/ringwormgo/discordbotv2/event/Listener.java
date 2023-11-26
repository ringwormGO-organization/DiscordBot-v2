package org.ringwormgo.discordbotv2.event;

import org.ringwormgo.discordbotv2.event.commands.slashcommands.SlashCommandManager;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Listener extends ListenerAdapter {
	private SlashCommandManager slashCommandManager;
	
	public Listener() {
		slashCommandManager = new SlashCommandManager();
	}
	
	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		slashCommandManager.manage(event);
	}
}
