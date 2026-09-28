package com.quizapp.terminal;

import com.quizapp.dao.PlayerDAO;
import com.quizapp.dao.ReportDAO;
import com.quizapp.model.Player;

import java.util.List;
import java.util.Scanner;

public class TerminalApp {

    public static void main(String[] args) {
        new TerminalApp().start();
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        PlayerDAO pdao = new PlayerDAO();
        ReportDAO rdao = new ReportDAO();

        while (true) {
            System.out.println("\n=== QUIZ TERMINAL MENU ===");
            System.out.println("1) Search player by ID");
            System.out.println("2) Delete player by ID");
            System.out.println("3) View all players (summary list)");
            System.out.println("4) View leaderboard");
            System.out.println("5) View statistical summary");
            System.out.println("0) Exit");
            System.out.print("Choose: ");

            String choice = sc.nextLine().trim();
            if (choice.equals("0")) break;

            try {
                if (choice.equals("1")) {
                    int id = readInt(sc, "Enter player ID: ");
                    Player p = pdao.findById(id);

                    if (p == null) {
                        System.out.println("No player found.");
                    } else {
                        System.out.println("\n--- Player Details ---");
                        System.out.println("ID: " + p.getPlayerId());
                        System.out.println("Name: " + p.getName());
                        System.out.println("Username: " + p.getUsername());
                        System.out.println("Extra: " + p.getExtraAttr());
                        System.out.println();
                        System.out.println(rdao.playerScoreSummary(id));
                    }

                } else if (choice.equals("2")) {
                    int id = readInt(sc, "Enter player ID to delete: ");

                    Player p = pdao.findById(id);
                    if (p == null) {
                        System.out.println("Player not found.");
                        continue;
                    }

                    System.out.println("Deleting: " + p.getName() + " (" + p.getUsername() + ")");
                    System.out.print("Type YES to confirm: ");
                    String confirm = sc.nextLine().trim();

                    if (!confirm.equalsIgnoreCase("YES")) {
                        System.out.println("Delete cancelled.");
                        continue;
                    }

                    boolean ok = pdao.deleteById(id);
                    System.out.println(ok ? "Player deleted." : "Delete failed.");

                } else if (choice.equals("3")) {
                    List<Player> players = pdao.findAll();
                    System.out.println("\n--- All Players ---");
                    if (players.isEmpty()) {
                        System.out.println("No players found.");
                    } else {
                        for (Player p : players) {
                            System.out.println(
                                    p.getPlayerId() + " | " + p.getName() + " | " + p.getUsername() + " | " + p.getExtraAttr()
                            );
                        }
                    }

                } else if (choice.equals("4")) {
                    System.out.print("Level (beginner/intermediate/advanced or all): ");
                    String level = sc.nextLine().trim().toLowerCase();

                    List<ReportDAO.LeaderRow> rows = rdao.leaderboard(level);
                    System.out.println("\n--- Leaderboard ---");
                    if (rows.isEmpty()) {
                        System.out.println("No results yet.");
                    } else {
                        System.out.println("Rank | ID | Name | Username | Total");
                        for (ReportDAO.LeaderRow r : rows) {
                            System.out.println(r.rank + " | " + r.playerId + " | " + r.name + " | " + r.username + " | " + r.totalScore);
                        }
                    }

                } else if (choice.equals("5")) {
                    System.out.println("\n--- Statistical Summary ---");
                    System.out.println(rdao.statisticalSummary());

                } else {
                    System.out.println("Invalid choice");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        sc.close();
        System.out.println("Bye.");
    }

    private int readInt(Scanner sc, String msg) {
        while (true) {
            System.out.print(msg);
            String s = sc.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (Exception e) {
                System.out.println("Enter a valid number.");
            }
        }
    }
}
