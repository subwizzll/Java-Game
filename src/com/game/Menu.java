package com.game;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class Menu extends MouseAdapter {

    private Game game;
    private int buttonWidth = 230;
    private int buttonHeight = 64;
    private String[][] menuText = new String[][]{
            {"Play", "Settings", "How to Play"},
            {"Setting #1", "Setting #2", "Back"},
            {"Back"}
    };
    private int offsetY = buttonHeight + buttonHeight / 2;
    private int stack = ((buttonHeight + offsetY) * menuText.length - offsetY) / 4;
    private int buttonX = (int) Game.WIDTH / 2 - buttonWidth / 2;
    private int buttonY = (int) Game.HEIGHT / 2 - buttonHeight / 2 - stack;

    public Menu(Game game) {
        this.game = game;
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        // Main Menu Click Handlers
        if (game.gameState == Game.STATE.Menu) {
            for (int i = 0; i < menuText[0].length; i++) {
                if (mouseOver(mx, my, buttonX, buttonY + offsetY * i, buttonWidth, buttonHeight)) {
                    if (i == 0) game.gameState = Game.STATE.Game;       // Play
                    if (i == 1) game.gameState = Game.STATE.Settings;   // Settings
                    if (i == 2) game.gameState = Game.STATE.HowToPlay;  // How to Play
                }
            }
        }
        // Settings Menu Click Handlers
        else if (game.gameState == Game.STATE.Settings) {
            for (int i = 0; i < menuText[0].length; i++) {
                if (mouseOver(mx, my, buttonX, buttonY + offsetY * i, buttonWidth, buttonHeight)) {
                    if (i == 0) ; // TODO
                    if (i == 1) ; // TODO
                    if (i == 2) game.gameState = Game.STATE.Menu;       // Back
                }
            }
        }
        // HowToPlay Menu Click Handlers
        else if (game.gameState == Game.STATE.HowToPlay) {
            for (int i = 0; i < menuText[0].length; i++) {
                if (mouseOver(mx, my, buttonX, buttonY + offsetY * i, buttonWidth, buttonHeight)) {
                    if (i == 0) game.gameState = Game.STATE.Menu;       // Back
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) {

    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width && my > y && my < y + height)
            return true;
        return false;
    }

    public void tick() {

    }

    public void render(Graphics g) {
        int width, height;
        Font fnt = new Font("courier new", 1, 70);
        Font fnt2 = new Font("courier new", 1, 30);
        g.setColor(Color.WHITE);
        g.setFont(fnt);

        // Main Menu Graphics
        if (game.gameState == Game.STATE.Menu) {
            width = g.getFontMetrics().stringWidth("Menu");
            g.drawString("Menu", (int) game.WIDTH / 2 - width / 2, (int) game.HEIGHT / 100 * 15);
            g.setFont(fnt2);
            for (int i = 0; i < menuText[0].length; ++i) {
                g.drawRect(buttonX, buttonY + offsetY * i, buttonWidth, buttonHeight);
                width = g.getFontMetrics().stringWidth(menuText[0][i]);
                height = g.getFontMetrics().getHeight();
                g.drawString(menuText[0][i], buttonX + buttonWidth / 2 - width / 2,
                        buttonY + offsetY * i + buttonHeight - buttonHeight / 2 + height / 4);
            }
        }
        // Settings Menu Graphics
        else if (game.gameState == Game.STATE.Settings) {
            width = g.getFontMetrics().stringWidth("Settings");
            g.drawString("Settings", (int) game.WIDTH / 2 - width / 2, (int) game.HEIGHT / 100 * 15);
            g.setFont(fnt2);

            for (int i = 0; i < menuText[1].length; ++i) {
                g.drawRect(buttonX, buttonY + offsetY * i, buttonWidth, buttonHeight);
                width = g.getFontMetrics().stringWidth(menuText[1][i]);
                height = g.getFontMetrics().getHeight();
                g.drawString(menuText[1][i], buttonX + buttonWidth / 2 - width / 2,
                        buttonY + offsetY * i + buttonHeight - buttonHeight / 2 + height / 4);
            }
        }
        // HowToPlay Menu Graphics
        else if (game.gameState == Game.STATE.HowToPlay) {
            width = g.getFontMetrics().stringWidth("HowToPlay");
            g.drawString("HowToPlay", (int) game.WIDTH / 2 - width / 2, (int) game.HEIGHT / 100 * 15);
            g.setFont(fnt2);

            for (int i = 0; i < menuText[2].length; ++i) {
                g.drawRect(buttonX, buttonY + offsetY * i, buttonWidth, buttonHeight);
                width = g.getFontMetrics().stringWidth(menuText[2][i]);
                height = g.getFontMetrics().getHeight();
                g.drawString(menuText[2][i], buttonX + buttonWidth / 2 - width / 2,
                        buttonY + offsetY * i + buttonHeight - buttonHeight / 2 + height / 4);
            }
        }
    }
}
