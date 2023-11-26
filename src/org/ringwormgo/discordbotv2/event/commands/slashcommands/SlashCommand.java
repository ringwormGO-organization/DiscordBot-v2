package org.ringwormgo.discordbotv2.event.commands.slashcommands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public abstract class SlashCommand {
	public abstract void execute(SlashCommandInteractionEvent event);
}
