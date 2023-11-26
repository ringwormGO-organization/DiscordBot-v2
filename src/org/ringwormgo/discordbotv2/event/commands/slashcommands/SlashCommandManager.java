package org.ringwormgo.discordbotv2.event.commands.slashcommands;

import java.util.HashMap;

import org.ringwormgo.discordbotv2.event.commands.slashcommands.commands.BanSlashCommand;
import org.ringwormgo.discordbotv2.event.commands.slashcommands.commands.PingSlashCommand;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class SlashCommandManager {
	private HashMap<String, SlashCommand> commands = new HashMap<String, SlashCommand>();
	
	public SlashCommandManager() {
		commands.put("ban", new BanSlashCommand());
		commands.put("ping", new PingSlashCommand());
	}
	
	public void manage(SlashCommandInteractionEvent event) {
		SlashCommand cmd = commands.get(event.getName());
		if(cmd == null) {
			System.err.printf("Unknown command %s used by %#s%n", event.getName(), event.getUser());
			event.reply("Unknown command!").queue();
			return;
		}
		cmd.execute(event);
	}
}
