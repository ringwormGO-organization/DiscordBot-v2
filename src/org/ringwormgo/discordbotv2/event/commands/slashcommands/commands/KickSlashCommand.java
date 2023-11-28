package org.ringwormgo.discordbotv2.event.commands.slashcommands.commands;

import org.ringwormgo.discordbotv2.event.commands.slashcommands.SlashCommand;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;

public class KickSlashCommand extends SlashCommand {
	public KickSlashCommand() {}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		if (!event.getMember().hasPermission(Permission.KICK_MEMBERS)) {
            event.reply("You cannot kick members! Nice try ;)").setEphemeral(true).queue();
            return;
        }
        
        User target = event.getOption("user", OptionMapping::getAsUser);
        Member member = event.getOption("user", OptionMapping::getAsMember);
        if (!event.getMember().canInteract(member)) {
            event.reply("You cannot kick this user.").setEphemeral(true).queue();
            return;
        }
        event.deferReply().queue();
        
        String reason = event.getOption("reason", OptionMapping::getAsString);
        if(reason == null) reason = "**" + target.getName() + "** was kicked by **" + event.getUser().getName() + "**!";
        
        AuditableRestAction<Void> action = event.getGuild().kick(target).reason(reason); // Start building our ban request
        action = action.reason(reason != null ? reason : "Kicked by " + event.getUser().getName()); // set the reason for the ban in the audit logs and ban log                
        action.queue(v -> {
            event.getHook().editOriginal("**" + target.getName() + "** was kicked by **" + event.getUser().getName() + "**!").queue();
        }, error -> {
            event.getHook().editOriginal("Some error occurred, try again!").queue();
            error.printStackTrace();
        });
	}
}
