package it.polimi.ingsw.PSP50.Model.GodsList;

import it.polimi.ingsw.PSP50.Model.God;
import it.polimi.ingsw.PSP50.Model.Gods;
import it.polimi.ingsw.PSP50.Model.Player;
import it.polimi.ingsw.PSP50.Model.Space;

import java.util.ArrayList;

public class Athena extends God {
    private final Gods name = Gods.ATHENA;

    // Athena doesn't need to use Power because she uses normal move and build.
    @Override
    public ArrayList<Space> power(Player player) {
        return null;
    }
}
