package org.ringwormgo.discordbotv2.event.commands.slashcommands.commands;

import org.ringwormgo.discordbotv2.event.commands.slashcommands.SlashCommand;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class AboutSlashCommand extends SlashCommand {
	private String aboutString = "**Ringwormgo discord bot v2**\n"
			+ "RingWormGO discord bot v2 is a discord bot written by the RingWormGO team.\n"
			+ "The github URL for the devteam is <https://github.com/ringwormGO-organization>\n"
			+ "And the bot can be found at <https://github.com/ringwormGO-organization/DiscordBot-v2>"; 
	
	public AboutSlashCommand() {}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		event.reply(aboutString).queue();
	}
}
