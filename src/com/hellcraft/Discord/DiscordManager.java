package com.hellcraft.Discord;

import java.awt.Color;
import java.io.File;
import java.time.LocalDate;
import javax.security.auth.login.LoginException;

import com.hellcraft.Main;
import com.hellcraft.Util.Log;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DiscordManager {
    private static DiscordManager discordManager;

    private final Main instance = Main.getInstance();

    private JDA bot;

    public DiscordManager(){
        try {
            JDABuilder builder = new JDABuilder(AccountType.BOT).setToken(instance.getConfig().getString("Token"));
            builder.addEventListeners(new Object[] { new JDAListeners(this) });
            this.bot = builder.build();
            this.bot.awaitReady();
        } catch (LoginException e) {
            e.printStackTrace();
            log("Ha ocurrido un error al iniciar sesion la aplicacide Discord, revisa tu token.");
        } catch (InterruptedException e) {
            e.printStackTrace();
            log("Ha ocurrido un error al iniciar sesion la aplicacide Discord, revisa tu token.");
        }
    }

//68 69

    public void banPlayer(OfflinePlayer off, String messages) {
        Player p = off.isOnline() ? (Player)off : null;
        String playerLoc = (p.getLocation().getBlockX() + " " + p.getLocation().getBlockY() + " " + p.getLocation().getBlockZ());
        String serverName = "Hellcraft";
        LocalDate n = LocalDate.now();
        String date = String.format("%02d/%02d/%02d", new Object[] { Integer.valueOf(n.getDayOfMonth()), Integer.valueOf(n.getMonthValue()), Integer.valueOf(n.getYear()) });
        EmbedBuilder b = buildEmbed(off.getName() + " ha sido PERMABANEADO de " + serverName + "\n", new Color(15993868), null, null, "https://minotar.net/armor/bust/" + off
                .getName() + "/100.png", new String[0]);
        String chanel = instance.getConfig().getString("DiscordChanelID");

        b.setAuthor("The Zazz");
        b.addField("Fecha", date, true);
        b.addField("Razon", messages, true);
        b.addField("Coordenadas", playerLoc, true);
        TextChannel channel = getBot().getTextChannelById("830213241296257024");
        if (channel == null)
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"No se encontro nada en el canal de muertes");
        channel.sendMessage(b.build()).queue(message -> message.addReaction("☠").queue());

    }

    public void onDayChange() {
        if (this.bot == null)
            return;
        String s = instance.getConfig().getString("Channels.Anuncios");
        if (s == null)
            return;
        TextChannel channel = this.bot.getTextChannelById(s);
        if (channel == null)
            return;
        sendEmbed((MessageChannel)channel, buildEmbed("Hellcraft", new Color(0xFFA09B18, true), null, null, "https://i.ibb.co/4sz3kys/logogrnade.png", new String[] { ":alarm_clock: Han avanzado al dia " + this.instance.getDays() }), new String[0]);
    }

    public void onDataManagerEnable() {
        if (this.bot == null)
            return;
        String s = instance.getConfig().getString("Channels.Anuncios");
        if (s == null)
            return;
        TextChannel channel = this.bot.getTextChannelById(s);
        if (channel == null)
            return;
        //https://i.ibb.co/4sz3kys/logogrnade.png
        EmbedBuilder b = buildEmbed("HELLCRAFT HA CERRADO", new Color(0xF8D600), null, null,"https://i.ibb.co/4sz3kys/logogrnade.png", new String[0]);
        b.setAuthor("HELLCRAFT ");
        b.addField("Hellcraft 2 ESTARÁ DISPONIBLE PRÓXIMAMENTE ","",false);

        channel.sendMessage("@everyone").queue();
        channel.sendMessage(b.build()).queue();
    }


    private void log(String s) {
        Log.getInstance().log("[DISCORD] " + s);
    }

    public JDA getBot() {
        return this.bot;
    }

    private EmbedBuilder buildEmbed(String title, Color color, String footer, String image, String thumbnail, String... description) {
        EmbedBuilder eb = new EmbedBuilder();
        if (title != null)
            eb.setTitle(title);
        if (color != null)
            eb.setColor(color);
        if (footer != null)
            eb.setFooter(footer);
        if (image != null)
            eb.setImage(image);
        if (thumbnail != null)
            eb.setThumbnail(thumbnail);
        for (String s : description)
            eb.addField("", s, false);
        return eb;
    }

    public static DiscordManager getInstance() {
        if (discordManager == null)
            discordManager = new DiscordManager();
        return discordManager;
    }

    private void sendEmbed(MessageChannel channel, EmbedBuilder b, String... reaction) {
        channel.sendMessage(b.build()).queue(message -> {
            for (String s : reaction)
                message.addReaction(s).queue();
        });
    }


}