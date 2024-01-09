package org.ringwormgo.discordbotv2.event.commands.slashcommands.commands;

import java.util.concurrent.TimeUnit;

import org.ringwormgo.discordbotv2.event.commands.slashcommands.SlashCommand;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;

public class BanSlashCommand extends SlashCommand {
	public BanSlashCommand() {}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		if (!event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
            event.reply("You cannot ban members! Nice try ;)").setEphemeral(true).queue();
            return;
        }
        
        User target = event.getOption("user", OptionMapping::getAsUser);
        Member member = event.getOption("user", OptionMapping::getAsMember);
        if (!event.getMember().canInteract(member)) {
            event.reply("You cannot ban this user.").setEphemeral(true).queue();
            return;
        }
        int deleteTF = event.getOption("delete", OptionMapping::getAsInt);
        
        event.deferReply().queue();
        
        String reason = event.getOption("reason", OptionMapping::getAsString);
        
        try {
        	target.openPrivateChannel().complete().sendMessage("**You were banned out in \"" + event.getGuild().getName() + "\" for \"" + reason + "\"!**").queue();
        } catch(Exception e) {
        	e.printStackTrace();
        }
        
        AuditableRestAction<Void> action = event.getGuild().ban(target, deleteTF, TimeUnit.SECONDS); // Start building our ban request
        action = action.reason(reason != null ? reason : "Banned by " + event.getUser().getName()); // set the reason for the ban in the audit logs and ban log                
        action.queue(v -> {
            event.getHook().editOriginal("**" + target.getName() + "** was banned by **" + event.getUser().getName() + "**!").queue();
        }, error -> {
            event.getHook().editOriginal("Some error occurred, try again!").queue();
            error.printStackTrace();
        });
	}
}
