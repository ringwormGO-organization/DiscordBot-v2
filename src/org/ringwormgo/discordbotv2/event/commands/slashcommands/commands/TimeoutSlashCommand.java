package org.ringwormgo.discordbotv2.event.commands.slashcommands.commands;

import java.util.concurrent.TimeUnit;

import org.ringwormgo.discordbotv2.event.commands.slashcommands.SlashCommand;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;

public class TimeoutSlashCommand extends SlashCommand {
	public TimeoutSlashCommand() {}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		if (!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            event.reply("You cannot kick members! Nice try ;)").setEphemeral(true).queue();
            return;
        }
		
		User target = event.getOption("user", OptionMapping::getAsUser);
        Member member = event.getOption("user", OptionMapping::getAsMember);
        int hours = event.getOption("time", OptionMapping::getAsInt);
        if (!event.getMember().canInteract(member)) {
            event.reply("You cannot kick this user.").setEphemeral(true).queue();
            return;
        }
        event.deferReply().queue();
        
        String reason = event.getOption("reason", OptionMapping::getAsString);
        if(reason == null) reason = "**" + target.getName() + "** was timed out by **" + event.getUser().getName() + "**!";
        
        AuditableRestAction<Void> action = event.getGuild().timeoutFor(target, hours, TimeUnit.HOURS).reason(reason); // Start building our timeout request
        action = action.reason(reason); // set the reason for the timeout in the audit logs and ban log                
        action.queue(v -> {
            event.getHook().editOriginal("**" + target.getName() + "** was timed out by **" + event.getUser().getName() + "**!").queue();
        }, error -> {
            event.getHook().editOriginal("Some error occurred, try again!").queue();
            error.printStackTrace();
        });
        
        target.openPrivateChannel().complete().sendMessage("**You were timed out in \"" + event.getGuild().getName() + "\" for \"" + reason + "\"! You will be in timeout for " + hours + "hours!**").queue();
	}
}
