package com.game;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;

// main menu class
public class Menu extends MouseAdapter {

    private final Game game;
    private final Handler handler;
    private final HUD hud;

    // global button dimensions
    private final int buttonWidth = 230;
    private final int buttonHeight = 64;

    // list of buttons
    private final String[][] menuText = new String[][]{
            {"Play", "About", "How to Play"},
            {"", "", "Back"},
            {"","","Back"}
    };

    // button layout parameters
    private final int offsetY = buttonHeight + buttonHeight / 2;
    private final int buttonStackHeight = ((buttonHeight + offsetY) * menuText.length - offsetY) / 4;
    private final int centerButtonX = (int) Game.WIDTH / 2 - buttonWidth / 2;
    private final int centerButtonY = (int) Game.HEIGHT / 2 - buttonHeight / 2 - buttonStackHeight;

    // menu torches
    private final BufferedImage[] torch = new BufferedImage[9];

    // ticker for animation
    private int ticker = 0;

    // constructor
    public Menu(Game game,Handler handler, HUD hud) {
        this.game = game;
        this.handler = handler;
        this.hud = hud;
        game.init();
        SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());
        for (int i = 0; i < torch.length; i++) {
            this.torch[i] = ss.getImage(10,i+8, ss.getCellSize(), ss.getCellSize()*2);
        }
    }

    // click handlers
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        // Main Menu Click Handlers
        if (game.gameState == Game.STATE.Menu) {
            for (int i = 0; i < menuText[0].length; i++) {
                if (mouseOver(mx, my, centerButtonX, centerButtonY + offsetY * i, buttonWidth, buttonHeight)) {
                    // Play
                    if (i == 0) {
                        game.gameState = Game.STATE.Game;
                        HUD.health = 100;
                        Game.paused = false;
                    }
                    // Settings
                    if (i == 1) game.gameState = Game.STATE.Settings;
                    // How to Play
                    if (i == 2) game.gameState = Game.STATE.HowToPlay;
                }
            }
        }
        // Settings Menu Click Handlers
        else if (game.gameState == Game.STATE.Settings) {
            for (int i = 0; i < menuText[0].length; i++) {
                if (mouseOver(mx, my, centerButtonX, centerButtonY + offsetY * i, buttonWidth, buttonHeight)) {
                    // Back button
                    if (i == 2) {
                        handler.object.clear();
                        game.gameState = Game.STATE.Menu;
                    }
                }
            }
        }
        // HowToPlay, Game Over, Game Won, and Pause Menu Click Handlers
        else if (game.gameState == Game.STATE.HowToPlay
                ||
                game.gameState == Game.STATE.GameOver
                ||
                game.gameState == Game.STATE.GameWon
                ||
                Game.paused) {
            for (int i = 0; i < menuText[0].length; i++) {
                if (mouseOver(mx, my, centerButtonX, centerButtonY + offsetY * i, buttonWidth, buttonHeight)) {
                    // Back
                    if (i == 2){ game.gameState = Game.STATE.Menu;
                        handler.object.clear();
                        hud.setLevel(0);
                        hud.setScore(0);
                    }
                }
            }
        }
    }

    // returns true if mouse is within designated box
    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
    return mx > x && mx < x + width && my > y && my < y + height;
    }

    // this drives torch animations
    public void tick() {
        ticker++;
        if(ticker == torch.length*5){
            ticker = 0;
        }
    }

    // rendering for all menus
    public void render(Graphics g) {

        // these values are reassigned through out this method to provide consistent spacing
        int width, height;
        String header;
        Font fnt = new Font("courier new", Font.BOLD, 70);
        Font fnt2 = new Font("courier new", Font.PLAIN, 50);
        Font fnt3 = new Font("courier new", Font.PLAIN, 30);
        g.setColor(Color.WHITE);
        g.setFont(fnt);

        // Main Menu Graphics
        if (game.gameState == Game.STATE.Menu) {
            header = "Dark Dungeon";
            header(g, header);
            g.setFont(fnt3);

            for (int i = 0; i < menuText[0].length; ++i) {
                g.drawRect(centerButtonX, centerButtonY + offsetY * i, buttonWidth, buttonHeight);
                width = g.getFontMetrics().stringWidth(menuText[0][i]);
                height = g.getFontMetrics().getHeight();
                g.drawString(menuText[0][i], centerButtonX + buttonWidth / 2 - width / 2,
                        centerButtonY + offsetY * i + buttonHeight - buttonHeight / 2 + height / 4);
            }
        }
        // Settings Menu Graphics
        else if (game.gameState == Game.STATE.Settings) {
            header = "About";
            header(g, header);
            g.setFont(fnt3);

            String[] aboutText = new  String[]{"The objective of this game is pure survival,",
                                                "your score increases the longer you stay alive.",
                                                "You will face grim opposition,",
                                                "",
                                                "Good Luck."
            };
            width = g.getFontMetrics().stringWidth(aboutText[0]);
            height = 0;
            for (int i = 0; i < aboutText.length; ++i) {
                height += g.getFontMetrics().getHeight();
                g.drawString(aboutText[i], (int) Game.WIDTH / 2 - width / 2,
                        (int) Game.HEIGHT / 4  + height);
            }
            for (int i = 0; i < menuText[1].length; ++i) {
                if(!menuText[1][i].equals("")){
                    g.drawRect(centerButtonX, centerButtonY + offsetY * i, buttonWidth, buttonHeight);
                    width = g.getFontMetrics().stringWidth(menuText[1][i]);
                    height = g.getFontMetrics().getHeight();
                    g.drawString(menuText[1][i], centerButtonX + buttonWidth / 2 - width / 2,
                            centerButtonY + offsetY * i + buttonHeight - buttonHeight / 2 +
                                    height / 4);
                }
            }
        }
        // HowToPlay Menu Graphics
        else if (game.gameState == Game.STATE.HowToPlay) {
            header = "HowToPlay";
            header(g, header);
            g.setFont(fnt3);

            String helpText = "Move your player with the WASD keys, press Esc to pause.";
            width = g.getFontMetrics().stringWidth(helpText);
            g.drawString(helpText, (int) Game.WIDTH / 2 - width / 2,
                                    (int) Game.HEIGHT / 2);

            for (int i = 0; i < menuText[2].length; ++i) {
                if(!menuText[2][i].equals("")) {
                    g.drawRect(centerButtonX, centerButtonY + offsetY * i, buttonWidth, buttonHeight);
                    width = g.getFontMetrics().stringWidth(menuText[2][i]);
                    height = g.getFontMetrics().getHeight();
                    g.drawString(menuText[2][i], centerButtonX + buttonWidth / 2 - width / 2,
                                                centerButtonY + offsetY * i + buttonHeight - buttonHeight / 2 +
                                                        height / 4);
                }
            }
        }
        // Game Over and Game One Menu Graphics
        else if (game.gameState == Game.STATE.GameOver
                ||
                game.gameState == Game.STATE.GameWon) {
            header = game.gameState == Game.STATE.GameOver ? "Game Over" : "Congratulations";
            String lineOne = game.gameState == Game.STATE.GameOver ?  "You Lose" : "You Win!";
            String lineTwo = String.format("Score: %7s",hud.getScore());
            String lineThree = String.format("Level: %7s",hud.getLevel());
            header(g, header);
            g.setFont(fnt2);
            width = g.getFontMetrics().stringWidth(lineOne);
            g.drawString(lineOne, (int) Game.WIDTH / 2 - width / 2,
                                (int) Game.HEIGHT / 2 - (buttonHeight+offsetY));
            g.setFont(fnt3);
            width = g.getFontMetrics().stringWidth(lineTwo);
            height = g.getFontMetrics().getHeight();
            g.drawString(lineTwo, (int) Game.WIDTH / 2 - width / 2,
                                    (int) Game.HEIGHT / 2 - height);
            width = g.getFontMetrics().stringWidth(lineThree);
            g.drawString(lineThree, (int) Game.WIDTH / 2 - width / 2,
                                    (int) Game.HEIGHT / 2);

            for (int i = 0; i < menuText[2].length; ++i) {
                if(!menuText[2][i].equals("")) {
                    width = g.getFontMetrics().stringWidth(menuText[2][i]);
                    height = g.getFontMetrics().getHeight();
                    g.drawString(menuText[2][i], centerButtonX + buttonWidth / 2 - width / 2,
                            centerButtonY + offsetY * i + buttonHeight - buttonHeight / 2 + height / 4);
                    g.drawRect(centerButtonX, centerButtonY + offsetY * i, buttonWidth, buttonHeight);
                }
            }
        }
        // Pause Menu Graphics
        else if (Game.paused) {
            header = "Paused";
            header(g, header);
            g.setFont(fnt3);

            String helpText = "Press Esc to resume.";
            width = g.getFontMetrics().stringWidth(helpText);
            g.drawString(helpText, (int) Game.WIDTH / 2 - width / 2,
                    (int) Game.HEIGHT / 2);

            for (int i = 0; i < menuText[2].length; ++i) {
                if(!menuText[2][i].equals("")) {
                    g.drawRect(centerButtonX, centerButtonY + offsetY * i, buttonWidth, buttonHeight);
                    width = g.getFontMetrics().stringWidth(menuText[2][i]);
                    height = g.getFontMetrics().getHeight();
                    g.drawString("Quit", centerButtonX + buttonWidth / 2 - width / 2,
                            centerButtonY + offsetY * i + buttonHeight - buttonHeight / 2 +
                                    height / 4);
                }
            }
        }
    }
    // this method renders our menu headers
    public void header(Graphics g, String header) {
        int width;
        int height;
        width = g.getFontMetrics().stringWidth(header);
        height = g.getFontMetrics().getHeight();
        g.drawString(header, (int) Game.WIDTH / 2 - width / 2,
                            (int) Game.HEIGHT / 2 - (buttonHeight+offsetY+height/2));
        g.drawImage(torch[ticker/5],(int) Game.WIDTH / 2 - width/2 - 72,
                                    (int) Game.HEIGHT / 2 - (buttonHeight+offsetY+height/2+88),
                                    48,96,null);
        g.drawImage(torch[ticker/5],(int) Game.WIDTH / 2 + width/2 + 24,
                                    (int) Game.HEIGHT / 2 - (buttonHeight+offsetY+height/2+88),
                                    48,96,null);
    }

}
