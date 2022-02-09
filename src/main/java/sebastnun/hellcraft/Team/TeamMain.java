package sebastnun.hellcraft.Team;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import sebastnun.hellcraft.Main;

import java.util.ArrayList;
import java.util.Set;

public class TeamMain {

    private static final Scoreboard scoreboard = Main.getInstance().getScoreboard();


    public boolean existTeam(String nameTeam){

        boolean isName = false;

        for (Team team : scoreboard.getTeams()){
            if (team.getName().equalsIgnoreCase(nameTeam)){
                isName = true;
                break;
            }else {
                isName = false;
            }
        }
        return isName;
    }


    public static void createTeam(String nameTeam){
        scoreboard.registerNewTeam(nameTeam);
    }


    public static void addMember(Player player, Team team, Player capitan){
        for (Team team1 : scoreboard.getTeams()){
            if (scoreboard.getPlayerTeam(player).equals(team1)){
                capitan.sendMessage(Main.format("&cEl jugador ".concat(player.getName()).concat(" ya esta en otro ")));
                return;
            }
        }
        team.addPlayer(player);
    }

    public static void addMember(Player player, Team team){
        for (Team team1 : scoreboard.getTeams()){
            if (scoreboard.getPlayerTeam(player).equals(team1)){
                return;
            }
        }

        team.addPlayer(player);
    }


    public static Team getTeam(Player player){
        return scoreboard.getPlayerTeam(player);
    }

    public static OfflinePlayer[] getMembers(String nameTeam){
        return (OfflinePlayer[]) scoreboard.getTeam(nameTeam).getPlayers().toArray();
    }





}
