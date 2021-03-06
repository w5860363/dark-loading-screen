package io.github.a5b84.darkloadingscreen.config;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class RgbFieldWidget extends TextFieldWidget {

    public static final int HEIGHT = 20;

    protected static final int SPACING = 4;
    protected static final int PREVIEW_BORDER = 0xffa0a0a0;
    //      Copié de TextFieldWidget#renderButton

    public RgbFieldWidget(TextRenderer textRenderer, int x, int y, Text text) {
        super(textRenderer, x, y, 64, HEIGHT, text);
        setMaxLength(9); // Pour laisser de la marge
    }



    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.renderButton(matrices, mouseX, mouseY, delta);

        if (!isVisible()) return;

        // Carré avec la couleur
        final int previewX = x + width + SPACING;

        // Contour
        drawRectangle(
            matrices,
            previewX - 1, y - 1,
            previewX + height, y + height,
            PREVIEW_BORDER
        );

        // Intérieur
        try {
            final int color = getColor();
            fill(matrices, previewX, y, previewX + height, y + height, color);
        } catch (NumberFormatException e) {
            // Erreur de lecture -> truc noir et rose
            final int hh = height / 2; // demi-hauteur
            fill(matrices, previewX, y, previewX + hh, y + hh, 0xfff800f8);
            fill(matrices, previewX + hh, y + hh, previewX + height, y + height, 0xfff800f8);
            fill(matrices, previewX + hh, y, previewX + height, y + hh, 0xff000000);
            fill(matrices, previewX, y + hh, previewX + hh, y + height, 0xff000000);
        }
    }

    protected int getColor() throws NumberFormatException {
        return Util.parseColor(getText()) | 0xff000000;
    }



    /** Affiche les contours d'un rectangle passant par les 2 points donnés. */
    private void drawRectangle(MatrixStack matrices, int x1, int y1, int x2, int y2, int color) {
        fill(matrices, x1, y1, x2 + 1, y1 + 1, color); // Haut
        fill(matrices, x1, y2, x2 + 1, y2 + 1, color); // Bas
        fill(matrices, x1, y1 + 1, x1 + 1, y2, color); // Gauche
        fill(matrices, x2, y1 + 1, x2 + 1, y2, color); // Droite
    }

}
